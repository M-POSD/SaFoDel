package com.example.safodel.fragment.menuB

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color.parseColor
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainer
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
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
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property.*
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.navigation.base.internal.extensions.applyDefaultParams
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.core.directions.session.RoutesRequestCallback
import timber.log.Timber
import java.util.*


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),OnMapReadyCallback,PermissionsListener,MapboxMap.OnMapLongClickListener {

    // Map
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mainActivity : MainActivity

    // Navigation
    private lateinit var mapboxNavigation: MapboxNavigation


    // Permission
    private lateinit var permissionsManager: PermissionsManager

    // Basic value
    private val defaultLatLng = LatLng(-37.876823, 145.045837)
    private val ORIGIN_COLOR = "#32a852" // Green
    private val DESTINATION_COLOR = "#F84D4D" // Red

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.let { Mapbox.getInstance(it.application,getString(R.string.mapbox_access_token)) }
        _binding = FragmentMapBinding.inflate(inflater,container,false)

        // init the view
        mainActivity = activity as MainActivity
        val toolbar = binding.toolbar.root
        mapView = binding.mapView
        setToolbarBasic(toolbar)

        // Navigation
        val mapboxNavigationOptions = MapboxNavigation
            .defaultNavigationOptionsBuilder(mainActivity, getString(R.string.mapbox_access_token))
            .build()
        mapboxNavigation = MapboxNavigation(mapboxNavigationOptions)


        // request permission
        permissionsManager = PermissionsManager(this)
        permissionsManager.requestLocationPermissions(activity)

        // basic location and position
        var latLng = defaultLatLng
        var position = CameraPosition.Builder().target(latLng).zoom(13.0)
            .tilt(30.0).build()
        val geocoder = Geocoder(context, Locale.getDefault())

        /* --- Bottom navigation hide, when touch the map. --- */
//        binding.mapView.setOnTouchListener { _, event ->
//            when(event.action){
//                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
//                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
//            }
//            false
//        }


        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.addressButton.setOnClickListener{
            if(binding.addressEditText.text.toString() == "")
                Toast.makeText(context,"Please enter the address", Toast.LENGTH_SHORT).show()
            else {
                val address = geocoder.getFromLocationName(binding.addressEditText.text.toString(), 1)
                if (address.isNotEmpty()) {
                    latLng = LatLng(address[0].latitude, address[0].longitude)
                    position = CameraPosition.Builder().target(latLng).zoom(13.0)
                        .tilt(30.0).build()
                    setMapboxMap(latLng,position) // set marker
                } else
                    Toast.makeText(context, "Can not find this address.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recenter.setOnClickListener {
            mapboxMap.setStyle(Style.LIGHT){
                enableLocationComponent(it)
            }
        }
        Snackbar.make(binding.coordinator, "long press map to point", LENGTH_SHORT).show()
        return binding.root
    }


    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setMaxZoomPreference(15.0)
        mapboxMap.setMinZoomPreference(5.0)
        this.mapboxMap = mapboxMap
        this.mapboxMap.setStyle(Style.LIGHT){
            val position =  CameraPosition.Builder().target(defaultLatLng).zoom(13.0).build()
            mapboxMap.cameraPosition = position
            enableLocationComponent(it)
            it.addSource(GeoJsonSource("CLICK_SOURCE"))
            it.addSource(GeoJsonSource(
                "ROUTE_LINE_SOURCE_ID",
                GeoJsonOptions().withLineMetrics(true)
            ))


            // Add the destination marker image
            it.addImage(
                "ICON_ID",
                BitmapUtils.getBitmapFromDrawable(
                    context?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1,
                            R.drawable.mapbox_marker_icon_default
                        )
                    }
                )!!)

            // Add the LineLayer below the LocationComponent's bottom layer, which is the
// circular accuracy layer. The LineLayer will display the directions route.
            it.addLayerBelow(
                LineLayer("ROUTE_LAYER_ID", "ROUTE_LINE_SOURCE_ID")
                    .withProperties(
                        lineCap(LINE_CAP_ROUND),
                        lineJoin(LINE_JOIN_ROUND),
                        lineWidth(6f),
                        lineGradient(
                            interpolate(
                                linear(),
                                lineProgress(),
                                stop(0f, color(parseColor(ORIGIN_COLOR))),
                                stop(1f, color(parseColor(DESTINATION_COLOR)))
                            )
                        )
                    ),
                "mapbox-location-shadow-layer"
            )

// Add the SymbolLayer to show the destination marker
            it.addLayerAbove(
                SymbolLayer("CLICK_LAYER", "CLICK_SOURCE")
                    .withProperties(
                        iconImage("ICON_ID")
                    ),
                "ROUTE_LAYER_ID"
            )

            mapboxMap.addOnMapLongClickListener(this)
            Snackbar.make(binding.coordinator, "long press map to point", LENGTH_SHORT).show()
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
            locationComponent.renderMode = RenderMode.COMPASS
        }
        else{
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(activity)
        }

    }


    private fun setMapboxMap(latLng: LatLng,position: CameraPosition){
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


    override fun onStart() {
        super.onStart()
        mapView.onStart()
        mainActivity.isBottomNavigationVisible(false)
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
        mainActivity.isBottomNavigationVisible(true)
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
        MapboxNavigationProvider.destroy()
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

    override fun onMapLongClick(point: LatLng): Boolean {
        binding.routeRetrievalProgressSpinner.visibility = View.INVISIBLE

        // Place the destination marker at the map long click location
        mapboxMap.getStyle {
            val clickPointSource = it.getSourceAs<GeoJsonSource>("CLICK_SOURCE")
            clickPointSource?.setGeoJson(Point.fromLngLat(point.longitude, point.latitude))
        }
        mapboxMap.locationComponent.lastKnownLocation?.let { originLocation ->
            originLocation.latitude
            val originPoint = Point.fromLngLat(originLocation.getLongitude(),originLocation.getLatitude())
            val point1 = Point.fromLngLat(originLocation.getLongitude(),originLocation.getLatitude())
            mapboxNavigation.requestRoutes(
                RouteOptions.builder().applyDefaultParams()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .coordinates(listOf<Point>(originPoint,point1))
                    .alternatives(true)
                    .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
                    .build(),
                routesReqCallback
            )
        }
        return true
    }

    private val routesReqCallback = object : RoutesRequestCallback {
        override fun onRoutesReady(routes: List<DirectionsRoute>) {
            if (routes.isNotEmpty()) {
                Snackbar.make(
                    binding.coordinator,
                    String.format(
                        "Step of route",
                        routes[0].legs()?.get(0)?.steps()?.size
                    ),
                    LENGTH_SHORT
                ).show()

// Update a gradient route LineLayer's source with the Maps SDK. This will
// visually add/update the line on the map. All of this is being done
// directly with Maps SDK code and NOT the Navigation UI SDK.
                mapboxMap.getStyle {
                    val clickPointSource = it.getSourceAs<GeoJsonSource>("ROUTE_LINE_SOURCE_ID")
                    val routeLineString = LineString.fromPolyline(
                        routes[0].geometry()!!,
                        6
                    )
                    clickPointSource?.setGeoJson(routeLineString)
                }
                binding.routeRetrievalProgressSpinner.visibility = View.INVISIBLE
            } else {
                Snackbar.make(binding.coordinator, "no route", LENGTH_SHORT).show()
            }
        }

        override fun onRoutesRequestCanceled(routeOptions: RouteOptions) {
            Timber.e("route request failure %s")
            Snackbar.make(binding.coordinator, "route request fail", LENGTH_SHORT).show()
        }

        override fun onRoutesRequestFailure(throwable: Throwable, routeOptions: RouteOptions) {
            Timber.d("route request canceled")
        }
    }
}