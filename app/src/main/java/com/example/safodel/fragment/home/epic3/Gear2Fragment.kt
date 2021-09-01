package com.example.safodel.fragment.home.epic3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.safodel.R
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

        binding.gear2.currentPageText.text =
            "A checklist of necessary safety equipment"
        binding.gear2.notification.text =
            "Worried about forgetting essential safety gear.\nFollow this checklist for a quick heads up!"

        setToolbarReturn(toolbar)
        configDefaultView()

        // set up the checkbox on the checkbox list has been clicked by users or not
        configCheckboxClicked()

        // set up when the check box is clicked, the record will be recorded in share preference
        configCheckboxClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // made up the check list to display
    private fun configDefaultView() {
        binding.gear2.detailCardV3.checkbox1.checkbox.text = "Helmet"
        binding.gear2.detailCardV3.checkbox1.checkboxImage.setImageResource(R.drawable.helmet2)
        binding.gear2.detailCardV3.checkbox2.checkbox.text = "Rear and Front lights"
        binding.gear2.detailCardV3.checkbox2.checkboxImage.setImageResource(R.drawable.bicycle_with_light)
        binding.gear2.detailCardV3.checkbox3.checkbox.text = "Mask and face over"
        binding.gear2.detailCardV3.checkbox3.checkboxImage.setImageResource(R.drawable.mask)
        binding.gear2.detailCardV3.checkbox4.checkbox.text = "Reflective vest"
        binding.gear2.detailCardV3.checkbox4.checkboxImage.setImageResource(R.drawable.vest)
        binding.gear2.detailCardV3.checkbox5.checkbox.text = "Gloves"
        binding.gear2.detailCardV3.checkbox5.checkboxImage.setImageResource(R.drawable.gloves)
        binding.gear2.detailCardV3.checkbox6.checkbox.text = "Hand Sanitizer"
        binding.gear2.detailCardV3.checkbox6.checkboxImage.setImageResource(R.drawable.hand_sanitizer)

    }

    // keep the record of the checkbox clicked
    private fun keepCheckboxSharePrefer(checkbox_num: Int, isChecked: Boolean) {
        val checkbox = "checkbox$checkbox_num"
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            checkbox, Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putBoolean(checkbox, isChecked)
        spEditor.apply()
    }

    // get the previous checkbox clicked by the user
    private fun getCheckboxSharePrefer(checkbox_num: Int): Boolean {
        val checkbox = "checkbox$checkbox_num"
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            checkbox,
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(checkbox, false)
    }

    private fun configCheckboxClicked() {
        binding.gear2.detailCardV3.checkbox1.checkbox.isChecked = getCheckboxSharePrefer(1)
        binding.gear2.detailCardV3.checkbox2.checkbox.isChecked = getCheckboxSharePrefer(2)
        binding.gear2.detailCardV3.checkbox3.checkbox.isChecked = getCheckboxSharePrefer(3)
        binding.gear2.detailCardV3.checkbox4.checkbox.isChecked = getCheckboxSharePrefer(4)
        binding.gear2.detailCardV3.checkbox5.checkbox.isChecked = getCheckboxSharePrefer(5)
        binding.gear2.detailCardV3.checkbox6.checkbox.isChecked = getCheckboxSharePrefer(6)

//        binding.gear2.detailCardV3.checkbox1.checkboxImage.setImageResource(R.drawable.checkbox2)
//        binding.gear2.detailCardV3.checkbox2.checkboxImage.setImageResource(R.drawable.checkbox2)
//        binding.gear2.detailCardV3.checkbox3.checkboxImage.setImageResource(R.drawable.checkbox2)
//        binding.gear2.detailCardV3.checkbox4.checkboxImage.setImageResource(R.drawable.checkbox2)
//        binding.gear2.detailCardV3.checkbox5.checkboxImage.setImageResource(R.drawable.checkbox2)
//        binding.gear2.detailCardV3.checkbox6.checkboxImage.setImageResource(R.drawable.checkbox2)


    }

    private fun configCheckboxClickListener() {
        binding.gear2.detailCardV3.checkbox1.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(1, true)
            } else {
                keepCheckboxSharePrefer(1, false)
            }
        })
        binding.gear2.detailCardV3.checkbox2.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(2, true)
            } else {
                keepCheckboxSharePrefer(2, false)
            }
        })
        binding.gear2.detailCardV3.checkbox3.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(3, true)
            } else {
                keepCheckboxSharePrefer(3, false)
            }
        })
        binding.gear2.detailCardV3.checkbox4.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(4, true)
            } else {
                keepCheckboxSharePrefer(4, false)
            }
        })
        binding.gear2.detailCardV3.checkbox5.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(5, true)
            } else {
                keepCheckboxSharePrefer(5, false)
            }
        })
        binding.gear2.detailCardV3.checkbox6.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(6, true)
            } else {
                keepCheckboxSharePrefer(6, false)
            }
        })
    }

}
