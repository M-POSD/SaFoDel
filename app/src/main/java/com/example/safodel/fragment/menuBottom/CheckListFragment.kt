package com.example.safodel.fragment.menuBottom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.safodel.R
import com.example.safodel.databinding.FragmentChecklistBinding
import com.example.safodel.fragment.BasicFragment

class CheckListFragment :
    BasicFragment<FragmentChecklistBinding>(FragmentChecklistBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        setToolbarReturn(toolbar)

        // set up the default text and image view
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
//        binding.gear2.currentPageText.text = getString(R.string.gear2_name)
//        binding.gear2.notification.text = getString(R.string.gear2_slang)

        binding.checklist.checkbox1.checkbox.text =
            getString(R.string.gear_check_list_item1)
        binding.checklist.checkbox1.checkboxImage.setImageResource(R.drawable.helmet2)
        binding.checklist.checkbox2.checkbox.text =
            getString(R.string.gear_check_list_item2)
        binding.checklist.checkbox2.checkboxImage.setImageResource(R.drawable.bicycle_with_light)
        binding.checklist.checkbox3.checkbox.text =
            getString(R.string.gear_check_list_item3)
        binding.checklist.checkbox3.checkboxImage.setImageResource(R.drawable.mask)
        binding.checklist.checkbox4.checkbox.text =
            getString(R.string.gear_check_list_item4)
        binding.checklist.checkbox4.checkboxImage.setImageResource(R.drawable.vest)
        binding.checklist.checkbox5.checkbox.text =
            getString(R.string.gear_check_list_item5)
        binding.checklist.checkbox5.checkboxImage.setImageResource(R.drawable.gloves)
        binding.checklist.checkbox6.checkbox.text =
            getString(R.string.gear_check_list_item6)
        binding.checklist.checkbox6.checkboxImage.setImageResource(R.drawable.hand_sanitizer)
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

    // set the checkbox was clicked by user or not
    private fun configCheckboxClicked() {
        binding.checklist.checkbox1.checkbox.isChecked = getCheckboxSharePrefer(1)
        binding.checklist.checkbox2.checkbox.isChecked = getCheckboxSharePrefer(2)
        binding.checklist.checkbox3.checkbox.isChecked = getCheckboxSharePrefer(3)
        binding.checklist.checkbox4.checkbox.isChecked = getCheckboxSharePrefer(4)
        binding.checklist.checkbox5.checkbox.isChecked = getCheckboxSharePrefer(5)
        binding.checklist.checkbox6.checkbox.isChecked = getCheckboxSharePrefer(6)
    }

    // record if the user tick the check box
    private fun configCheckboxClickListener() {
        binding.checklist.checkbox1.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(1, true)
            } else {
                keepCheckboxSharePrefer(1, false)
            }
        })
        binding.checklist.checkbox2.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(2, true)
            } else {
                keepCheckboxSharePrefer(2, false)
            }
        })
        binding.checklist.checkbox3.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(3, true)
            } else {
                keepCheckboxSharePrefer(3, false)
            }
        })
        binding.checklist.checkbox4.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(4, true)
            } else {
                keepCheckboxSharePrefer(4, false)
            }
        })
        binding.checklist.checkbox5.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(5, true)
            } else {
                keepCheckboxSharePrefer(5, false)
            }
        })
        binding.checklist.checkbox6.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                keepCheckboxSharePrefer(6, true)
            } else {
                keepCheckboxSharePrefer(6, false)
            }
        })
    }
}
