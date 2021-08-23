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
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import java.util.*


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),OnMapReadyCallback,PermissionsListener {
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var permissionsManager: PermissionsManager
    private val defaultLatLng = LatLng(-37.876823, 145.045837)


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.let { Mapbox.getInstance(it.application,getString(R.string.mapbox_access_token)) }
        _binding = FragmentMapBinding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        val mainActivity = activity as MainActivity
        mapView = binding.mapView

        /*
         *   Bottom navigation hide, when touch the map.
         */
        binding.mapView.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
            }
            false
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        addressSender()
        binding.recenter.setOnClickListener {
            mapboxMap.setStyle(Style.LIGHT){
                enableLocationComponent(it)
            }
        }
        return binding.root
    }


    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setMaxZoomPreference(15.0)
        mapboxMap.setMinZoomPreference(12.0)
        this.mapboxMap = mapboxMap
        this.mapboxMap.setStyle(Style.LIGHT){
            val position =  CameraPosition.Builder().target(defaultLatLng).zoom(13.0).build()
            mapboxMap.cameraPosition = position
            enableLocationComponent(it)
        }
    }

    @SuppressWarnings("MissingPermission")
    fun enableLocationComponent(loadMapStyle: Style){
        if(PermissionsManager.areLocationPermissionsGranted(context)){
            val customLocationComponentOptions: LocationComponentOptions? = context?.let {
                LocationComponentOptions
                    .builder(it)
                    .pulseEnabled(true).build()
            }

            val locationComponent: LocationComponent = mapboxMap.locationComponent

            context?.let {
                LocationComponentActivationOptions.builder(it,loadMapStyle)
                    .locationComponentOptions(customLocationComponentOptions).build()
            }?.let {
                locationComponent.activateLocationComponent(it)
            }

            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.NORMAL
        }
        else{
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(activity)
        }

    }

    private fun addressSender(){
        var latLng = defaultLatLng
        var position = CameraPosition.Builder().target(latLng).zoom(13.0)
            .tilt(30.0).build()
        val geocoder = Geocoder(context, Locale.getDefault())
        binding.addressButton.setOnClickListener {
            if(binding.addressEditText.text.toString() == "")
                Toast.makeText(context,"Please enter the address", Toast.LENGTH_SHORT).show()
            else {
                val address = geocoder.getFromLocationName(binding.addressEditText.text.toString(), 1)
                if (address.isNotEmpty()) {
                    latLng = LatLng(address[0].latitude, address[0].longitude)
                    position = CameraPosition.Builder().target(latLng).zoom(13.0)
                        .tilt(30.0).build()
                } else
                    Toast.makeText(context, "Can not find this address.", Toast.LENGTH_SHORT).show()
                    mapboxMap.setStyle(Style.LIGHT) {
                    mapboxMap.cameraPosition
                    val symbol = SymbolManager(mapView, mapboxMap, it)
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


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        _binding = null
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(context, "Need the user location permission", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if(granted){
            mapboxMap.getStyle {
                enableLocationComponent(it)
            }
        }
        else{
            Toast.makeText(context, "user location permission not granted", Toast.LENGTH_LONG).show()
        }
    }





}