package com.example.safodel.fragment.menuBottom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.example.safodel.R
import com.example.safodel.databinding.FilterCardsBinding
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
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
import java.math.RoundingMode
import com.mapbox.maps.MapboxMap as MapboxMap2
import com.mapbox.maps.MapView as MapView2
import com.mapbox.maps.Style as Style2
import com.mapbox.geojson.*
import com.mapbox.mapboxsdk.style.sources.Source
import com.mapbox.navigation.ui.maps.internal.route.line.MapboxRouteLineApiExtensions.setRoutes


private val locationList: ArrayList<Point> = ArrayList()
private val pathsList: ArrayList<ArrayList<Point>> = ArrayList()
private var feature: ArrayList<Feature> = ArrayList()
private var alertsFeature: ArrayList<Feature> = ArrayList()
private var suburb: String = "MELBOURNE"
private var spinnerTimes = 0
private var alertClickTimes = 0
private var accidentClickTimes = 0
private lateinit var toast: Toast
private lateinit var fragmentNow: OnMapReadyCallback
private lateinit var mapViewModel: MapAccidentViewModel

// Retrofit
private lateinit var suburbInterface: SuburbInterface


class MapFragment : BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback, PermissionsListener {

    // View
    private lateinit var toolbar: Toolbar
    private lateinit var dialog: MaterialDialog
    private lateinit var diaglogFilter: MaterialDialog
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
    private lateinit var filterCards : View
    private lateinit var markerViewManager: MarkerViewManager
    private lateinit var alertMarkerBubble: MarkerView
    private lateinit var accidentMarkerBubble: MarkerView
    private lateinit var filterCardBinding: FilterCardsBinding

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
        filterCards = binding.filterCards.root
        filterCardBinding = binding.filterCards
        fragmentNow = this
        mapViewModel = MapAccidentViewModel()
        mapView2 = binding.mapView2  // for navigation
        mapboxMap2 = mapView2.getMapboxMap() // for navigation
        diaglogFilter = MaterialDialog(mainActivity)
        spotlightRoot = FrameLayout(requireContext())
        setToolbarBasic(toolbar)


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
                removeAlertBubble()
                setDialog()
                spinnerTimes++ // calculate the times to test
                if (spinnerTimes >= 1) {
                    suburb = parent?.getItemAtPosition(position).toString()
                    callAllClient(true)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        suburbInterface = SuburbClient.getSuburbService()

        // change the float button height
        changeFloatButtonHeight()
        // basic location and position


        setDialog()
//        callAlertsClient()
//        callSuburbClient()
//        callPathsClient()
        callAllClient(false)

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
        markerViewManager = MarkerViewManager(mapView, mapboxMap)
        this.mapboxMap.setStyle(Style.LIGHT) {

            cameraAutoZoomToSuburb()

            // get user location
            enableLocationComponent(it)

            checkDataEmpty()

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

            // paths source
            it.addSource(
                GeoJsonSource(
                    "pathsSource",
                    FeatureCollection.fromFeatures(
                        arrayOf(
                            Feature.fromGeometry(
                                MultiLineString.fromLngLats(pathsList as List<MutableList<Point>>)
                            )
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

            /*-- Add Paths layer --*/
            it.addLayer(
                LineLayer("multi_line_layer", "pathsSource").withProperties(
                    lineOpacity(.7f),
                    lineCap(Property.LINE_CAP_SQUARE),
                    lineJoin(Property.LINE_JOIN_ROUND),
                    lineWidth(
                        interpolate(
                            exponential(6f), zoom(),
                            stop(10,3f),
                            stop(15,12f),
                            stop(20,48f)
                        )
                    ),
                    lineColor(ContextCompat.getColor(requireActivity(), R.color.blueSky))
                )
            )

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
                    )
                ),
                iconIgnorePlacement(false),
                iconAllowOverlap(false)
            )
            //alertIconLayer.minZoom = 15f
            it.addLayer(alertIconLayer)

            mapboxMap.addOnMapClickListener {
                val pointHere = Point.fromLngLat(it.longitude, it.latitude)
                val pointFHere = mapboxMap.projection.toScreenLocation(it)
                val featureAlert = mapboxMap.queryRenderedFeatures(pointFHere, "alert_layer")
                val featureAccident = mapboxMap.queryRenderedFeatures(pointFHere,"basic_circle_cayer")

                if(featureAccident.isNotEmpty()){
                    val type = featureAccident[0].getStringProperty("type")
                    val road = featureAccident[0].getStringProperty("road")
                    val severity = featureAccident[0].getStringProperty("severity")
                    handleClickAccident(type,road,severity,pointHere,true)
                }
                else if (featureAlert.isNotEmpty()) {
                    val type = featureAlert[0].getStringProperty("type")
                    handleClickAlert(type, pointHere, true)
                } else handleClickAlert("", pointHere, false)
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

        setZoombutton()

        /*
            Setting filter card in the Mapview1
         */
        filterTrafficListener()
        filterPathsListener()
        filterAccidentListener()
        filterAlertsListener()

    }

    fun handleClickAlert(type: String, point: Point, boolean: Boolean) {
        //markerViewManager.removeMarker(alertMarkerBubble)
        removeAlertBubble()

        if (boolean) {
            val alertBubble = LayoutInflater.from(mainActivity).inflate(
                R.layout.marker_alert_bubble, null
            )

            alertBubble.setLayoutParams(FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))

            val alertTitle = alertBubble.findViewById<TextView>(R.id.marker_alert_title)
            val alertSnippet = alertBubble.findViewById<TextView>(R.id.marker_alert_snippet)

            alertTitle.text = type
            alertSnippet.text = getAlertPointString(point)
            alertMarkerBubble = MarkerView(LatLng(point.latitude(), point.longitude()), alertBubble)

            markerViewManager.addMarker(alertMarkerBubble)
            alertClickTimes++
        }
    }

    fun removeAlertBubble() {
        if (alertClickTimes > 0) {
            markerViewManager.removeMarker(alertMarkerBubble)
            alertClickTimes--
        }

        if (accidentClickTimes > 0) {
            markerViewManager.removeMarker(accidentMarkerBubble)
            accidentClickTimes--
        }
    }

    fun getAlertPointString(point: Point)
            : String {
        val sb = StringBuilder()
        sb.append('(')
        sb.append(point.longitude().toBigDecimal().setScale(3, RoundingMode.HALF_EVEN).toString())
        sb.append(", ")
        sb.append(point.latitude().toBigDecimal().setScale(3, RoundingMode.HALF_EVEN).toString())
        sb.append(')')
        return sb.toString()
    }

    fun handleClickAccident(type: String, road: String, severity: String,point: Point,boolean: Boolean) {
        //markerViewManager.removeMarker(alertMarkerBubble)
        removeAlertBubble()

        if (boolean) {
            val accidentBubble = LayoutInflater.from(mainActivity).inflate(
                R.layout.marker_accident_bubble,null
            )

            accidentBubble.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            val accidentTitle = accidentBubble.findViewById<TextView>(R.id.marker_accident_title)
            val accidentRoad = accidentBubble.findViewById<TextView>(R.id.marker_accident_road)
            val accidentSeverity  = accidentBubble.findViewById<TextView>(R.id.marker_accident_severity)

            accidentTitle.text = type
            accidentRoad.text = road
            accidentSeverity.text = severity
            accidentMarkerBubble = MarkerView(LatLng(point.latitude(), point.longitude()), accidentBubble)

            markerViewManager.addMarker(accidentMarkerBubble)
            accidentClickTimes++
        }
    }

    fun onMapUpdate(){

        cameraAutoZoomToSuburb()
        checkDataEmpty()
        mapboxMap.getStyle {

            enableLocationComponent(it)

            val source = it.getSourceAs<GeoJsonSource>("source")
            if (source != null) {
                source.setGeoJson(FeatureCollection.fromFeatures(
                    ArrayList<Feature>(
                        feature
                    )
                ))
            }


            val alertSource = it.getSourceAs<GeoJsonSource>("alertSource")
            if (alertSource != null) {
                alertSource.setGeoJson(FeatureCollection.fromFeatures(
                    ArrayList<Feature>(
                        alertsFeature
                    )
                ))
            }

            // paths source
            val pathsSource = it.getSourceAs<GeoJsonSource>("pathsSource")
            if (pathsSource != null) {
                pathsSource.setGeoJson(FeatureCollection.fromFeatures(
                    arrayOf(
                        Feature.fromGeometry(
                            MultiLineString.fromLngLats(pathsList as List<MutableList<Point>>)
                        )
                    )
                ))
            }
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
        dialog.dismiss()
    }


    fun setZoombutton(){

        binding.floatButtonZoomIn.setOnClickListener {
            updateCamera(1.0)
        }
        binding.floatButtonZoomIn.setOnLongClickListener {
            updateCamera(5.0)
            false
        }

        binding.floatButtonZoomOut.setOnClickListener {
            updateCamera(-1.0)
        }
        binding.floatButtonZoomOut.setOnLongClickListener {
            updateCamera(-5.0)
            false
        }
    }

    /*
            Update the camera base on new zoom level.
     */
    fun updateCamera(zoomLevel: Double){
        val currentZoomLevel = mapboxMap.cameraPosition.zoom
        mapboxMap.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(
                    CameraPosition.Builder()
                        .zoom(currentZoomLevel + zoomLevel)
                        .build()
                ),3000
        )
    }

    /*-- Camera auto zoom to the suburb area --*/
    fun cameraAutoZoomToSuburb(){

        if (feature.size != 0 && locationList.size != 0) {
            suburbPoint = locationList[0]
            val position = CameraPosition.Builder()
                .target(LatLng(suburbPoint.latitude(), suburbPoint.longitude()))
                .zoom(9.0)
                .build()
            mapboxMap.cameraPosition = position
        }
    }

    /*-- Make a toast when data is updating --*/
    fun checkDataEmpty(){

        if (feature.size == 0 && locationList.size == 0) {
            toast.setText(getString(R.string.no_data))
            toast.show()
        }
    }


    /*-- get the location permission --*/
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
        if (this::markerViewManager.isInitialized)
            markerViewManager.onDestroy()
        mapView.onDestroy()
        mapboxNavigation.onDestroy()
        if (::mapboxNavigation.isInitialized) {
            mapboxNavigation.unregisterRoutesObserver(routesObserver)
            mapboxNavigation.unregisterLocationObserver(locationObserver)
            mapboxNavigation.unregisterRouteProgressObserver(routeProgressObserver)
        }
        MapboxNavigationProvider.destroy()
        mainActivity.isBottomNavigationVisible(true)
        alertClickTimes = 0
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
            Toast.makeText(context, getString(R.string.location_granted), Toast.LENGTH_LONG)
                .show()
        }
    }


