package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast

import com.afollestad.materialdialogs.MaterialDialog
import com.anychart.AnyChart

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.palettes.RangeColors

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner

import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnalysisFragment : BasicFragment<FragmentAnalysisBinding>(FragmentAnalysisBinding::inflate) {
    private lateinit var spProvince: SmartMaterialSpinner<Any>
    private var suburbList = SuburbList.init()
    private lateinit var suburbInterface: SuburbInterface
    private lateinit var dialog: MaterialDialog
    private lateinit var bar: Cartesian
    private lateinit var bar2: Cartesian

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
        binding.barChart.setChart(bar)

//        // i am just for testing purpose
        testingChart()
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
        val data: ArrayList<DataEntry> = ArrayList()
        val lackData: ArrayList<Int> = ArrayList()
        for(i in 0..23)
            lackData.add(i)
        for(i in list){
            if(lackData.contains(i.accidentHours))
                lackData.remove(i.accidentHours)
        }
        for(each in list){
            data.add(ValueDataEntry(each.accidentHours,each.accidentsNumber))
        }
        for(each in lackData)
            data.add(ValueDataEntry(each,0))
        bar.data(data)

        // testing
        bar2.data(data)
        Log.d("Set the bar chart", "Success!!  " + data.size)
    }

    private fun testingChart() {
        bar2 = AnyChart.column().title("Accident time")
            .palette(RangeColors.instantiate().items("#8AD0AB","#8AD0AB"))
        bar2.tooltip().titleFormat("Times: {%value}").format("Accidents Hour: {%x}")
        binding.barChart2.setChart(bar2)
        binding.barChart3.setChart(bar2)
    }

}