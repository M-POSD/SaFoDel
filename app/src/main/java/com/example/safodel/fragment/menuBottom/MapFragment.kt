package com.example.safodel.fragment.menuBottom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.viewModelScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.SuburbAlertsResponse
import com.example.safodel.model.SuburbList
import com.example.safodel.model.SuburbMapResponse
import com.example.safodel.model.SuburbTimeResponse
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.ui.map.TrafficPlugin
import com.example.safodel.viewModel.MapAccidentViewModel
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
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
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import kotlin.collections.ArrayList
import com.mapbox.mapboxsdk.style.expressions.Expression.stop
import com.mapbox.mapboxsdk.style.expressions.Expression.zoom
import com.mapbox.mapboxsdk.style.layers.*
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.base.TimeFormat
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.applyLanguageAndVoiceUnitOptions
import com.mapbox.navigation.base.formatter.DistanceFormatterOptions
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.RouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.base.trip.model.RouteProgress
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.core.formatter.MapboxDistanceFormatter
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.core.trip.session.RouteProgressObserver
import com.mapbox.navigation.ui.base.util.MapboxNavigationConsumer
import com.mapbox.navigation.ui.maneuver.api.MapboxManeuverApi
import com.mapbox.navigation.ui.maps.camera.NavigationCamera
import com.mapbox.navigation.ui.maps.camera.data.MapboxNavigationViewportDataSource
import com.mapbox.navigation.ui.maps.camera.lifecycle.NavigationBasicGesturesHandler
import com.mapbox.navigation.ui.maps.camera.state.NavigationCameraState
import com.mapbox.navigation.ui.maps.camera.state.NavigationCameraStateChangedObserver
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider
import com.mapbox.navigation.ui.maps.route.arrow.api.MapboxRouteArrowApi
import com.mapbox.navigation.ui.maps.route.arrow.api.MapboxRouteArrowView
import com.mapbox.navigation.ui.maps.route.arrow.model.RouteArrowOptions
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.*
import com.mapbox.navigation.ui.tripprogress.api.MapboxTripProgressApi
import com.mapbox.navigation.ui.tripprogress.model.*
import com.mapbox.navigation.utils.internal.ifNonNull
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Circle
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.Exception
import com.mapbox.maps.MapboxMap as MapboxMap2
import com.mapbox.maps.MapView as MapView2
import com.mapbox.maps.Style as Style2


private val locationList: ArrayList<Point> = ArrayList()
private var feature: ArrayList<Feature> = ArrayList()
private var alertsFeature: ArrayList<Feature> = ArrayList()
private var suburb: String = "MELBOURNE"
private var spinnerTimes = 0
private lateinit var toast: Toast
private lateinit var fragmentNow : OnMapReadyCallback
private lateinit var mapViewModel: MapAccidentViewModel