    /* --
            UI part - Hide or Show the crash relative view.
    -- */
    fun changeToNav() {
        removeAlertBubble()
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
                    binding.filterCards.root.visibility = View.INVISIBLE
                    bcLayer.setProperties(visibility(Property.NONE))
                    scLayer?.setProperties(visibility(Property.NONE))
                    siLayer?.setProperties(visibility(Property.NONE))

                    /*--    Edit the Search bar    --*/
                    val coroutineScope = CoroutineScope(Dispatchers.Main)
                    coroutineScope.launch {
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
                            setToolbarBasic(toolbar)
                            mainActivity.isBottomNavigationVisible(true)
                            mapView2.visibility = View.INVISIBLE
                            binding.floatButtonStop.visibility = View.INVISIBLE
                            binding.searchBar.visibility = View.INVISIBLE
                            mapView.visibility = View.VISIBLE
                            recenter.visibility = View.INVISIBLE
                            binding.floatButtonNav.setImageResource(R.drawable.baseline_assistant_direction_black_36)
                            binding.spinner.visibility = View.VISIBLE
                            binding.filterCards.root.visibility = View.VISIBLE
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


    /*
        Update the navgation camera
     */
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


    /*
        Find route of the navigaiton
     */
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

    /*
        Setting the route of navigation
     */
    private fun setRouteAndStartNavigation(route: DirectionsRoute) {
        // set route
        mapboxNavigation.setRoutes(listOf(route))

        // move the camera to overview when new route is available
        navigationCamera.requestNavigationCameraToOverview()
        binding.tripProgressCard.visibility = View.VISIBLE
        changeFloatButtonHeight()
    }

    /*
        Clear the route of the navigaion function
     */
    private fun clearRouteAndStopNavigation() {
        // clear
        mapboxNavigation.setRoutes(listOf())
        binding.maneuverView.visibility = View.INVISIBLE
        binding.tripProgressCard.visibility = View.INVISIBLE
        changeFloatButtonHeight()

    }


    /*
        Change the height(margin buttom) of the float buttons
     */
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

        val FBHeightZoomOut = binding.floatButtonZoomOut.layoutParams as CoordinatorLayout.LayoutParams
        FBHeightZoomOut.bottomMargin = FBHeight.bottomMargin * 3
        binding.floatButtonZoomOut.layoutParams = FBHeightZoomOut

        val FBHeightZoomIn = binding.floatButtonZoomIn.layoutParams as CoordinatorLayout.LayoutParams
        FBHeightZoomIn.bottomMargin = FBHeight.bottomMargin * 4
        binding.floatButtonZoomIn.layoutParams = FBHeightZoomIn
    }


    /**
     * get the alert data by calling retrofit to connect to the server
     */
    private fun callAlertsClient() {
        alertsFeature.clear()
        //val fragmentNow = this
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
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
                                val eachPoint =
                                    Point.fromLngLat(each.location.long, each.location.lat)
                                val eachFeature = Feature.fromGeometry(eachPoint)
                                eachFeature.addStringProperty("type", each.type)
                                alertsFeature.add(eachFeature)
                            }
                        }
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

