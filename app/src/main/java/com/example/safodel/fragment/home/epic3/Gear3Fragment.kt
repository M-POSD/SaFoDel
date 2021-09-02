package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GearStandardAdapter
import com.example.safodel.databinding.FragmentGear3Binding

import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GearStandard

class Gear3Fragment : BasicFragment<FragmentGear3Binding>(FragmentGear3Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var gearStandards: MutableList<GearStandard>
    private lateinit var adapter: GearStandardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear3Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        configDefaultTextView()
        configRecycleView()
        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configDefaultTextView() {
        binding.gear3.currentPageText.text = "Australian standards for the safety gear"

        // HtmlCompat -> allow app to use html format
        binding.gear3.notification.text = HtmlCompat.fromHtml(
            "Bicycle helmets should have a sticker showing the Australian Standard " +
                    "<font color='#EE0000'>AS 2063, AS/NZS 2063</font><br>",
            HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        gearStandards =  GearStandard.init()

        adapter = GearStandardAdapter(requireActivity(), gearStandards)

        binding.gear3.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.gear3.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.gear3.recyclerView.layoutManager = layoutManager
    }

}
