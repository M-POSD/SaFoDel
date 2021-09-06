package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.example.safodel.R
import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnalysisFragment : BasicFragment<FragmentAnalysisBinding>(FragmentAnalysisBinding::inflate) {
    private lateinit var spProvince: SmartMaterialSpinner<Any>
    private var suburbList = SuburbList.init()
    private lateinit var suburbInterface: SuburbInterface
    private lateinit var dialog: MaterialDialog
    private lateinit var bar2: BarChart
    private lateinit var bar: HorizontalBarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        // Set Dialog
        dialog = MaterialDialog(requireContext())

        // Set toolbar
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        // Set Chart
        bar2 = binding.barChart2
        bar = binding.barChart
        bar.zoomOut()

        // Setting Spinner
        initSpinner()

        // Retrofit get data
        suburbInterface = SuburbClient.getRetrofitService()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
        Setting the spinner
     */
    private fun initSpinner() {
        spProvince = binding.spinner
        spProvince.item = suburbList
        spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                    setDialog()
                    callSuburbClient(spProvince.item[position].toString())
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    /*
        Setting the dialog when item select
     */
    private fun setDialog(){
        dialog.show {
            message(text = "loading.. Please wait.")
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }


    /*
        Init the data from retrofit 2
     */
    private fun callSuburbClient(suburbName: String){


        /*
            Get the number for the total Text in the card
         */
        val callAsync: Call<SuburbResponse> = suburbInterface.totalRepos(
            "total",
            suburbName
        )
        callAsync.enqueue(object : Callback<SuburbResponse?> {
            override fun onResponse(call: Call<SuburbResponse?>?, response: Response<SuburbResponse?>) {
                if (response.isSuccessful) {
                    dialog.dismiss()
                    val resultList = response.body()?.suburbAccidents
                    if(resultList?.isNotEmpty() == true){
                        binding.accidentNumber.text = resultList[0].accidents.toString()
                    }
                } else {
                    Log.i("Error ", "Response failed")
                }
            }
            override fun onFailure(call: Call<SuburbResponse?>?, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        /*
            Get the data for first bar -- bar2
         */
        val callTime: Call<SuburbTimeResponse> = suburbInterface.timeRepos(
            "time",
            suburbName
        )
        callTime.enqueue(object : Callback<SuburbTimeResponse?> {
            override fun onResponse(call: Call<SuburbTimeResponse?>?, response: Response<SuburbTimeResponse?>) {
                if (response.isSuccessful) {
                    val resultList = response.body()!!.suburbTimeAccidents
                    setBarChart(resultList)
                } else {
                    Log.i("Error ", "Response failed")
                }
            }
            override fun onFailure(call: Call<SuburbTimeResponse?>?, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        /*
            Get the data for first bar -- bar2
         */
        val callStreet: Call<SuburbStreetsResponse> = suburbInterface.streetRepos(
            "streets",
            suburbName
        )
        callStreet.enqueue(object : Callback<SuburbStreetsResponse?> {
            override fun onResponse(call: Call<SuburbStreetsResponse?>?, response: Response<SuburbStreetsResponse?>) {
                if (response.isSuccessful) {
                    val resultList = response.body()!!.suburbStreetsAccidents
                    setStreetsBarChat(resultList)
                } else {
                    Log.i("Error ", "Response failed")
                }
            }
            override fun onFailure(call: Call<SuburbStreetsResponse?>?, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })


    }

    /*
        Set the Bar Chart of Accident time in this Suburb.
     */
    private fun setBarChart(list: List<SuburbTimeAccidents>){
        val color  = ContextCompat.getColor(requireContext(), R.color.third_green)
        val data2: MutableList<BarEntry> = ArrayList()
        val lackData: ArrayList<Int> = ArrayList()
        for(i in 0..23)
            lackData.add(i)
        for(i in list){
            if(lackData.contains(i.accidentHours))
                lackData.remove(i.accidentHours)
        }
        for(each in list){
            data2.add(BarEntry(each.accidentHours.toFloat(),each.accidentsNumber.toFloat()))
        }
        for(each in lackData)
            data2.add(BarEntry(each.toFloat(),0.05f))
        val barDataset = BarDataSet(data2,"Accident Times")
        barDataset.setColor(color)
        barDataset.valueFormatter = IntegerFormatter()
        val barData = BarData(barDataset)

        bar2.data = barData
        setBarStyle(bar2)
        bar2.invalidate()
    }

    private fun setStreetsBarChat(list: List<SuburbStreetsAccidents>){
        val color  = ContextCompat.getColor(requireContext(), R.color.third_green)
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
        val barDataset = BarDataSet(data,"Accident Times")
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
        bar.axisLeft.setDrawGridLines(false)
        bar.axisRight.setDrawGridLines(false)
        bar.xAxis.setDrawGridLines(false)
        bar.axisRight.isEnabled = false
        bar.xAxis.position = XAxis.XAxisPosition.BOTTOM
        bar.axisLeft.valueFormatter = IntegerFormatter()
        bar.xAxis.valueFormatter = StreetNameFormatter(bar,map)
        bar.xAxis.setAvoidFirstLastClipping(true)
        bar.xAxis.setCenterAxisLabels(true)
        bar.description.text = ""
    }


    /*
        Set the style of bar2
     */
    private fun setBarStyle(bar2:BarChart){
        bar2.axisLeft.setDrawGridLines(false)
        bar2.xAxis.setDrawGridLines(false)
        bar2.xAxis.position = XAxis.XAxisPosition.BOTTOM
        bar2.axisRight.isEnabled = false
        bar2.xAxis.axisMinimum = 0f
        bar2.xAxis.axisMaximum = 23f
        bar2.axisLeft.valueFormatter = IntegerFormatter()
        bar2.description.text = ""
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
