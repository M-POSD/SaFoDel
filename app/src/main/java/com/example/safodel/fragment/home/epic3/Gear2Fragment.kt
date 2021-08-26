package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GearAdapter
import com.example.safodel.databinding.FragmentGear2Binding
import com.example.safodel.databinding.FragmentGear3Binding

import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Gear

class Gear2Fragment : BasicFragment<FragmentGear2Binding>(FragmentGear2Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var gears: MutableList<Gear>
    private lateinit var adapter: GearAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear2Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        binding.gear2.extremeSmall.editText.text = "A detailed checklist of necessary safety equipment"
        binding.gear2.notification.text = "Prepare all gears listed below??????"

        setToolbarReturn(toolbar)

        var checkListString = ""
        for (item in getCheckList()) {
            checkListString += item + "\n"
        }

        binding.gear2.detailCard.title.text = "Check list"
        binding.gear2.detailCard.subtitle.text = checkListString

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // made up the check list to display
    private fun getCheckList() : MutableList<String>{
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