    /**
     * get the suburb accidents data by calling retrofit to connect to the server
     */
    private fun callSuburbClient() {
        feature.clear()
        locationList.clear()

        val callAsync2: Call<SuburbMapResponse> = suburbInterface.mapRepos(
            "accidents",
            suburb
        )

        callAsync2.enqueue(object : Callback<SuburbMapResponse?> {
            override fun onResponse(
                call: Call<SuburbMapResponse?>?,
                response: Response<SuburbMapResponse?>
            ) {
                if (response.isSuccessful) {
                    val resultList = response.body()?.suburbMapAccidents
                    if (resultList?.isNotEmpty() == true) {
                        for (each in resultList) {
                            val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
                            locationList.add(eachPoint)
                            val eachFeature = Feature.fromGeometry(eachPoint)
                            eachFeature.addStringProperty("type",each.type)
                            eachFeature.addStringProperty("road",each.road_name)
                            eachFeature.addStringProperty("severity",each.severity.toString())
                            feature.add(eachFeature)
                        }
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

    /**
     * get the paths data by calling retrofit to connect to the server
     */
    private fun callPathsClient() {
        pathsList.clear()
        val callAsync: Call<SuburbPathsResponse> = suburbInterface.pathsRepos(
            "paths",
            suburb
        )

        callAsync.enqueue(object : Callback<SuburbPathsResponse?> {
            override fun onResponse(
                call: Call<SuburbPathsResponse?>?,
                response: Response<SuburbPathsResponse?>
            ) {
                if (response.isSuccessful) {
                    val geometries = response.body()!!.pathResults
                    if (geometries.isNotEmpty()) {
                        for (geometry in geometries) {
                            var locations = geometry.geometries
                            var coordinates = ArrayList<Point>()
                            for (location in locations) {
                                val eachPoint = Point.fromLngLat(location.lng, location.lat)
                                coordinates.add(eachPoint)
                            }
                            pathsList.add(coordinates)
                        }

                    }
                    Log.d("testing", pathsList.size.toString())
                } else {
                    Log.d("Error ", "Response failed")
                }
            }

            override fun onFailure(call: Call<SuburbPathsResponse?>, t: Throwable) {
                toast.cancel()
                toast.setText("${t.message}")
                toast.show()
            }
        })
    }


    /*
        Call the retrofit cilent to get all data in the map
     */
    fun callAllClient(boolean: Boolean){
        alertsFeature.clear()
        feature.clear()
        locationList.clear()
        pathsList.clear()
        val callAsync: Call<SuburbAllResponse> = suburbInterface.allRepos(
            "all",
            suburb
        )

        callAsync.enqueue(object : Callback<SuburbAllResponse?> {
            override fun onResponse(
                call: Call<SuburbAllResponse?>?,
                response: Response<SuburbAllResponse?>
            ) {
                if (response.isSuccessful) {
                    val allresult = response.body()!!.suburbAllAccidents

                            val accidents = allresult.accidents
                            val paths = allresult.paths
                            val alerts = allresult.alerts
                            if (alerts.isNotEmpty()) {
                                for (each in alerts) {
                                    val eachPoint =
                                        Point.fromLngLat(each.location.long, each.location.lat)
                                    val eachFeature = Feature.fromGeometry(eachPoint)
                                    eachFeature.addStringProperty("type", each.type)
                                    alertsFeature.add(eachFeature)
                                }
                            }

                            if (accidents.isNotEmpty()) {
                                for (each in accidents) {
                                    val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
                                    locationList.add(eachPoint)
                                    val eachFeature = Feature.fromGeometry(eachPoint)
                                    eachFeature.addStringProperty("type",each.type)
                                    eachFeature.addStringProperty("road",each.road_name)
                                    eachFeature.addStringProperty("severity",each.severity)
                                    feature.add(eachFeature)
                                }
                            }

                            if (paths.isNotEmpty()) {
                                for (geometry in paths) {
                                    val locations = geometry.geometries
                                    val coordinates = ArrayList<Point>()
                                    for (location in locations) {
                                        val eachPoint = Point.fromLngLat(location.lng, location.lat)
                                        coordinates.add(eachPoint)
                                    }
                                    pathsList.add(coordinates)
                                }
                            }

                    if(boolean)
                        onMapUpdate()
                    else mapView.getMapAsync(fragmentNow)
                } else {
                    dialog.dismiss()
                    Timber.d("Response failed")
                }
            }

            override fun onFailure(call: Call<SuburbAllResponse?>, t: Throwable) {
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


    /*
        Reset the height fo the search bar and filter cards,
         and set the icon of the cards.
     */
    private fun fitSearchMap1() {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            // try to get the height of status bar and then margin top
            val searchBarMap1Height = searchBarMap1.layoutParams as CoordinatorLayout.LayoutParams
            val filterCardsHeight = filterCards.layoutParams as CoordinatorLayout.LayoutParams
            while (searchBarMap1Height.topMargin == 0)
                searchBarMap1Height.topMargin = mainActivity.getStatusHeight()
            while (filterCardsHeight.topMargin == 0)
                filterCardsHeight.topMargin = searchBarMap1Height.topMargin + searchBarMap1.measuredHeight + 10
            searchBarMap1.layoutParams = searchBarMap1Height
            filterCards.layoutParams = filterCardsHeight

            binding.filterCards.filterPaths.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_path, 0, 0, 0
            )

            binding.filterCards.filterTraffic.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_traffic,0,0,0
            )

            binding.filterCards.filterAccidents.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_accident,0,0,0
            )

            binding.filterCards.filterAlerts.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_alert,0,0,0
            )

            this.cancel()
        }
    }



    ///////////////////////////////////////////////////////////////////////////////////////
    ///                                 Filter                                         ////
    ///////////////////////////////////////////////////////////////////////////////////////


    /*
        Setting the filter traffic listener and the reaction
     */
    fun filterTrafficListener(){
        val filterTraffic =  filterCardBinding.filterTraffic
        filterTraffic.setOnClickListener {
            if(trafficPlugin.isVisible == false){
                trafficPlugin.setVisibility(true)
                filterTraffic.setTextColor(ContextCompat.getColor(mainActivity, R.color.black))
                filterTraffic.alpha = 1f
            }

            else {
                trafficPlugin.setVisibility(false)
                filterTraffic.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray))
                filterTraffic.alpha = 0.8f
            }
        }
    }


    /*
        Setting the filter path listener and the reaction
     */
    fun filterPathsListener(){
        val filterPaths = filterCardBinding.filterPaths
        var filterStatus = true
        filterPaths.setOnClickListener {
            if(filterStatus){
                mapboxMap.getStyle {
                    val layer = it.getLayer("multi_line_layer")
                    if(layer != null){
                        layer.setProperties(visibility(Property.NONE))
                        filterPaths.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray))
                        filterPaths.alpha = 0.8f
                        filterStatus = false
                    }
                }
            }
            else{
                mapboxMap.getStyle {
                    val layer = it.getLayer("multi_line_layer")
                    if(layer != null){
                        layer.setProperties(visibility(Property.VISIBLE))
                        filterPaths.setTextColor(ContextCompat.getColor(mainActivity, R.color.black))
                        filterPaths.alpha = 1f
                        filterStatus = true
                    }
                }
            }
        }
    }


    /*
        Setting the filter accidents listener and the reaction
     */
    fun filterAccidentListener(){
        val filterAccidents = filterCardBinding.filterAccidents
        var filterStatus = true
        filterAccidents.setOnClickListener {
            if(filterStatus){
                mapboxMap.getStyle {
                    val layer1 = it.getLayer("basic_circle_cayer")
                    val layer2 = it.getLayer("shadow_circle_cayer")
                    val layer3 = it.getLayer("icon_layer")
                    if (layer1 != null && layer2 != null && layer3 != null) {
                            layer1.setProperties(visibility(Property.NONE))
                            layer2.setProperties(visibility(Property.NONE))
                            layer3.setProperties(visibility(Property.NONE))
                        filterAccidents.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray))
                        filterAccidents.alpha = 0.8f
                        filterStatus = false
                    }
                }
            }
            else{
                mapboxMap.getStyle {
                    val layer1 = it.getLayer("basic_circle_cayer")
                    val layer2 = it.getLayer("shadow_circle_cayer")
                    val layer3 = it.getLayer("icon_layer")
                    if (layer1 != null && layer2 != null && layer3 != null) {
                            layer1.setProperties(visibility(Property.VISIBLE))
                            layer2.setProperties(visibility(Property.VISIBLE))
                            layer3.setProperties(visibility(Property.VISIBLE))
                        filterAccidents.setTextColor(ContextCompat.getColor(mainActivity, R.color.black))
                        filterAccidents.alpha = 1f
                        filterStatus = true
                    }
                }
            }
        }
    }


    /*
        Setting the filter alert listener and the reaction
     */
    fun filterAlertsListener(){
        //alert_layer
        val filterAlerts = filterCardBinding.filterAlerts
        var filterStatus = true
        filterAlerts.setOnClickListener {
            if(filterStatus){
                mapboxMap.getStyle {
                    val layer1 = it.getLayer("alert_layer")
                    if (layer1 != null) {
                        layer1.setProperties(visibility(Property.NONE))
                        filterAlerts.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray))
                        filterAlerts.alpha = 0.8f
                        filterStatus = false
                    }
                }
            }
            else{
                mapboxMap.getStyle {
                    val layer1 = it.getLayer("alert_layer")
                    if (layer1 != null) {
                        layer1.setProperties(visibility(Property.VISIBLE))
                        filterAlerts.setTextColor(ContextCompat.getColor(mainActivity, R.color.black))
                        filterAlerts.alpha = 1f
                        filterStatus = true
                    }
                }
            }
        }
    }

}





