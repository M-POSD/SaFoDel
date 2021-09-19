package com.example.safodel.fragment.menuBottom

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.safodel.R
import com.example.safodel.databinding.DetailCardChecklistBinding
import com.example.safodel.databinding.FragmentChecklistBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.CheckListViewModel

class CheckListFragment :
    BasicFragment<FragmentChecklistBinding>(FragmentChecklistBinding::inflate) {
    private lateinit var checklist: DetailCardChecklistBinding
    private lateinit var model: CheckListViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        val toolbar = binding.toolbar.root
        checklist = binding.checklist
        model = ViewModelProvider(requireActivity()).get(CheckListViewModel::class.java)

        configNotificationView()

        setToolbarReturn(toolbar)

        // set up the default text and image view
        configDefaultView()

        // set up the checkbox on the checkbox list has been clicked by users or not
        configCheckboxClicked()

        // set up when the check box is clicked, the record will be recorded in share preference
        configCheckboxClickListener()


        model.getCheck().observe(viewLifecycleOwner, { t ->
            if (t == true) {
                binding.checklistNotificationIcon.setImageResource(R.drawable.well_down)
                binding.checklistNotificationText.text =
                    getString(R.string.checklist_notification_prepared)
            } else {
                binding.checklistNotificationIcon.setImageResource(R.drawable.not_cool)
                binding.checklistNotificationText.text =
                    getString(R.string.checklist_notification_unprepared)
            }
            mainActivity.changeCheckListIcon(t)
        })

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

        checklist.checkbox1.checkbox.text =
            getString(R.string.gear_check_list_item1)
        checklist.checkbox1.checkboxImage.setImageResource(R.drawable.helmet2)
        checklist.checkbox2.checkbox.text =
            getString(R.string.gear_check_list_item2)
        checklist.checkbox2.checkboxImage.setImageResource(R.drawable.bicycle_with_light)
        checklist.checkbox3.checkbox.text =
            getString(R.string.gear_check_list_item3)
        checklist.checkbox3.checkboxImage.setImageResource(R.drawable.mask)
        checklist.checkbox4.checkbox.text =
            getString(R.string.gear_check_list_item4)
        checklist.checkbox4.checkboxImage.setImageResource(R.drawable.vest)
        checklist.checkbox5.checkbox.text =
            getString(R.string.gear_check_list_item5)
        checklist.checkbox5.checkboxImage.setImageResource(R.drawable.gloves)
        checklist.checkbox6.checkbox.text =
            getString(R.string.gear_check_list_item6)
        checklist.checkbox6.checkboxImage.setImageResource(R.drawable.hand_sanitizer)
    }

    private fun configNotificationView() {
        if (mainActivity.getCheckboxSharePrefer(1) && mainActivity.getCheckboxSharePrefer(2)
            && mainActivity.getCheckboxSharePrefer(3) && mainActivity.getCheckboxSharePrefer(4)
            && mainActivity.getCheckboxSharePrefer(5) && mainActivity.getCheckboxSharePrefer(6)
        ) {
            model.setCheck(true)
        } else {
            model.setCheck(false)
        }

    }

    // set the checkbox was clicked by user or not
    private fun configCheckboxClicked() {
        checklist.checkbox1.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(1)
        checklist.checkbox2.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(2)
        checklist.checkbox3.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(3)
        checklist.checkbox4.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(4)
        checklist.checkbox5.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(5)
        checklist.checkbox6.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(6)
    }

    // record if the user tick the check box
    private fun configCheckboxClickListener() {
        checklist.checkbox1.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(1, true)
            } else {
                mainActivity.keepCheckboxSharePrefer(1, false)
            }
            configNotificationView()
        })
        checklist.checkbox2.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(2, true)
            } else {
                mainActivity.keepCheckboxSharePrefer(2, false)
            }
            configNotificationView()
        })
        checklist.checkbox3.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(3, true)
            } else {
                mainActivity.keepCheckboxSharePrefer(3, false)
            }
            configNotificationView()
        })
        checklist.checkbox4.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(4, true)
            } else {
                mainActivity.keepCheckboxSharePrefer(4, false)
            }
            configNotificationView()
        })
        checklist.checkbox5.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(5, true)
            } else {
                mainActivity.keepCheckboxSharePrefer(5, false)
            }
            configNotificationView()
        })
        checklist.checkbox6.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(6, true)
            } else {
                mainActivity.keepCheckboxSharePrefer(6, false)
            }
            configNotificationView()
        })
    }
}
