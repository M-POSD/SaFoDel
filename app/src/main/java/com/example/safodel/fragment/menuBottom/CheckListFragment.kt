package com.example.safodel.fragment.menuBottom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
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

    private lateinit var helmetImageView: ImageView
    private lateinit var maskImageView: ImageView
    private lateinit var glovesImageView: ImageView
    private lateinit var lightImageView: ImageView
    private lateinit var sanitizerImageView: ImageView
    private lateinit var vestImageView: ImageView
    private lateinit var backpackImageView: ImageView

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

        setToolbarReturn(toolbar)

        configNotificationView()

        // set up the checkbox on the checkbox list has been clicked by users or not
        configCheckboxClicked()

        // set up the default text and image view
        configDefaultView()

        // set up when the check box is clicked, the record will be recorded in share preference
        configCheckboxClickListener()

        // set up image view
        configImageView()


        model.getCheck().observe(viewLifecycleOwner, { t ->
            if (t == true) {
                binding.checklistNotificationIcon.setImageResource(R.drawable.well_down)
                binding.checklistNotificationText.setBackgroundResource(R.drawable.correct_info_border_bg)
                binding.checklistNotificationText.text =
                    getString(R.string.checklist_notification_prepared)
            } else {
                binding.checklistNotificationText.setBackgroundResource(R.drawable.wrong_info_border_bg)
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

    private fun configImageView() {
        helmetImageView = binding.checklist.clHelmet
        maskImageView = binding.checklist.clMask
        glovesImageView = binding.checklist.clGloves
        lightImageView = binding.checklist.clLight
        sanitizerImageView = binding.checklist.clSanitizer
        vestImageView = binding.checklist.clVest
        backpackImageView = binding.checklist.clBackpack

        if(checklist.helmetCheckbox1.checkbox.isChecked) {
            helmetImageView.alpha = 1f
        }
        if(checklist.maskCheckbox2.checkbox.isChecked) {
            maskImageView.alpha = 1f
        }
        if(checklist.glovesCheckbox3.checkbox.isChecked) {
            glovesImageView.alpha = 1f
        }
        if(checklist.lightCheckbox4.checkbox.isChecked) {
            lightImageView.alpha = 1f
        }
        if(checklist.sanitizerCheckbox5.checkbox.isChecked) {
            sanitizerImageView.alpha = 1f
        }
        if(checklist.helmetCheckbox1.checkbox.isChecked) {
            helmetImageView.alpha = 1f
        }
        if(checklist.vestCheckbox6.checkbox.isChecked) {
            vestImageView.alpha = 1f
        }

        if(checklist.backpackCheckbox7.checkbox.isChecked) {
            backpackImageView.alpha = 1f
        }
    }

    // made up the check list to display
    private fun configDefaultView() {
        checklist.helmetCheckbox1.checkbox.text =
            getString(R.string.helmet_checkbox_1)
        checklist.maskCheckbox2.checkbox.text =
            getString(R.string.mask_checkbox_2)
        checklist.glovesCheckbox3.checkbox.text =
            getString(R.string.gloves_checkbox_3)
        checklist.lightCheckbox4.checkbox.text =
            getString(R.string.light_checkbox_4)
        checklist.sanitizerCheckbox5.checkbox.text =
            getString(R.string.sanitizer_checkbox_5)
        checklist.vestCheckbox6.checkbox.text =
            getString(R.string.vest_checkbox_6)
        checklist.backpackCheckbox7.checkbox.text =
            getString(R.string.backpack_checkbox_7)
    }

    private fun configNotificationView() {
        if (mainActivity.getCheckboxSharePrefer(1) && mainActivity.getCheckboxSharePrefer(2)
            && mainActivity.getCheckboxSharePrefer(3) && mainActivity.getCheckboxSharePrefer(4)
            && mainActivity.getCheckboxSharePrefer(5) && mainActivity.getCheckboxSharePrefer(6)
            && mainActivity.getCheckboxSharePrefer(7)
        ) {
            model.setCheck(true)
        } else {
            model.setCheck(false)
        }

    }

    // set the checkbox was clicked by user or not
    private fun configCheckboxClicked() {
        checklist.helmetCheckbox1.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(1)
        checklist.maskCheckbox2.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(2)
        checklist.glovesCheckbox3.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(3)
        checklist.lightCheckbox4.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(4)
        checklist.sanitizerCheckbox5.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(5)
        checklist.vestCheckbox6.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(6)
        checklist.backpackCheckbox7.checkbox.isChecked = mainActivity.getCheckboxSharePrefer(7)
    }

    // record if the user tick the check box
    private fun configCheckboxClickListener() {
        checklist.helmetCheckbox1.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(1, true)
                imageAnimation(helmetImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(1, false)
                helmetImageView.alpha = 0f
            }
            configNotificationView()
        })
        checklist.maskCheckbox2.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(2, true)
                imageAnimation(maskImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(2, false)
                maskImageView.alpha = 0f
            }
            configNotificationView()
        })
        checklist.glovesCheckbox3.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(3, true)
                imageAnimation(glovesImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(3, false)
                glovesImageView.alpha = 0f
            }
            configNotificationView()
        })
        checklist.lightCheckbox4.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(4, true)
                imageAnimation(lightImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(4, false)
                lightImageView.alpha = 0f
            }
            configNotificationView()
        })
        checklist.sanitizerCheckbox5.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(5, true)
                imageAnimation(sanitizerImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(5, false)
                sanitizerImageView.alpha = 0f
            }
            configNotificationView()
        })
        checklist.vestCheckbox6.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(6, true)
                imageAnimation(vestImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(6, false)
                vestImageView.alpha = 0f
            }
            configNotificationView()
        })

        checklist.backpackCheckbox7.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainActivity.keepCheckboxSharePrefer(7, true)
                imageAnimation(backpackImageView)
            } else {
                mainActivity.keepCheckboxSharePrefer(7, false)
                backpackImageView.alpha = 0f
            }
            configNotificationView()
        })
    }

    private fun imageAnimation(imageView: ImageView) {
        var objectAnimator: ObjectAnimator =
            ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f).setDuration(500)
        var animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator)
        animatorSet.start()
    }

}
