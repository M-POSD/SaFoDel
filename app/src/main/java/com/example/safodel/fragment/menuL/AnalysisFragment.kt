package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.example.safodel.R
import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.databinding.FragmentExamBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.SuburbList
import com.example.safodel.model.SuburbResponse
import com.example.safodel.model.WeatherResponse
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        dialog = MaterialDialog(requireContext())
        setToolbarBasic(toolbar)
        initSpinner()
        suburbInterface = SuburbClient.getRetrofitService()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSpinner() {
        spProvince = binding.spinner
        spProvince.item = suburbList
        spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                    dialog.show {
                        title(text = "loading.. Please wait.")
                        cancelable(false)
                        cancelOnTouchOutside(false)
                    }
                    callSuburbClient(spProvince.item[position].toString())

            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

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
    }
}