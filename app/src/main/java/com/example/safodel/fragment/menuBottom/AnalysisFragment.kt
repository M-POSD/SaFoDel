package com.example.safodel.fragment.menuBottom

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import com.afollestad.materialdialogs.MaterialDialog
import com.ajithvgiri.searchdialog.OnSearchItemSelected
import com.ajithvgiri.searchdialog.SearchListItem
import com.ajithvgiri.searchdialog.SearchableDialog
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.example.safodel.R
import com.example.safodel.adapter.AnalysisViewAdapter
import com.example.safodel.adapter.HomeViewAdapter
import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import com.example.safodel.ui.analysis.YourMarkerView
import com.example.safodel.ui.main.MainActivity

private var spinnerIndex = 0

class AnalysisFragment : BasicFragment<FragmentAnalysisBinding>(FragmentAnalysisBinding::inflate),OnSearchItemSelected {
//    private lateinit var spProvince: SmartMaterialSpinner<Any>
    private var suburbList = SuburbList.init()
    private lateinit var suburbInterface: SuburbInterface
    private lateinit var dialog: MaterialDialog
//    private lateinit var lineChart: LineChart
    private lateinit var bar: HorizontalBarChart
    private lateinit var mainActivity: MainActivity
    //private lateinit var accidentsNumber : TextView
    private var suburbName = "MELBOURNE"
    private lateinit var toast1: Toast
    private lateinit var toast2: Toast
    private lateinit var toast3: Toast
    private lateinit var scrollView: View
    private lateinit var toolbar: Toolbar
    private lateinit var searchableDialog: SearchableDialog
    private lateinit var spinnerText: TextView

    private lateinit var adapter: AnalysisViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        toast1 = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        toast2 = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        toast3 = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        scrollView = binding.scrollView
        spinnerText = binding.spinnerText
        toolbar = binding.toolbar.root

        // Set Dialog
        dialog = MaterialDialog(requireContext())

        // Set toolbar
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        // Set text
        //accidentsNumber  = binding.accidentNumber

        // Set Chart
        //lineChart = binding.lineChart
        bar = binding.barChart
        bar.zoomOut()
        bar.xAxis.labelCount = 5

        // setting height of scroll view
        setMarginView()

        // Retrofit get data
        suburbInterface = SuburbClient.getSuburbService()

        adapter = AnalysisViewAdapter(requireActivity())
        binding.viewPager2Home.adapter = adapter
        binding.wormDotsIndicatorHome.setViewPager2(binding.viewPager2Home)
        mainActivity = activity as MainActivity
        // Setting Spinner
        initSpinner()



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    fun start(){
        setDialog()
        callSuburbClient(suburbName)
    }

    /*
        Setting the spinner
     */
    private fun initSpinner() {
//        spProvince = binding.spinner
//        val typeFace = ResourcesCompat.getFont(requireContext(), R.font.opensans_medium)
//        spProvince.typeface = typeFace
//        spProvince.item = suburbList
//        spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
//                setDialog()
//                callSuburbClient(spProvince.item[position].toString())
//            }
//            override fun onNothingSelected(adapterView: AdapterView<*>) {}
//        }

        searchableDialog = SearchableDialog(mainActivity, suburbList, getString(R.string.select_suburb))
        searchableDialog.setOnItemSelected(this)
        binding.spinnerBackground.setOnClickListener {
            searchableDialog.show()
            searchableDialog.recyclerView.smoothScrollToPosition(spinnerIndex)
        }

        spinnerText.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.baseline_search_black_36,0,0,0
        )
    }

    /*
        Setting the dialog when item select
     */
    private fun setDialog(){
        dialog.show {
            message(text = getString(R.string.loading))
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }

    fun setMarginView(){
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            val scrollHeight = scrollView.layoutParams as LinearLayout.LayoutParams

            while (scrollHeight.topMargin == 0)
                scrollHeight.topMargin = toolbar.measuredHeight
            scrollView.layoutParams = scrollHeight
        }
    }


    /*
        Init the data from retrofit 2
     */
    private fun callSuburbClient(suburbName: String){

        /*
            Get the data for first bar -- bar2
         */
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            //job2.join()
            val callStreet: Call<SuburbStreetsResponse> = suburbInterface.streetRepos(
                "streets",
                suburbName
            )
            callStreet.enqueue(object : Callback<SuburbStreetsResponse?> {
                override fun onResponse(call: Call<SuburbStreetsResponse?>?, response: Response<SuburbStreetsResponse?>) {
                    if (response.isSuccessful) {
                        dialog.dismiss()
                        val resultList = response.body()!!.suburbStreetsAccidents
                        setStreetsBarChat(resultList)
                    } else {
                        Timber.i("Response failed")
                    }
                }
                override fun onFailure(call: Call<SuburbStreetsResponse?>?, t: Throwable) {
                    dialog.dismiss()
                    toast3.setText(t.message)
                    toast3.show()
                    Timber.i(t.localizedMessage.toString())
                }
            })
        }



    }



    private fun setStreetsBarChat(list: List<SuburbStreetsAccidents>){
        val color  = ContextCompat.getColor(requireContext(), R.color.primary_green)
        val rawList = list.sortedBy {
            it.accidentsNumber
        }
        val data: MutableList<BarEntry> = ArrayList()
        var count = 0
        val map :HashMap<Int,String>  = HashMap()
        for (each in rawList){
            map.put(count,each.accidentAddressName)
            data.add(BarEntry(count.toFloat(),each.accidentsNumber.toFloat()))
            count++
        }
        val barDataset = BarDataSet(data,getString(R.string.accident_times))
        barDataset.setColor(color)
        val barData = BarData(barDataset)
        bar.data = barData
        setHBarStyle(bar,map)
        bar.invalidate()
    }

    /*
        Set the style of Horizontal Bar Chart
     */
    private fun setHBarStyle(bar:HorizontalBarChart, map:HashMap<Int,String>){
        val marker: IMarker = YourMarkerView(activity,com.example.safodel.R.layout.analysis_bar_content)
        bar.marker = marker
        bar.axisLeft.setDrawGridLines(false)
        bar.axisRight.setDrawGridLines(false)
        bar.xAxis.setDrawGridLines(false)
        bar.axisRight.isEnabled = false
        bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
        bar.axisLeft.valueFormatter = IntegerFormatter()
        bar.xAxis.valueFormatter = StreetNameFormatter(bar,map)
        bar.description.text = ""
    }


            /*
            Get the number for the total Text in the card
         */

