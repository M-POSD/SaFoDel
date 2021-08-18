package com.example.safodel.fragment.menuB

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
    private var mapView: MapView? = null
    private lateinit var permissionsManager: PermissionsManager
    var latLng = LatLng(-37.876823, 145.045837)


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.let { Mapbox.getInstance(it.application,getString(R.string.mapbox_access_token)) }
        _binding = FragmentMapBinding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root
        val mainActivity = activity as MainActivity
        setToolbar(toolbar)
        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync {
            MapboxMap -> MapboxMap.setStyle(Style.LIGHT){
                val position =  CameraPosition.Builder().target(latLng).zoom(13.0).build()
            MapboxMap.cameraPosition = position
            }
        }

        binding.mapView.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
            }
            false
        }
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
        _binding = null
    }

}