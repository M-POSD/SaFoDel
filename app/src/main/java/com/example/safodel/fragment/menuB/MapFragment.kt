package com.example.safodel.fragment.menuB

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import java.util.*


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
    private var mapView: MapView? = null
    private lateinit var permissionsManager: PermissionsManager
    val defaultLatLng = LatLng(-37.876823, 145.045837)


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
        setToolbarBasic(toolbar)
        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync {
            MapboxMap -> MapboxMap.setStyle(Style.LIGHT){
                val position =  CameraPosition.Builder().target(defaultLatLng).zoom(13.0).build()
            MapboxMap.cameraPosition = position
            }
        }
        bottomNavHide() // Bottom navigation hide, when touch the map.
        addressSender()

        return binding.root
    }



    /*
     *   Bottom navigation hide, when touch the map.
     */
    @SuppressLint("ClickableViewAccessibility")
    fun bottomNavHide(){
        val mainActivity = activity as MainActivity
        binding.mapView.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
            }
            false
        }
    }

    fun addressSender(){
        var latLng = defaultLatLng
        var position = CameraPosition.Builder().target(latLng).zoom(13.0)
            .tilt(30.0).build()
        val geocoder = Geocoder(context, Locale.getDefault())
        binding.addressButton.setOnClickListener {
            if(binding.addressEditText.text.toString() == "")
                Toast.makeText(context,"Please enter the address", Toast.LENGTH_SHORT).show()
            else {
                val address = geocoder.getFromLocationName(binding.addressEditText.text.toString(), 1)
                if (!address.isEmpty()) {
                    latLng = LatLng(address[0].latitude, address[0].longitude)
                    position = CameraPosition.Builder().target(latLng).zoom(13.0)
                        .tilt(30.0).build()
                } else
                    Toast.makeText(context, "Can not find this address.", Toast.LENGTH_SHORT).show()
                mapView?.getMapAsync { mapboxMap ->
                    mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                        mapboxMap.cameraPosition
                        val symbol = SymbolManager(mapView!!, mapboxMap, it)
                        symbol.iconAllowOverlap = true
                        it.addImage(
                            "Marker",
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.mapbox_marker_icon_default
                            )
                        )
                        symbol.create(SymbolOptions().withLatLng(latLng).withIconImage("Marker"))
                    }
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 4000)
                }
            }
        }
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