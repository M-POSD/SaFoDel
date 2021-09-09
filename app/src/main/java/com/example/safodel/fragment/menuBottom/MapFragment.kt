package com.example.safodel.fragment.menuBottom

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.parseColor
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.LGAList
import com.example.safodel.ui.main.MainActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
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
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.collections.ArrayList
import com.mapbox.mapboxsdk.style.expressions.Expression.stop
import com.mapbox.mapboxsdk.style.expressions.Expression.zoom
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius
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
import org.angmarch.views.NiceSpinner
import com.mapbox.maps.MapboxMap as MapboxMap2
import com.mapbox.maps.MapView as MapView2
import com.mapbox.maps.Style as Style2


private val locationList: ArrayList<Point> = ArrayList()
private var feature: ArrayList<Feature> = ArrayList()
private var lga: String = "MELBOURNE"
private var spinnerTimes = 0
private lateinit var mThread:Thread


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback,PermissionsListener {

    // View
    private lateinit var toast: Toast
    private lateinit var toolbar: Toolbar

    // Map
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mapboxMap2: MapboxMap2
    private lateinit var mainActivity : MainActivity
    private lateinit var mapView: MapView
    private lateinit var mapView2 : MapView2
    private lateinit var LGAPoint:Point

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
    private val LGAlist = LGAList.init()

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
                    MapboxNavigationConsumer<Expected<RouteLineError,RouteSetValue>>{
                    override fun accept(value: Expected<RouteLineError,RouteSetValue>) {
                        ifNonNull(routeLineView, mapboxMap2.getStyle()) { view, style ->
                            view.renderRouteDrawData(style, value)
                            viewportDataSource.options.followingFrameOptions.zoomUpdatesAllowed = true
                            viewportDataSource.options.followingFrameOptions.centerUpdatesAllowed = true
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
                            object : MapboxNavigationConsumer<Expected< RouteLineError,RouteLineClearValue>> {
                                override fun accept(value: Expected<RouteLineError,RouteLineClearValue>) {
                                    view.renderClearRouteLineValue(style, value)
                                }
                            }
                        )
                    }

            }
        }}
    
    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.let { Mapbox.getInstance(it.application,getString(R.string.mapbox_access_token)) }
        _binding = FragmentMapBinding.inflate(inflater,container,false)
        toast = Toast.makeText(context,"message",Toast.LENGTH_SHORT)

        // init the view
        binding.floatButtonStop.visibility = View.INVISIBLE
        mainActivity = activity as MainActivity
        toolbar = binding.toolbar.root
        mapView = binding.mapView
        mapView2 = binding.mapView2
        mapboxMap2 = mapView2.getMapboxMap()
        setToolbarBasic(toolbar)

        // request permission of user location
        permissionsManager = PermissionsManager(this)
        permissionsManager.requestLocationPermissions(activity)

        // spinner init
        val spinner = binding.spinner
        spinner.item  = LGAlist
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                spinnerTimes++ // calculate the times to test
                if(spinnerTimes >= 1){
                    lga = parent?.getItemAtPosition(position).toString()
                    mThread = fetchdata()
                    mThread.start()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // change the float button height
        changeFloatButtonHeight()
        // basic location and position
        mThread = fetchdata()
        mThread.start()

        // update the map
        binding.updateMap.setOnClickListener {
            mapView.getMapAsync(this)
        }

        // go to the user's current location
        binding.floatButton.setOnClickListener {
            mapboxMap.style?.let { it1 -> enableLocationComponent(it1) }
            if(this::navigationCamera.isInitialized){
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

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this) // update the map
        return binding.root
    }


    /*
        update the map when call getMapAsync()
     */
    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setMaxZoomPreference(20.0)
        mapboxMap.setMinZoomPreference(5.0)

        this.mapboxMap = mapboxMap
        this.mapboxMap.setStyle(Style.LIGHT){

            /*-- Camera auto zoom to the LGA area --*/
            if(feature.size != 0){
                LGAPoint = locationList[0]
                val position = CameraPosition.Builder()
                    .target(LatLng(LGAPoint.latitude(),LGAPoint.longitude()))
                    .zoom(9.0)
                    .build()
                mapboxMap.cameraPosition = position
            }

            // get user location
            enableLocationComponent(it)

            // Make a toast when data is updating
            if(feature.size == 0){
                toast.setText("Data is updating...")
                toast.show()
            }

            /*-- Add inmage --*/
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

            /*-- Add source --*/
            it.addSource(GeoJsonSource("source", FeatureCollection.fromFeatures(ArrayList<Feature>(
                feature))))
            val baseicCircle:CircleLayer = CircleLayer("basic_circle_cayer","source").withProperties(
                circleColor(Color.parseColor("#FF0000")),
                visibility(Property.VISIBLE),
                iconIgnorePlacement(false),
                iconAllowOverlap(false),
                circleRadius(
                    interpolate(
                        linear(), zoom(),
                        stop(11f, 4f),
                        stop(12f, 4f),
                        stop(15f,4f),
                        stop(15.1f,0f)
                )
            ))

            /*-- Add layer --*/
            it.addLayer(baseicCircle)

            /*-- Add circle layer --*/
            val shadowTransitionCircleLayer = CircleLayer("shadow_circle_cayer", "source")
                .withProperties(
                    circleColor(parseColor("#FC9C9C")),
                    circleRadius(6f),
                    visibility(Property.VISIBLE),
                    circleOpacity(
                        interpolate(
                            linear(), zoom(),
                            stop(11f, .5f),
                            stop(15f,0f)
                        )
                    ),
                    iconIgnorePlacement(false),
                    iconAllowOverlap(false)
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
        mThread.interrupt()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mThread.interrupt()
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
        mThread.interrupt()
        mapView.onDestroy()
        mapboxNavigation.onDestroy()
        if (::mapboxNavigation.isInitialized){
            mapboxNavigation.unregisterRoutesObserver(routesObserver)
            mapboxNavigation.unregisterLocationObserver(locationObserver)
            mapboxNavigation.unregisterRouteProgressObserver(routeProgressObserver)
        }
        MapboxNavigationProvider.destroy()
        mainActivity.isBottomNavigationVisible(true)
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

    /* -- Hide or Show the crash relative view. -- */
    fun changeToNav(){
        mapboxMap.getStyle {
            val bcLayer =  it.getLayer("basic_circle_cayer")
            val scLayer = it.getLayer("shadow_circle_cayer")
            val siLayer = it.getLayer("icon_layer")
            if(bcLayer != null && scLayer != null && siLayer != null){
                if(View.VISIBLE.equals(mapView.visibility)){
                    setToolbarReturn(toolbar)
                    mainActivity.isBottomNavigationVisible(false)
                    mapView2.visibility = View.VISIBLE
                    mapView.visibility = View.INVISIBLE
                    binding.recenter.visibility = View.VISIBLE
                    binding.floatButtonStop.visibility = View.VISIBLE
                    initNav()
                    binding.floatButtonNav.setImageResource(R.drawable.crash_36)
                    binding.spinner.visibility = View.INVISIBLE
                    binding.updateMap.visibility = View.INVISIBLE
                    bcLayer.setProperties(visibility(Property.NONE))
                    scLayer.setProperties(visibility(Property.NONE))
                    siLayer.setProperties(visibility(Property.NONE))

                }
            else {
                    setToolbarBasic(toolbar)
                    mainActivity.isBottomNavigationVisible(true)
                    mapView2.visibility = View.INVISIBLE
                    binding.floatButtonStop.visibility = View.INVISIBLE
                    mapView.visibility = View.VISIBLE
                    binding.recenter.visibility = View.INVISIBLE
                    mapView.getMapAsync(this)
                    binding.floatButtonNav.setImageResource(R.drawable.baseline_assistant_direction_black_36)
                    binding.spinner.visibility = View.VISIBLE
                    binding.updateMap.visibility = View.VISIBLE
                    bcLayer.setProperties(visibility(Property.VISIBLE))
                    scLayer.setProperties(visibility(Property.VISIBLE))
                    siLayer.setProperties(visibility(Property.VISIBLE))

            }
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun initNav(){
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
                            NavigationCameraState.FOLLOWING -> binding.recenter.visibility = View.INVISIBLE
                            NavigationCameraState.TRANSITION_TO_OVERVIEW,
                            NavigationCameraState.OVERVIEW,
                            NavigationCameraState.IDLE -> binding.recenter.visibility =
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
        mapboxNavigation.startTripSession()
    }

    private fun updateNavCamera(){

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
        val origin = navigationLocationProvider.lastLocation?.let {
            Point.fromLngLat(it.longitude, it.latitude)
        } ?: return

        mapboxNavigation.requestRoutes(
            RouteOptions.builder()
                .applyDefaultNavigationOptions()
                .applyLanguageAndVoiceUnitOptions(mainActivity)
                .coordinatesList(listOf(origin, destination))
                .build(),
            object : RouterCallback {
                override fun onRoutesReady(
                    routes: List<DirectionsRoute>,
                    routerOrigin: RouterOrigin
                ) {
                    setRouteAndStartNavigation(routes.first())
                }

                override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
                    // no impl
                }

                override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                    // no impl
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

    private fun changeFloatButtonHeight(){
        // change the float button height
        val FBHeight = binding.floatButton.layoutParams as CoordinatorLayout.LayoutParams
        if(binding.tripProgressCard.visibility.equals(View.VISIBLE))
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

}


    /*
     *  Collect data
     */
    class fetchdata(): Thread() {
        var data: String = ""
        override fun run() {
          try {
            feature.clear()
            /*---    Fetch the data from team's API  ---*/
            val url = URL("http://13.211.206.2/accidents?location=" + lga)
            val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream: InputStream = httpURLConnection.inputStream
            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            data = bufferReader.readLine()

            if(data.isNotEmpty()){
                val jsonObject = JSONObject(data)
                val points: JSONArray = jsonObject.getJSONArray("results")
                locationList.clear()
                for(point in 0..points.length()-1){
                    val location: JSONObject = points.getJSONObject(point)
                    val eachPoint = Point.fromLngLat(location.get("long").
                    toString().toDouble(),
                        location.get("lat").toString().toDouble())
                    locationList.add(eachPoint)
                }
            }
        }catch(e: MalformedURLException){
            e.printStackTrace()

        }catch(e: IOException){
            e.printStackTrace()
        }

        Handler(Looper.getMainLooper()).post {
            run {
                for(each in 0..locationList.size-1){
                    Log.d("Hello Handler Locationlist Size is:", locationList.size.toString())
                    feature.add(Feature.fromGeometry(locationList[each]))
                }
            }
        }
    }


}




