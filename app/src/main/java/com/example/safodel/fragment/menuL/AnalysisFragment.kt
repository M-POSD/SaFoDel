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
import com.anychart.AnyChart

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.gauge.pointers.Bar
import com.anychart.palettes.RangeColors

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.example.safodel.R

import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mapbox.maps.extension.style.expressions.dsl.generated.format
import com.mapbox.maps.extension.style.types.formatted
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnalysisFragment : BasicFragment<FragmentAnalysisBinding>(FragmentAnalysisBinding::inflate) {
    private lateinit var spProvince: SmartMaterialSpinner<Any>
    private var suburbList = SuburbList.init()
    private lateinit var suburbInterface: SuburbInterface
    private lateinit var dialog: MaterialDialog
    private lateinit var bar: Cartesian
    private lateinit var bar2: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        dialog = MaterialDialog(requireContext())
        setToolbarBasic(toolbar)
        bar = AnyChart.column().title("Accident time")
            .palette(RangeColors.instantiate().items("#8AD0AB","#8AD0AB"))
        bar.tooltip().titleFormat("Times: {%value}").format("Accidents Hour: {%x}")

        // 2
        bar2 = binding.barChart2

        binding.barChart.setChart(bar)

        initSpinner()
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

        val callTime: Call<SuburbTimeResponse> = suburbInterface.timeRepos(
            "time",
            suburbName
        )

        callTime.enqueue(object : Callback<SuburbTimeResponse?> {
            override fun onResponse(call: Call<SuburbTimeResponse?>?, response: Response<SuburbTimeResponse?>) {
                if (response.isSuccessful) {
                    val resultList = response.body()!!.suburbTimeAccidents
                    setBarChat(resultList)
                } else {
                    Log.i("Error ", "Response failed")
                }
            }
            override fun onFailure(call: Call<SuburbTimeResponse?>?, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setBarChat(list: List<SuburbTimeAccidents>){
        var color  = ContextCompat.getColor(requireContext(), R.color.third_green)
        val data: ArrayList<DataEntry> = ArrayList()
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
            data.add(ValueDataEntry(each.accidentHours,each.accidentsNumber))
        }
        for(each in lackData)
            data.add(ValueDataEntry(each,0))
        bar.data(data)
        val barDataset = BarDataSet(data2,"Accident Times")
        barDataset.setColor(color)
        barDataset.valueFormatter = IntegerFormatter()
        val barData = BarData(barDataset)

        bar2.data = barData
        bar2.axisLeft.setDrawGridLines(false)
        bar2.xAxis.setDrawGridLines(false)
        bar2.xAxis.position = XAxis.XAxisPosition.BOTTOM
        bar2.axisRight.isEnabled = false
        bar2.xAxis.axisMinimum = 0f
        bar2.axisLeft.setDrawZeroLine(true)
        bar2.axisLeft.valueFormatter = IntegerFormatter()
        bar2.description.text = ""
        bar2.invalidate()

    }
}

class IntegerFormatter() : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return value.toInt().toString()
    }

}