// Retrofit
private lateinit var suburbInterface: SuburbInterface


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback,PermissionsListener {

    // View
    private lateinit var toolbar: Toolbar
    private lateinit var dialog: MaterialDialog
    private lateinit var diaglogFilter : MaterialDialog
    private lateinit var recenter: View
    private lateinit var spotlightRoot: FrameLayout


    // Map
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mapboxMap2: MapboxMap2
    private lateinit var mainActivity: MainActivity
    private lateinit var mapView: MapView
    private lateinit var mapView2: MapView2
    private lateinit var searchBarMap1: FrameLayout
    private lateinit var suburbPoint: Point
    private lateinit var trafficPlugin: TrafficPlugin
    private lateinit var searchBar: FrameLayout
    private lateinit var buttonFilter: View
    private lateinit var markerViewManager: MarkerViewManager
    private lateinit var alertMarkerBubble: MarkerView

    // Route
    private lateinit var routeLineAPI: MapboxRouteLineApi
    private lateinit var routeLineView: MapboxRouteLineView
    private lateinit var routeArrowView: MapboxRouteArrowView
    private val routeArrowAPI: MapboxRouteArrowApi = MapboxRouteArrowApi()

    /* ----- Mapbox Navigation components ----- */
    private lateinit var mapboxNavigation: MapboxNavigation
    private lateinit var maneuverApi: MapboxManeuverApi
    private lateinit var tripProgressApi: MapboxTripProgressApi

    // location puck integration
    private val navigationLocationProvider = NavigationLocationProvider()

    //camera
    private lateinit var viewportDataSource: MapboxNavigationViewportDataSource
    private lateinit var navigationCamera: NavigationCamera

    // Permission
    private lateinit var permissionsManager: PermissionsManager

    // Basic value
    private val suburbList = SuburbList.init()

    /* ----- Location and route progress callbacks ----- */
    private val locationObserver = object : LocationObserver {
        // use this after location get the new point
        override fun onRawLocationChanged(rawLocation: Location) {
            // not handled
        }

        // when map(location's route) match the road
        override fun onEnhancedLocationChanged(
            enhancedLocation: Location,
            keyPoints: List<Location>
        ) {
            // update location puck's position on the map
            navigationLocationProvider.changePosition(
                location = enhancedLocation,
                keyPoints = keyPoints
            )
            // update camera position to account for new location
            viewportDataSource.onLocationChanged(enhancedLocation)
            viewportDataSource.evaluate()
        }
    }


    private val routeProgressObserver = object : RouteProgressObserver {
        override fun onRouteProgressChanged(routeProgress: RouteProgress) {
            // update the camera position to account for the progressed fragment of the route
            viewportDataSource.onRouteProgressChanged(routeProgress)
            viewportDataSource.evaluate()

            // show arrow on the route line with the next maneuver
            val maneuverArrowResult = routeArrowAPI.addUpcomingManeuverArrow(routeProgress)
            val style = mapboxMap2.getStyle()
            if (style != null) {
                routeArrowView.renderManeuverUpdate(style, maneuverArrowResult)
            }
            val maneuvers = maneuverApi.getManeuvers(routeProgress)
            maneuvers.fold(
                { error ->
                    Toast.makeText(
                        mainActivity,
                        error.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    binding.maneuverView.visibility = View.VISIBLE
                    binding.maneuverView.renderManeuvers(maneuvers)
                }
            )

            binding.tripProgressView.render(
                tripProgressApi.getTripProgress(routeProgress)
            )
        }
    }

    /*--   to know when the route list changed  --*/
    private val routesObserver = object : RoutesObserver {
        override fun onRoutesChanged(routes: List<DirectionsRoute>) {
            if (routes.isNotEmpty()) {
                val selectedRoute = routes.first()
                routeLineAPI.setRoutes(listOf(RouteLine(selectedRoute, null)), object :
                    MapboxNavigationConsumer<Expected<RouteLineError, RouteSetValue>> {
                    override fun accept(value: Expected<RouteLineError, RouteSetValue>) {
                        ifNonNull(routeLineView, mapboxMap2.getStyle()) { view, style ->
                            view.renderRouteDrawData(style, value)
                            viewportDataSource.options.followingFrameOptions.zoomUpdatesAllowed =
                                true
                            viewportDataSource.options.followingFrameOptions.centerUpdatesAllowed =
                                true
                            viewportDataSource.evaluate()
                            navigationCamera.requestNavigationCameraToFollowing()
                        }
                    }
                })
                viewportDataSource.onRouteChanged(selectedRoute)
            } else {
                viewportDataSource.clearRouteData()
                navigationCamera.requestNavigationCameraToIdle()
                ifNonNull(routeLineAPI, routeLineView, mapboxMap2.getStyle()) { api, view, style ->
                    api.clearRouteLine(
                        object :
                            MapboxNavigationConsumer<Expected<RouteLineError, RouteLineClearValue>> {
                            override fun accept(value: Expected<RouteLineError, RouteLineClearValue>) {
                                view.renderClearRouteLineValue(style, value)
                            }
                        }
                    )
                }

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.let {
            Mapbox.getInstance(
                it.application,
                getString(R.string.mapbox_access_token)
            )
        }
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        // init the toast (it will not show)
        toast = Toast.makeText(context, "message", Toast.LENGTH_SHORT)

        // init the view
        binding.floatButtonStop.visibility = View.INVISIBLE
        mainActivity = activity as MainActivity
        toolbar = binding.toolbar.root
        recenter = binding.recenter
        mapView = binding.mapView
        searchBar = binding.searchBar
        searchBarMap1 = binding.searchMap1
        buttonFilter = binding.floatButtonFilter
        fragmentNow = this
        mapViewModel = MapAccidentViewModel()

        buttonFilter.doOnPreDraw {
            spotlight()
        }
        mapView2 = binding.mapView2  // for navigation
        mapboxMap2 = mapView2.getMapboxMap() // for navigation
        diaglogFilter = MaterialDialog(mainActivity)
        spotlightRoot = FrameLayout(requireContext())
        setToolbarGray(toolbar)
        setfilterListener()


        // request permission of user location
        permissionsManager = PermissionsManager(this)
        permissionsManager.requestLocationPermissions(activity)

        // Set Dialog
        dialog = MaterialDialog(requireContext())

        // spinner init
        val spinner = binding.spinner
        spinner.item = suburbList
        spinner.typeface = ResourcesCompat.getFont(requireContext(), R.font.rubik_medium)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                setDialog()
                spinnerTimes++ // calculate the times to test
                if (spinnerTimes >= 1) {
                    suburb = parent?.getItemAtPosition(position).toString()
                    callSuburbClient()
                    //mapView.getMapAsync(fragmentNow)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        suburbInterface = SuburbClient.getSuburbService()

        // change the float button height
        changeFloatButtonHeight()
        // basic location and position


        setDialog()
        callAlertsClient()
        callSuburbClient()

        // go to the user's current location
        binding.floatButton.setOnClickListener {
            mapboxMap.style?.let { it1 -> enableLocationComponent(it1) }
            if (this::navigationCamera.isInitialized) {
                navigationCamera.requestNavigationCameraToOverview()
                updateNavCamera()
            }

        }

        // initialize Mapbox Navigation
        mapboxNavigation = MapboxNavigation(
            NavigationOptions.Builder(mainActivity)
                .accessToken(getString(R.string.mapbox_access_token))
                .build()
        )


        fitSearchMap1() // fit windows to the search bar in Mapview1
        mapView.onCreate(savedInstanceState)
        //setDialog()
        //mapView.getMapAsync(this) // update the map
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    /*
        update the map when call getMapAsync()
     */
    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setMaxZoomPreference(20.0)
        mapboxMap.setMinZoomPreference(5.0)


        this.mapboxMap = mapboxMap
        markerViewManager = MarkerViewManager(mapView,mapboxMap)
        this.mapboxMap.setStyle(Style.LIGHT) {

            /*-- Camera auto zoom to the suburb area --*/
            if (feature.size != 0 && locationList.size != 0) {
                suburbPoint = locationList[0]
                val position = CameraPosition.Builder()
                    .target(LatLng(suburbPoint.latitude(), suburbPoint.longitude()))
                    .zoom(9.0)
                    .build()
                mapboxMap.cameraPosition = position
            }

            // get user location
            enableLocationComponent(it)

            // Make a toast when data is updating
            if (feature.size == 0 && locationList.size == 0) {
                toast.setText(getString(R.string.no_data))
                toast.show()
            }

            /*-- Add location image --*/
            it.addImage(
                "icon_image",
                BitmapUtils.getBitmapFromDrawable(
                    context?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1,
                            R.drawable.crash_location
                        )
                    }
                )!!)

            /*--   Add alerts image   --*/
            it.addImage(
                "alert_image",
                BitmapUtils.getBitmapFromDrawable(
                    context?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1,
                            R.drawable.alerts_icon
                        )
                    }
                )!!

            )

            /*-- Add source --*/
            it.addSource(
                GeoJsonSource(
                    "source", FeatureCollection.fromFeatures(
                        ArrayList<Feature>(
                            feature
                        )
                    )
                )
            )

            it.addSource(
                GeoJsonSource(
                    "alertSource", FeatureCollection.fromFeatures(
                        ArrayList<Feature>(
                            alertsFeature
                        )
                    )
                )
            )




            trafficPlugin = TrafficPlugin(mapView, mapboxMap, it)
            trafficPlugin.setVisibility(true)


            /*-- Add layer --*/
            var basicCircle: CircleLayer =
                CircleLayer("basic_circle_cayer", "source").withProperties(
                    circleColor(Color.parseColor("#ff0015")),
                    visibility(Property.VISIBLE),
                    iconIgnorePlacement(false),
                    iconAllowOverlap(false),
                    circleRadius(
                        interpolate(
                            linear(), zoom(),
                            stop(10, 1.0f),
                            stop(15, 4.0f),
                            stop(20, 16f)
                        )
                    ), circleOpacity(0.5f)
                )
            it.addLayer(basicCircle)

            /*-- Add circle layer --*/
            var shadowTransitionCircleLayer = CircleLayer("shadow_circle_cayer", "source")
                .withProperties(
                    circleColor(parseColor("#bd0010")),
                    visibility(Property.VISIBLE),
                    circleOpacity(
                        interpolate(
                            linear(), zoom(),
                            stop(10, 0.75f),
                            stop(15, 6f),
                            stop(20.0f, 18.0f)
                        )
                    ),
                    iconIgnorePlacement(false),
                    iconAllowOverlap(false), circleOpacity(0.5f)
                )
            it.addLayerBelow(shadowTransitionCircleLayer, "basic_circle_cayer")

            /*-- Add symbol layer --*/
            val symbolIconLayer = SymbolLayer("icon_layer", "source")
            symbolIconLayer.withProperties(
                visibility(Property.VISIBLE),
                iconImage("icon_image"),
                iconSize(1.5f),
                iconIgnorePlacement(false),
                iconAllowOverlap(false)
            )
            symbolIconLayer.minZoom = 15f
            it.addLayer(symbolIconLayer)

            /*-- Add alerts layer --*/
            val alertIconLayer = SymbolLayer("alert_layer", "alertSource")
            alertIconLayer.withProperties(
                visibility(Property.VISIBLE),
                iconImage("alert_image"),
                iconSize(
                    interpolate(
                    linear(), zoom(),
                    stop(10, 0.2f),
                    stop(15, 0.3f),
                    stop(20.0f, 0.5f)
                )),
                iconIgnorePlacement(false),
                iconAllowOverlap(false)
            )
            //alertIconLayer.minZoom = 15f
            it.addLayer(alertIconLayer)

//            val alertsWindowLayer = SymbolLayer("alert_window_layer","alertSource")
//            alertsWindowLayer.withProperties(
//                iconImage("{name}"),
//                iconAnchor(Property.ICON_ANCHOR_BOTTOM),
//                iconAllowOverlap(true),
//                iconOffset(floatArrayOf(-2f,-28f).toTypedArray())
//            )
//            it.addLayer(alertsWindowLayer)

            mapboxMap.addOnMapClickListener {
                handleClickAlert(Point.fromLngLat(it.longitude,it.latitude))
                false
            }



            /*-- Set the camera's animation --*/
            mapboxMap.animateCamera(
                CameraUpdateFactory
                    .newCameraPosition(
                        CameraPosition.Builder()
                            .zoom(10.5)
                            .build()
                    ), 3000
            )


            /*--  Float button can hide the crash data relative view.   --*/
            binding.floatButtonNav.setOnClickListener {
                changeToNav()
            }
            dialog.dismiss()
        }

    }

    fun handleClickAlert(point: Point){
        //markerViewManager.removeMarker(alertMarkerBubble)
        val alertBubble = LayoutInflater.from(mainActivity).inflate(
            R.layout.marker_alert_bubble, null)
        alertBubble.setLayoutParams(FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
        var title = alertBubble.findViewById<TextView>(R.id.marker_alert_title)
        var snippet = alertBubble.findViewById<TextView>(R.id.marker_alert_snippet)
        title.text = point.longitude().toString()
        snippet.text = point.latitude().toString()
        alertMarkerBubble = MarkerView(LatLng(point.latitude(),point.longitude()),alertBubble)

        markerViewManager.addMarker(alertMarkerBubble)
    }




    @SuppressWarnings("MissingPermission")
    fun enableLocationComponent(loadMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            val customLocationComponentOptions: LocationComponentOptions? = context?.let {
                LocationComponentOptions
                    .builder(it)
                    .pulseEnabled(true).build()
            }

            val locationComponent: LocationComponent = mapboxMap.locationComponent

            context?.let {
                LocationComponentActivationOptions.builder(it, loadMapStyle)
                    .locationComponentOptions(customLocationComponentOptions).build()
            }?.let {
                locationComponent.activateLocationComponent(it)
            }

            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS
        } else {
            /*-- Ask permission --*/
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(activity)
        }
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
        mapboxNavigation.registerRoutesObserver(routesObserver)
        mapboxNavigation.registerRouteProgressObserver(routeProgressObserver)
        mapboxNavigation.registerLocationObserver(locationObserver)
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
        mapboxNavigation.onDestroy()
        if (::mapboxNavigation.isInitialized) {
            mapboxNavigation.unregisterRoutesObserver(routesObserver)
            mapboxNavigation.unregisterLocationObserver(locationObserver)
            mapboxNavigation.unregisterRouteProgressObserver(routeProgressObserver)
        }
        MapboxNavigationProvider.destroy()
        mainActivity.isBottomNavigationVisible(true)
        _binding = null
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(context, getString(R.string.need_premission), Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            mapboxMap.getStyle {
                enableLocationComponent(it)
            }
        } else {
            Toast.makeText(context, getString(R.string.loation_granted), Toast.LENGTH_LONG)
                .show()
        }
    }


    /* --
            UI part - Hide or Show the crash relative view.
    -- */
    fun changeToNav() {
        mapboxMap.getStyle {
            val bcLayer = it.getLayer("basic_circle_cayer")
            val scLayer = it.getLayer("shadow_circle_cayer")
            val siLayer = it.getLayer("icon_layer")
            if (bcLayer != null) {
                if (View.VISIBLE.equals(mapView.visibility)) {
                    setToolbarReturn(toolbar)
                    mainActivity.isBottomNavigationVisible(false)
                    mapView2.visibility = View.VISIBLE
                    mapView.visibility = View.INVISIBLE
                    binding.recenter.visibility = View.VISIBLE
                    binding.searchBar.visibility = View.VISIBLE
                    binding.floatButtonStop.visibility = View.VISIBLE
                    initNav()
                    binding.floatButtonNav.setImageResource(R.drawable.crash_36)
                    binding.spinner.visibility = View.INVISIBLE
                    bcLayer.setProperties(visibility(Property.NONE))
                    scLayer?.setProperties(visibility(Property.NONE))
                    siLayer?.setProperties(visibility(Property.NONE))

                    /*--    Edit the Search bar    --*/
                    GlobalScope.launch {
                        // try to get the height of status bar and then margin top
                        val searchBarHeight =
                            searchBar.layoutParams as CoordinatorLayout.LayoutParams
                        while (searchBarHeight.topMargin == 0)
                            searchBarHeight.topMargin = mainActivity.getStatusHeight()
                        searchBar.layoutParams = searchBarHeight
                        this.cancel()
                    }

                } else {

                    val dialog2 = MaterialDialog(mainActivity)
                    val fragmentNow = this
                    dialog2.show {
                        message(text = getString(R.string.ask_leave))
                        positiveButton(R.string.yes) {
                            setToolbarGray(toolbar)
                            mainActivity.isBottomNavigationVisible(true)
                            mapView2.visibility = View.INVISIBLE
                            binding.floatButtonStop.visibility = View.INVISIBLE
                            binding.searchBar.visibility = View.INVISIBLE
                            mapView.visibility = View.VISIBLE
                            recenter.visibility = View.INVISIBLE
                            mapView.getMapAsync(fragmentNow)
                            binding.floatButtonNav.setImageResource(R.drawable.baseline_assistant_direction_black_36)
                            binding.spinner.visibility = View.VISIBLE
                            bcLayer.setProperties(visibility(Property.VISIBLE))
                            scLayer?.setProperties(visibility(Property.VISIBLE))
                            siLayer?.setProperties(visibility(Property.VISIBLE))
                            clearRouteAndStopNavigation()
                        }
                        negativeButton(R.string.no) {
                            // do nothing
                        }
                        cancelOnTouchOutside(false)
                    }


                }
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ///                                  Navigation                                    ////
    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("MissingPermission")
    private fun initNav() {
        // initialize the location puck
        mapView2.location.apply {
            this.locationPuck = LocationPuck2D(
                bearingImage = ContextCompat.getDrawable(
                    mainActivity,
                    R.drawable.mapbox_navigation_puck_icon
                )
            )
            setLocationProvider(navigationLocationProvider)
            enabled = true

        }

        // move the camera to current location on the first update
        updateNavCamera()

        viewportDataSource = MapboxNavigationViewportDataSource(
            mapView2.getMapboxMap()
        )
        navigationCamera = NavigationCamera(
            mapView2.getMapboxMap(),
            mapView2.camera,
            viewportDataSource
        )
        mapView2.camera.addCameraAnimationsLifecycleListener(
            NavigationBasicGesturesHandler(navigationCamera)
        )
        navigationCamera.registerNavigationCameraStateChangeObserver(
            object : NavigationCameraStateChangedObserver {
                override fun onNavigationCameraStateChanged(
                    navigationCameraState: NavigationCameraState
                ) {
                    // shows/hide the recenter button depending on the camera state
                    when (navigationCameraState) {
                        NavigationCameraState.TRANSITION_TO_FOLLOWING,
                        NavigationCameraState.FOLLOWING -> recenter.visibility = View.INVISIBLE
                        NavigationCameraState.TRANSITION_TO_OVERVIEW,
                        NavigationCameraState.OVERVIEW,
                        NavigationCameraState.IDLE -> recenter.visibility =
                            View.VISIBLE
                    }
                }
            }
        )

        // initialize top maneuver view
        maneuverApi = MapboxManeuverApi(
            MapboxDistanceFormatter(DistanceFormatterOptions.Builder(mainActivity).build())
        )

        // initialize bottom progress view
        tripProgressApi = MapboxTripProgressApi(
            TripProgressUpdateFormatter.Builder(mainActivity)
                .distanceRemainingFormatter(
                    DistanceRemainingFormatter(
                        mapboxNavigation.navigationOptions.distanceFormatterOptions
                    )
                )
                .timeRemainingFormatter(TimeRemainingFormatter(mainActivity))
                .percentRouteTraveledFormatter(PercentDistanceTraveledFormatter())
                .estimatedTimeToArrivalFormatter(
                    EstimatedTimeToArrivalFormatter(mainActivity, TimeFormat.NONE_SPECIFIED)
                )
                .build()
        )


        val mapboxRouteLineOptions = MapboxRouteLineOptions.Builder(mainActivity)
            .withRouteLineBelowLayerId("road-label")
            .build()
        routeLineAPI = MapboxRouteLineApi(mapboxRouteLineOptions)
        routeLineView = MapboxRouteLineView(mapboxRouteLineOptions)
        val routeArrowOptions = RouteArrowOptions.Builder(mainActivity).build()
        routeArrowView = MapboxRouteArrowView(routeArrowOptions)

        mapboxMap2.loadStyleUri(
            Style2.TRAFFIC_DAY,
            {
                // add long click listener that search for a route to the clicked destination
                mapView2.gestures.addOnMapLongClickListener(
                    object : OnMapLongClickListener {
                        override fun onMapLongClick(point: Point): Boolean {
                            findRoute(point)
                            return true
                        }
                    }
                )
            }
        )

        binding.floatButtonStop.setOnClickListener {
            clearRouteAndStopNavigation()
        }

        binding.recenter.setOnClickListener {
            navigationCamera.requestNavigationCameraToFollowing()
        }

        searchBar.setOnClickListener {
            val intent: Intent = PlaceAutocomplete.IntentBuilder()
                .accessToken(getString(R.string.mapbox_access_token))
                .placeOptions(
                    PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(10)
                        .build(PlaceOptions.MODE_CARDS)
                )
                .build(mainActivity)
            startActivityForResult(intent, 1)

        }

        mapboxNavigation.startTripSession()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val selectedCarmenFeature = PlaceAutocomplete.getPlace(data)
            val long = selectedCarmenFeature.center()?.longitude()!!
            val lat = selectedCarmenFeature.center()?.latitude()!!

            findRoute(Point.fromLngLat(long, lat))
        }


    }

    private fun updateNavCamera() {

        mapboxNavigation.registerLocationObserver(object : LocationObserver {
            override fun onRawLocationChanged(rawLocation: Location) {
                val point = Point.fromLngLat(rawLocation.longitude, rawLocation.latitude)
                val cameraOptions = CameraOptions.Builder()
                    .center(point)
                    .zoom(13.0)
                    .build()
                mapboxMap2.setCamera(cameraOptions)
                mapboxNavigation.unregisterLocationObserver(this)
            }

            override fun onEnhancedLocationChanged(
                enhancedLocation: Location,
                keyPoints: List<Location>
            ) {
                // not handled
            }
        })
    }


    private fun findRoute(destination: Point) {
        setDialog()
        val origin = navigationLocationProvider.lastLocation?.let {
            Point.fromLngLat(it.longitude, it.latitude)
        } ?: return

        mapboxNavigation.requestRoutes(
            RouteOptions.builder()
                .applyDefaultNavigationOptions()
                .profile(DirectionsCriteria.PROFILE_CYCLING)
                .applyLanguageAndVoiceUnitOptions(mainActivity)
                .coordinatesList(listOf(origin, destination))
                .build(),
            object : RouterCallback {
                override fun onRoutesReady(
                    routes: List<DirectionsRoute>,
                    routerOrigin: RouterOrigin
                ) {
                    dialog.dismiss()
                    setRouteAndStartNavigation(routes.first())
                }

                override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
                    dialog.dismiss()
                    toast.setText(getString(R.string.can_not_go))
                    toast.show()

                }

                override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                    // no impl
                    dialog.dismiss()
                }
            }
        )
    }

    private fun setRouteAndStartNavigation(route: DirectionsRoute) {
        // set route
        mapboxNavigation.setRoutes(listOf(route))

        // move the camera to overview when new route is available
        navigationCamera.requestNavigationCameraToOverview()
        binding.tripProgressCard.visibility = View.VISIBLE
        changeFloatButtonHeight()
    }

    private fun clearRouteAndStopNavigation() {
        // clear
        mapboxNavigation.setRoutes(listOf())
        binding.maneuverView.visibility = View.INVISIBLE
        binding.tripProgressCard.visibility = View.INVISIBLE
        changeFloatButtonHeight()

    }

    private fun changeFloatButtonHeight() {
        // change the float button height
        val FBHeight = binding.floatButton.layoutParams as CoordinatorLayout.LayoutParams
        if (binding.tripProgressCard.visibility.equals(View.VISIBLE))
            FBHeight.bottomMargin = binding.tripProgressCard.height + 20
        else
            FBHeight.bottomMargin = mainActivity.bottomNavHeight() + 20
        binding.floatButton.layoutParams = FBHeight

        // change the float button Nav height
        val FBHeightNav = binding.floatButtonNav.layoutParams as CoordinatorLayout.LayoutParams
        FBHeightNav.bottomMargin = FBHeight.bottomMargin * 2
        binding.floatButtonNav.layoutParams = FBHeightNav

        // change the float button Stop height
        val FBHeightStop = binding.floatButtonStop.layoutParams as CoordinatorLayout.LayoutParams
        FBHeightStop.bottomMargin = FBHeight.bottomMargin * 3
        binding.floatButtonStop.layoutParams = FBHeightStop
    }

    private fun callAlertsClient(){
        //val fragmentNow = this
        GlobalScope.launch {
            val callAsync: Call<SuburbAlertsResponse> = suburbInterface.alertsRepos(
                "alerts"
            )
            callAsync.enqueue(object : Callback<SuburbAlertsResponse?> {
                override fun onResponse(
                    call: Call<SuburbAlertsResponse?>?,
                    response: Response<SuburbAlertsResponse?>
                ) {
                    if (response.isSuccessful) {
                        val resultList = response.body()?.suburbAlertsAccidents
                        if (resultList?.isNotEmpty() == true) {
                            for (each in resultList) {
                                val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
                                alertsFeature.add(Feature.fromGeometry(eachPoint))
                            }
                        }
                        //mapView.getMapAsync(fragmentNow)
                    } else {
                        Timber.i(getString(R.string.response_failed))
                    }
                }

                override fun onFailure(call: Call<SuburbAlertsResponse?>?, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    /*
        Collect the data
     */
    private fun callSuburbClient() {
            feature.clear()
            locationList.clear()
//        mapViewModel.launchIt {
//            try {
//                val callAsync2 = async {
//                    suburbInterface.mapRepos(
//                        "accidents",
//                        suburb
//                    )
//                }
//
//                val callAsyncAlert = async {
//                    callAsync2.join()
//                    suburbInterface.alertsRepos(
//                        "alerts"
//                    )
//                }
//                val resultList = callAsync2.await().suburbMapAccidents
//                callAsync2.await().run {
//                }
//                if (resultList.isNotEmpty() == true) {
//                    for (each in resultList) {
//                        val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
//                        locationList.add(eachPoint)
//                    }
//                }
//                for (each in 0..locationList.size - 1) {
//                    feature.add(Feature.fromGeometry(locationList[each]))
//                }
//                val resultListAlert = callAsyncAlert.await().suburbAlertsAccidents
//                if (resultListAlert.isNotEmpty() == true) {
//                    for (each in resultListAlert) {
//                        val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
//                        alertsFeature.add(Feature.fromGeometry(eachPoint))
//                    }
//                }
//
//            }catch (e: Throwable) {
//                dialog.dismiss()
//                toast.setText(e.message)
//                toast.show()
//                Timber.d(e.message.toString())
//            }finally {
//                this.cancel()
//            }
//
//        }
        val callAsync2: Call<SuburbMapResponse> = SuburbClient.getSuburbService().mapRepos(
                        "accidents",
                        suburb
                    )

            callAsync2.enqueue(object : Callback<SuburbMapResponse?> {
                override fun onResponse(
                    call: Call<SuburbMapResponse?>?,
                    response: Response<SuburbMapResponse?>
                ) {
                    if (response.isSuccessful) {
                        val resultList = response.body()    ?.suburbMapAccidents
                        if (resultList?.isNotEmpty() == true) {
                            for (each in resultList) {
                                val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
                                locationList.add(eachPoint)
                            }
                        }
                        for (each in 0..locationList.size - 1) {
                            feature.add(Feature.fromGeometry(locationList[each]))
                        }
                        mapView.getMapAsync(fragmentNow)
                    } else {
                        Timber.i(getString(R.string.response_failed))
                    }
                }

                override fun onFailure(call: Call<SuburbMapResponse?>?, t: Throwable) {
                    dialog.dismiss()
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

    }


    /*
        Setting the dialog when item select
     */
    private fun setDialog() {
        dialog.show {
            message(text = getString(R.string.loading))
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }

    private fun fitSearchMap1() {
        GlobalScope.launch {
            // try to get the height of status bar and then margin top
            val searchBarMap1Height = searchBarMap1.layoutParams as CoordinatorLayout.LayoutParams
            val buttonFilterHeight = buttonFilter.layoutParams as CoordinatorLayout.LayoutParams
            while (searchBarMap1Height.topMargin == 0)
                searchBarMap1Height.topMargin = mainActivity.getStatusHeight()
            while (buttonFilterHeight.topMargin == 0)
                buttonFilterHeight.topMargin = mainActivity.getStatusHeight() + 10
            searchBarMap1.layoutParams = searchBarMap1Height
            buttonFilter.layoutParams = buttonFilterHeight
            this.cancel()
        }
    }

    /*****
     * *****
     * *****
     * *****███████████████████████████████████████████████████████████████████████████████████████████
     * *****█░░░░░░░░░░░░░░█░░░░░░░░░░█░░░░░░█████████░░░░░░░░░░░░░░█░░░░░░░░░░░░░░█░░░░░░░░░░░░░░░░███
     * *****█░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀░░█░░▄▀░░█████████░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀▄▀░░███
     * *****█░░▄▀░░░░░░░░░░█░░░░▄▀░░░░█░░▄▀░░█████████░░░░░░▄▀░░░░░░█░░▄▀░░░░░░░░░░█░░▄▀░░░░░░░░▄▀░░███
     * *****█░░▄▀░░███████████░░▄▀░░███░░▄▀░░█████████████░░▄▀░░█████░░▄▀░░█████████░░▄▀░░████░░▄▀░░███
     * *****█░░▄▀░░░░░░░░░░███░░▄▀░░███░░▄▀░░█████████████░░▄▀░░█████░░▄▀░░░░░░░░░░█░░▄▀░░░░░░░░▄▀░░███
     * *****█░░▄▀▄▀▄▀▄▀▄▀░░███░░▄▀░░███░░▄▀░░█████████████░░▄▀░░█████░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀▄▀░░███
     * *****█░░▄▀░░░░░░░░░░███░░▄▀░░███░░▄▀░░█████████████░░▄▀░░█████░░▄▀░░░░░░░░░░█░░▄▀░░░░░░▄▀░░░░███
     * *****█░░▄▀░░███████████░░▄▀░░███░░▄▀░░█████████████░░▄▀░░█████░░▄▀░░█████████░░▄▀░░██░░▄▀░░█████
     * *****█░░▄▀░░█████████░░░░▄▀░░░░█░░▄▀░░░░░░░░░░█████░░▄▀░░█████░░▄▀░░░░░░░░░░█░░▄▀░░██░░▄▀░░░░░░█
     * *****█░░▄▀░░█████████░░▄▀▄▀▄▀░░█░░▄▀▄▀▄▀▄▀▄▀░░█████░░▄▀░░█████░░▄▀▄▀▄▀▄▀▄▀░░█░░▄▀░░██░░▄▀▄▀▄▀░░█
     * *****█░░░░░░█████████░░░░░░░░░░█░░░░░░░░░░░░░░█████░░░░░░█████░░░░░░░░░░░░░░█░░░░░░██░░░░░░░░░░█
     * *****███████████████████████████████████████████████████████████████████████████████████████████
     * *****
     * *****
     * *****
     * *****/


    /*
            Filter
     */
    private fun setfilterListener() {
        buttonFilter.setOnClickListener {
            showDialogFilter()
        }
    }

    @SuppressLint("CheckResult")
    private fun showDialogFilter() {
        val fragmentNow = this
        val myItems = listOf(getString(R.string.traffic_status), getString(R.string.accident_point))
        diaglogFilter = MaterialDialog(mainActivity)
        diaglogFilter.show {
            message(text = getString(R.string.filter))
            listItemsMultiChoice(items = myItems,waitForPositiveButton = true,allowEmptySelection = true){dialog, indices, items ->
                //Toast.makeText(mainActivity,items.toString(),Toast.LENGTH_SHORT).show()
                if(items.contains(myItems[0]))
                    trafficPlugin.setVisibility(true)
                else trafficPlugin.setVisibility(false)
                if(items.contains(myItems[1])){
                    mapView.getMapAsync(fragmentNow)
                }
                else{
                    mapboxMap.getStyle {
                        val layer1 = it.getLayer("basic_circle_cayer")
                        val layer2 = it.getLayer("shadow_circle_cayer")
                        val layer3 = it.getLayer("icon_layer")
                        if(layer1 != null && layer2 != null && layer3 != null){
                            if(layer1.visibility.getValue()!!.equals(Property.VISIBLE)){
                                layer1.setProperties(visibility(Property.NONE))
                                layer2.setProperties(visibility(Property.NONE))
                                layer3.setProperties(visibility(Property.NONE))
                            }
                        }
                    }

                }
            }
            positiveButton(R.string.select)
        }
    }


    /*
           show the learning mode while open the map in first time.
     */
    @SuppressLint("ResourceAsColor")
    private fun spotlight(){
        val sf = mainActivity.getPreferences(Context.MODE_PRIVATE)
        var go = sf.getBoolean("mapLeaningMode",false)
        if(go){
            val targetLay = layoutInflater.inflate(R.layout.filter_target,spotlightRoot)
            val target = Target.Builder()
                .setShape(Circle(0f))
                .setOverlay(targetLay)
                .build()


            val spotlight = Spotlight.Builder(mainActivity)
                .setTargets(target)
                .setBackgroundColor(R.color.spotlightBackground)
                .setDuration(1000L)
                .setAnimation(DecelerateInterpolator(2f))
                .build()

            spotlight.start()
            sf.edit().putBoolean("mapLeaningMode",false).apply()
            targetLay.findViewById<View>(R.id.filter_target_page).setOnClickListener {
                spotlight.finish()
            }
        }

    }
}





