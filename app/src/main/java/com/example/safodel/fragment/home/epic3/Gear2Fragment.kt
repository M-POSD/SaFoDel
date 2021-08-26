package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentGear2Binding

import com.example.safodel.fragment.BasicFragment

class Gear2Fragment : BasicFragment<FragmentGear2Binding>(FragmentGear2Binding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear2Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.gear2.extremeSmall.editText.text =
            "A checklist of necessary safety equipment"
        binding.gear2.notification.text = "Worried about forgetting essential safety gear. Follow this checklist for a quick heads up!"

        setToolbarReturn(toolbar)




//        // set string in checklist
//        var checkListString = ""
//        for (item in getCheckList()) {
//            checkListString += item + "\n"
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // made up the check list to display
    private fun getCheckList(): MutableList<String> {
        var checkList: MutableList<String> = ArrayList()
        checkList.add("Helmet")
        checkList.add("Working breaks")
        checkList.add("Lights & reflectors")
        checkList.add("High visibility clothing ")
        checkList.add("Check bicycle chain")
        checkList.add("Check tyres")
        checkList.add("Check pedals spin freely")
        return checkList
    }

}
