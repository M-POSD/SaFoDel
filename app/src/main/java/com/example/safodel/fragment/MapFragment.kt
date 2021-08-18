package com.example.safodel.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.ui.main.MainActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import javax.microedition.khronos.egl.EGL


class MapFragment:BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
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

        /**
         * When touch the screen, bottom navigation set as INVISIBLE, Tap Up to show the navigation
         */
        binding.mapView.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
            }
            false  // Make sure finger can move the map
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