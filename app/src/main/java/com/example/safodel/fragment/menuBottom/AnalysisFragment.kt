package com.example.safodel.fragment.menuBottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.ajithvgiri.searchdialog.OnSearchItemSelected
import com.ajithvgiri.searchdialog.SearchListItem
import com.ajithvgiri.searchdialog.SearchableDialog
import com.example.safodel.R
import com.example.safodel.adapter.AnalysisViewAdapter
import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import com.example.safodel.ui.analysis.YourMarkerView
import com.example.safodel.ui.main.MainActivity

private var spinnerIndex = 0  // To get the index when item select in spinner.

class AnalysisFragment : BasicFragment<FragmentAnalysisBinding>(FragmentAnalysisBinding::inflate),OnSearchItemSelected {

    /*
        static value
     */
    private var suburbName = "MELBOURNE"  // default value
    private var suburbList = SuburbList.init()  // setting the suburb list

    /*
        basic object
     */
    private lateinit var suburbInterface: SuburbInterface
    private lateinit var dialog: MaterialDialog
    private lateinit var bar: HorizontalBarChart
    private lateinit var mainActivity: MainActivity
    private lateinit var scrollView: View
    private lateinit var toolbar: Toolbar
    private lateinit var searchableDialog: SearchableDialog
    private lateinit var spinnerText: TextView
    private lateinit var adapter: AnalysisViewAdapter


    /*
        Toast
     */
    private lateinit var toast1: Toast
    private lateinit var toast2: Toast
    private lateinit var toast3: Toast


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        /*
            Setting basic object
         */
        toast1 = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        toast2 = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        toast3 = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        scrollView = binding.scrollView
        spinnerText = binding.spinnerText
        toolbar = binding.toolbar.root

        /*
            set dialog
         */
        dialog = MaterialDialog(requireContext())

        /*
            set toolbar
         */
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        /*
            Set chart
         */
        bar = binding.barChart
        bar.zoomOut()
        bar.xAxis.labelCount = 5

        setMarginView()  // setting height of scroll view


        suburbInterface = SuburbClient.getSuburbService()  // Retrofit get data

        /*
            For Viewpager
         */
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

    /*
        When go to this page run
     */
    fun start(){
        setDialog()
        callSuburbClient(suburbName)
    }

    /*
        Setting the spinner
     */
    private fun initSpinner() {
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

    /*
        setting basic view draw
     */
    private fun setMarginView(){
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

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
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
                    toast3.setText(getString(R.string.trend_null))
                    toast3.show()
                }
            })
        }



    }


    /*
        Setting the bar chart
     */
    private fun setStreetsBarChat(list: List<SuburbStreetsAccidents>){
        val color  = ContextCompat.getColor(requireContext(), R.color.primary_green)
        val rawList = list.sortedBy {
            it.accidentsNumber
        }
        val data: MutableList<BarEntry> = ArrayList()
        val map :HashMap<Int,String>  = HashMap()
        for ((count, each) in rawList.withIndex()){
            map[count] = each.accidentAddressName
            data.add(BarEntry(count.toFloat(),each.accidentsNumber.toFloat()))
        }
        val barDataset = BarDataSet(data,getString(R.string.accident_times))
        barDataset.color = color
        val barData = BarData(barDataset)
        bar.data = barData
        setHBarStyle(bar,map)
        bar.invalidate()
    }

    /*
        Set the style of Horizontal Bar Chart
     */
    private fun setHBarStyle(bar:HorizontalBarChart, map:HashMap<Int,String>){
        val marker: IMarker = YourMarkerView(activity,R.layout.analysis_bar_content)
        bar.marker = marker
        bar.axisLeft.setDrawGridLines(false)
        bar.axisRight.setDrawGridLines(false)
        bar.xAxis.setDrawGridLines(false)
        bar.axisRight.isEnabled = false
        bar.xAxis.isGranularityEnabled = true
        bar.axisLeft.isGranularityEnabled = true
        bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
        bar.axisLeft.valueFormatter = IntegerFormatter()
        bar.xAxis.valueFormatter = StreetNameFormatter(bar,map)
        bar.description.text = ""
    }

    /*
        When click the item in spinner, function in OnSearchItemSelected implementation.
     */
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
class IntegerFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        if(value < 0.5)
            return 0.toString()
        return value.toInt().toString()
    }
}

/*
    Formatter
 */
class StreetNameFormatter(private val bar: HorizontalBarChart, val map: HashMap<Int, String>):ValueFormatter(){
    override fun getFormattedValue(value: Float): String {
        val res = map[value.toInt()]
        bar.zoomOut()

        if(res != null)
            return res
        return "NULL"

    }

}