//        val job = GlobalScope.launch {
//            val callAsync: Call<SuburbResponse> = suburbInterface.totalRepos(
//                "total",
//                suburbName
//            )
//
//            callAsync.enqueue(object : Callback<SuburbResponse?> {
//                override fun onResponse(call: Call<SuburbResponse?>?, response: Response<SuburbResponse?>) {
//                    if (response.isSuccessful) {
//                        val resultList = response.body()?.suburbAccidents
//                        if(resultList?.isNotEmpty() == true){
//                            accidentsNumber.text = resultList[0].accidents.toString()
//                        }
//                    } else {
//                        Timber.i("Response failed")
//                    }
//                }
//                override fun onFailure(call: Call<SuburbResponse?>?, t: Throwable) {
//                    toast1.cancel()
//                    toast1.setText(t.message)
//                    toast1.show()
//
//                    Timber.i(t.message.toString())
//                }
//            })
//            delay(100L)
//        }

    //        /*
//            Get the data for first bar -- bar2
//         */
//        val job2 = GlobalScope.launch {
//            job.join()
//            val callTime: Call<SuburbTimeResponse> = suburbInterface.timeRepos(
//                "time",
//                suburbName
//            )
//            callTime.enqueue(object : Callback<SuburbTimeResponse?> {
//                override fun onResponse(call: Call<SuburbTimeResponse?>?, response: Response<SuburbTimeResponse?>) {
//                    if (response.isSuccessful) {
//                        val resultList = response.body()!!.suburbTimeAccidents
//                        setLineChart(resultList)
//                    } else {
//                        Timber.i("Response failed")
//                    }
//                }
//                override fun onFailure(call: Call<SuburbTimeResponse?>?, t: Throwable) {
//                    toast2.cancel()
//                    toast2.setText(t.message)
//                    toast2.show()
//                    Timber.i(t.message.toString())
//                }
//            })
//            delay(100L)
//        }

     /*
        Set the Bar Chart of Accident time in this Suburb.
     */
//    private fun setLineChart(list: List<SuburbTimeAccidents>){
//        val color  = ContextCompat.getColor(requireContext(), R.color.primary_green)
//        val colorList = ArrayList<Int>()
//        colorList.add(ColorTemplate.rgb("#8AD0AB"))
//        val data2: MutableList<Entry> = ArrayList()
//
//        for(each in list){
//            data2.add(Entry(each.accidentHours.toFloat(),each.accidentsNumber.toFloat()))
//        }
//        val lineDataset = LineDataSet(data2,getString(R.string.accident_times))
//        lineDataset.setColor(color)
//        lineDataset.valueFormatter = IntegerFormatter()
//        lineDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
//        lineDataset.lineWidth = 2f
//        lineDataset.circleColors = colorList
//        val lineData = LineData(lineDataset)
//
//        lineChart.data = lineData
//        setLineStyle(lineChart)
//        lineChart.invalidate()
//    }

    override fun onClick(position: Int, searchListItem: SearchListItem) {
        searchableDialog.dismiss()
        setDialog()
        callSuburbClient(searchListItem.title)
        spinnerIndex = searchListItem.id
        spinnerText.text = searchListItem.title
    }
}


/*
    Create a formatter of Integer
 */
class IntegerFormatter() : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        if(value < 0.5)
            return 0.toString()
        return value.toInt().toString()
    }
}

class StreetNameFormatter(bar: HorizontalBarChart, map: HashMap<Int,String>):ValueFormatter(){
    val map = map
    val bar = bar
    override fun getFormattedValue(value: Float): String {
        val res = map.get(value.toInt())
        bar.zoomOut()

        if(res != null)
            return res
        return "NULL"

    }

}


