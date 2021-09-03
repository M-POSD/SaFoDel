package com.example.safodel.fragment.menuL

import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.refactor.lib.colordialog.ColorDialog
import cn.refactor.lib.colordialog.PromptDialog
import com.example.safodel.databinding.FragmentAppIntroBinding
import com.example.safodel.fragment.BasicFragment


class AppIntroFragment : BasicFragment<FragmentAppIntroBinding>(FragmentAppIntroBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppIntroBinding.inflate(inflater,container,false)

        val toolbar = binding.toolbar.root

        binding.about.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.about.setOnClickListener {
            PromptDialog(context).setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("Topic")
                .setContentText("Educating Food Delivery Bike Riders\n")
                .setPositiveListener ("OK"){
                    it.dismiss()
                }.show()
        }

        binding.audience.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.audience.setOnClickListener {
            PromptDialog(context).setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("Audience")
                .setContentText("Potential and already existing food delivery bike riders\n")
                .setPositiveListener("OK") {
                    it.dismiss()
                }.show()
        }




        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}