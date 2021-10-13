package com.example.safodel.fragment.menuBottom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color.parseColor
import android.graphics.RectF
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.ajithvgiri.searchdialog.OnSearchItemSelected
import com.ajithvgiri.searchdialog.SearchListItem
import com.ajithvgiri.searchdialog.SearchableDialog
import com.example.safodel.R
import com.example.safodel.databinding.FilterCardsBinding
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.*
import com.example.safodel.retrofit.SuburbClient
import com.example.safodel.retrofit.SuburbInterface
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.ui.map.TrafficPlugin
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.core.constants.Constants
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
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.core.formatter.MapboxDistanceFormatter
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.core.trip.session.RouteProgressObserver
import com.mapbox.navigation.ui.maneuver.api.MapboxManeuverApi
import com.mapbox.navigation.ui.maps.camera.NavigationCamera
import com.mapbox.navigation.ui.maps.camera.data.MapboxNavigationViewportDataSource
import com.mapbox.navigation.ui.maps.camera.lifecycle.NavigationBasicGesturesHandler
import com.mapbox.navigation.ui.maps.camera.state.NavigationCameraState
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
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin

/*
    Global basic value
 */
private val locationList: ArrayList<Point> = ArrayList()
private val pathsList: ArrayList<ArrayList<Point>> = ArrayList()
private var feature: ArrayList<Feature> = ArrayList()
private var alertsFeature: ArrayList<Feature> = ArrayList()
private var destinationFeature: ArrayList<Feature> = ArrayList()
private var suburb: String = "MELBOURNE"
private var spinnerTimes = 0
private var alertClickTimes = 0
private var accidentClickTimes = 0
private var destinationClickTimes = 0
private var spinnerIndex = 0
private var routeRequestID = 0L


/*
    Global view object
 */
private lateinit var toast: Toast
private lateinit var fragmentNow: OnMapReadyCallback
private lateinit var floatButton: FloatingActionButton
private lateinit var floatButtonNav: FloatingActionButton
private lateinit var tripProgressCard: CardView
private lateinit var floatButtonStop: FloatingActionButton


// Retrofit
private lateinit var suburbInterface: SuburbInterface


class MapFragment : BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback, PermissionsListener,OnSearchItemSelected {

    // View
    private lateinit var toolbar: Toolbar
    private lateinit var dialog: MaterialDialog
    private lateinit var dialogFilter: MaterialDialog
    private lateinit var recenter: View
    private lateinit var spotlightRoot: FrameLayout
    private lateinit var spinnerText: TextView


    // Map
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mapboxMap2: MapboxMap2
    private lateinit var mainActivity: MainActivity
    private lateinit var mapView: MapView
    private lateinit var mapView2: MapView2
    private lateinit var searchBarMap1: FrameLayout
    private lateinit var suburbPoint: Point
    private lateinit var trafficPlugin: TrafficPlugin
    private lateinit var filterCards : View
    private lateinit var markerViewManager: MarkerViewManager
    private lateinit var alertMarkerBubble: MarkerView
    private lateinit var accidentMarkerBubble: MarkerView
    private lateinit var destinationMarkerBubble: MarkerView
    private lateinit var filterCardBinding: FilterCardsBinding
    private lateinit var floatButtonZoomIn: View
    private lateinit var floatButtonZoomOut: View
    private lateinit var floatButtonDestination: View
    private lateinit var searchableDialog: SearchableDialog
    private lateinit var simpleRoute: DirectionsRoute

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

    /* ----- Location progress callbacks ----- */
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

    /* ----- route progress callbacks ----- */
    private val routeProgressObserver =
        RouteProgressObserver { routeProgress -> // update the camera position to account for the progressed fragment of the route
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

    /*--   to know when the route list changed  --*/
    private val routesObserver =
        RoutesObserver { routes ->
            if (routes.isNotEmpty()) {
                val selectedRoute = routes.first()
                routeLineAPI.setRoutes(listOf(RouteLine(selectedRoute, null))
                ) { value ->
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
                viewportDataSource.onRouteChanged(selectedRoute)
            } else {
                viewportDataSource.clearRouteData()
                navigationCamera.requestNavigationCameraToIdle()
                ifNonNull(routeLineAPI, routeLineView, mapboxMap2.getStyle()) { api, view, style ->
                    api.clearRouteLine { value -> view.renderClearRouteLineValue(style, value) }
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
        searchBarMap1 = binding.searchMap1
        filterCards = binding.filterCards.root
        filterCardBinding = binding.filterCards
        fragmentNow = this
        mapView2 = binding.mapView2  // for navigation
        mapboxMap2 = mapView2.getMapboxMap() // for navigation
        dialogFilter = MaterialDialog(mainActivity)
        spotlightRoot = FrameLayout(requireContext())
        floatButtonZoomIn = binding.floatButtonZoomIn
        floatButtonZoomOut = binding.floatButtonZoomOut
        floatButtonDestination = binding.floatButtonDestination
        floatButton = binding.floatButton
        floatButtonNav = binding.floatButtonNav
        tripProgressCard = binding.tripProgressCard
        floatButtonStop = binding.floatButtonStop
        spinnerText = binding.spinnerText
        setToolbarBasic(toolbar)


        // request permission of user location
        permissionsManager = PermissionsManager(this)
        permissionsManager.requestLocationPermissions(activity)

        // Set Dialog
        dialog = MaterialDialog(requireContext())

        // spinner init
        searchableDialog = SearchableDialog(mainActivity, suburbList, getString(R.string.select_suburb))
        searchableDialog.setOnItemSelected(this)
        binding.searchMap1.setOnClickListener {
            searchableDialog.show()
            searchableDialog.recyclerView.smoothScrollToPosition(spinnerIndex)
        }

        suburbInterface = SuburbClient.getSuburbService()

        // change the float button height
        changeFloatButtonHeight()


        /*
            Start the function
         */
        setDialog()
        callAllClient(false)

        // go to the user's current location
        binding.floatButton.setOnClickListener {
            if(this::mapboxMap.isInitialized){
                mapboxMap.style?.let { it1 -> enableLocationComponent(it1,true) }
            if (this::navigationCamera.isInitialized) {
                navigationCamera.requestNavigationCameraToOverview()
                updateNavCamera()
            }
            }
        }

        // initialize Mapbox Navigation
        mapboxNavigation = MapboxNavigation(
            NavigationOptions.Builder(mainActivity)
                .accessToken(getString(R.string.mapbox_access_token))
                .build()
        )


        fitSearchMap1() // fit windows to the search bar in Mapview1
        mapView.onCreate(savedInstanceState) // init the map
        return binding.root
    }

    /*
        update the map when call getMapAsync()
     */
    override fun onMapReady(mapboxMap: MapboxMap) {
        if(mapView.isDestroyed) return
        mapboxMap.setMaxZoomPreference(20.0)
        mapboxMap.setMinZoomPreference(5.0)
        this.mapboxMap = mapboxMap
        markerViewManager = MarkerViewManager(mapView, mapboxMap)

        /*
            Setting style of the map
         */
        this.mapboxMap.setStyle(Style.LIGHT) { it ->
            cameraAutoZoomToSuburb()
            val localizationPlugin = LocalizationPlugin(mapView, mapboxMap, it)
            try {
                val sf = mainActivity.getSharedPreferences("language", Activity.MODE_PRIVATE)
                var lang = sf.getString("lang", "name_en")
                lang = when(lang){
                    "zh_CN" -> "name_zh-Hans"
                    "zh_TW" -> "name_zh"
                    else -> "name_en"
                }
                localizationPlugin.setMapLanguage(lang)
            } catch (exception: RuntimeException) {
                Timber.d(exception.toString())
            }

            // get user location
            enableLocationComponent(it,false)

            // Check data is empty or not
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

            /*--   Add  destination image   --*/
            it.addImage(
                "destination_image",
                BitmapUtils.getBitmapFromDrawable(
                    context?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1,
                            R.drawable.destination_point
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

            it.addSource(
                GeoJsonSource(
                    "destinationSource", FeatureCollection.fromFeatures(
                        ArrayList<Feature>(
                            destinationFeature
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

            // simple route source
            it.addSource(
                GeoJsonSource(
                    "simpleRouteSource"
                )
            )
            trafficPlugin = TrafficPlugin(mapView, mapboxMap, it)
            trafficPlugin.setVisibility(true)



            /*-- Add layer --*/
            val basicCircle: CircleLayer =
                CircleLayer("basic_circle_layer", "source").withProperties(
                    circleColor(parseColor("#ff0015")),
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
            val shadowTransitionCircleLayer = CircleLayer("shadow_circle_layer", "source")
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
            it.addLayerBelow(shadowTransitionCircleLayer, "basic_circle_layer")

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

            /*-- Add Simpel route layer --*/
            it.addLayer(
                LineLayer("simple_route_layer", "simpleRouteSource").withProperties(
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
                    lineColor(ContextCompat.getColor(requireActivity(), R.color.gray2))
                )
            )

            /*
                add the icon layer
             */
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

            /*-- Add destination layer --*/
            val destinationIconLayer = SymbolLayer("destination_layer", "destinationSource")
            destinationIconLayer.withProperties(
                visibility(Property.VISIBLE),
                iconImage("destination_image"),
                iconSize(
                    interpolate(
                        linear(), zoom(),
                        stop(10, 0.05f),
                        stop(15, 0.075f),
                        stop(20.0f, 0.15f)
                    )
                ),
                iconIgnorePlacement(false),
                iconAllowOverlap(false)
            )
            it.addLayer(destinationIconLayer)

            /*
                Handle click event -- pop up windows
             */
            mapboxMap.addOnMapClickListener {

                val pointHere = Point.fromLngLat(it.longitude, it.latitude)
                val pointFHere = mapboxMap.projection.toScreenLocation(it)

                val rectF = RectF(pointFHere.x -20, pointFHere.y-20,pointFHere.x+20,pointFHere.y+20)

                val featureAlert = mapboxMap.queryRenderedFeatures(rectF, "alert_layer")
                val featureAccident = mapboxMap.queryRenderedFeatures(rectF,"shadow_circle_layer")
                val featureDestination = mapboxMap.queryRenderedFeatures(rectF,"destination_layer")

                when {
                    featureAccident.isNotEmpty() -> {
                        val type = featureAccident[0].getStringProperty("type")
                        val road = featureAccident[0].getStringProperty("road")
                        val severity = featureAccident[0].getStringProperty("severity")
                        val year = featureAccident[0].getNumberProperty("year")
                        val long = featureAccident[0].getNumberProperty("long")
                        val lat = featureAccident[0].getNumberProperty("lat")
                        val pointNow = Point.fromLngLat(long as Double, lat as Double)
                        handleClickAccident(year.toInt(),type,road,severity,pointNow,true)
                    }
                    featureAlert.isNotEmpty() -> {
                        val type = featureAlert[0].getStringProperty("type")
                        val long = featureAlert[0].getNumberProperty("long")
                        val lat = featureAlert[0].getNumberProperty("lat")
                        val pointNow = Point.fromLngLat(long as Double, lat as Double)
                        handleClickAlert(type, pointNow, true)
                    }
                    featureDestination.isNotEmpty() -> {
                        val title = featureDestination[0].getStringProperty("title")
                        val long = featureDestination[0].getNumberProperty("long")
                        val lat = featureDestination[0].getNumberProperty("lat")
                        val pointNow = Point.fromLngLat(long as Double, lat as Double)
                        handleClickDestination(title, pointNow, true)
                    }
                    else -> handleClickAlert("", pointHere, false)
                }
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

            /*-- Check destination function   --*/
            floatButtonDestination.setOnClickListener {
                val intent: Intent = PlaceAutocomplete.IntentBuilder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .placeOptions(
                        PlaceOptions.builder()
                            .backgroundColor(parseColor("#EEEEEE"))
                            .country("AU")
                            .limit(10)
                            .build(PlaceOptions.MODE_CARDS)
                    )
                    .build(mainActivity)
                startActivityForResult(intent, 1)

            }
            dialog.dismiss()
        }

        /*
            Float button click listener setting
         */
        setZooming()

        /*
            Change the margin view of nearly all view in the map fragment
         */
        changeFloatButtonHeight()

        /*
            Setting filter card in the Mapview1
         */
        filterTrafficListener()
        filterPathsListener()
        filterAccidentListener()
        filterAlertsListener()

    }

    /*
        When click the circle layer or alert layer
     */
    @SuppressLint("InflateParams")
    fun handleClickAlert(type: String, point: Point, boolean: Boolean) {

        removeAlertBubble()  // CLear all the bubble window first

        /*
            If can add a bubble window
         */
        if (boolean) {
            val alertBubble = LayoutInflater.from(mainActivity).inflate(
                R.layout.marker_alert_bubble, null
            )
            alertBubble.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val alertTitle = alertBubble.findViewById<TextView>(R.id.marker_alert_title)
            alertTitle.text = type
            alertMarkerBubble = MarkerView(LatLng(point.latitude(), point.longitude()), alertBubble)
            markerViewManager.addMarker(alertMarkerBubble)
            alertClickTimes++
        }
    }

    /*
        Clear all bubble including alert and accident icon
     */
    private fun removeAlertBubble() {
        if (alertClickTimes > 0) {
            if(this::alertMarkerBubble.isInitialized){
                markerViewManager.removeMarker(alertMarkerBubble)
                alertClickTimes--
            }

        }

        if (accidentClickTimes > 0) {
            if(this::accidentMarkerBubble.isInitialized){
                markerViewManager.removeMarker(accidentMarkerBubble)
                accidentClickTimes--
            }

        }

        if (destinationClickTimes > 0) {
            if(this::destinationMarkerBubble.isInitialized){
                markerViewManager.removeMarker(destinationMarkerBubble)
                destinationClickTimes--
            }

        }
    }

    /*
        Handle Accident circle click event.
     */
    @SuppressLint("InflateParams")
    fun handleClickAccident(year:Int, type: String, road: String, severity: String, point: Point, boolean: Boolean) {
        removeAlertBubble()
        if (boolean) {
            val accidentBubble = LayoutInflater.from(mainActivity).inflate(
                R.layout.marker_accident_bubble,null
            )
            accidentBubble.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val accidentsYear = accidentBubble.findViewById<TextView>(R.id.marker_accident_year)
            val accidentTitle = accidentBubble.findViewById<TextView>(R.id.marker_accident_title)
            val accidentRoad = accidentBubble.findViewById<TextView>(R.id.marker_accident_road)
            val accidentSeverity  = accidentBubble.findViewById<TextView>(R.id.marker_accident_severity)
            accidentsYear.text = year.toString()
            accidentTitle.text = type
            accidentRoad.text = road
            accidentSeverity.text = severity
            accidentMarkerBubble = MarkerView(LatLng(point.latitude(), point.longitude()), accidentBubble)
            markerViewManager.addMarker(accidentMarkerBubble)
            accidentClickTimes++
        }
    }

    /*
        Handle destination circle click event.
     */
    @SuppressLint("InflateParams")
    fun handleClickDestination(title: String, point: Point, boolean: Boolean) {
        removeAlertBubble()
        if (boolean) {
            val destinationBubble = LayoutInflater.from(mainActivity).inflate(
                R.layout.marker_destination_bubble,null
            )
            destinationBubble.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val accidentTitle = destinationBubble.findViewById<TextView>(R.id.marker_destination_title)
            val clearButton = destinationBubble.findViewById<TextView>(R.id.clear_destination_point_button)
            val simpleRouteButton = destinationBubble.findViewById<TextView>(R.id.route_destination_point_button)
            clearButton.setOnClickListener {
                destinationFeature.clear()
                removeAlertBubble()
                clearSimplePath()
                updateDestinationPoint()
            }

            simpleRouteButton.setOnClickListener {
                var lat = 0.0
                var long = 0.0
                if(destinationFeature.size != 0){
                    long = destinationFeature[0].getNumberProperty("long") as Double
                    lat = destinationFeature[0].getNumberProperty("lat") as Double
                }
                simpleFindRoute(Point.fromLngLat(long,lat))
            }

            accidentTitle.text = title
            destinationMarkerBubble = MarkerView(LatLng(point.latitude(), point.longitude()), destinationBubble)
            markerViewManager.addMarker(destinationMarkerBubble)
            destinationClickTimes++
        }
    }



    /*
        Update the map without recreate the map and layer
     */
    fun onMapUpdate(){
        if(!this::mapboxMap.isInitialized) return
        cameraAutoZoomToSuburb()
        checkDataEmpty()
        mapboxMap.getStyle {
            enableLocationComponent(it,true)

            it.getSourceAs<GeoJsonSource>("source")?.setGeoJson(
                FeatureCollection.fromFeatures(
                    ArrayList<Feature>(
                        feature
                    )
                )
            )

            // Alert source
            it.getSourceAs<GeoJsonSource>("alertSource")?.setGeoJson(
                FeatureCollection.fromFeatures(
                    ArrayList<Feature>(
                        alertsFeature
                    )
                )
            )

            // paths source
            it.getSourceAs<GeoJsonSource>("pathsSource")?.setGeoJson(
                FeatureCollection.fromFeatures(
                    arrayOf(
                        Feature.fromGeometry(
                            MultiLineString.fromLngLats(pathsList as List<MutableList<Point>>)
                        )
                    )
                )
            )


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

        dialog.dismiss() // remove the dialog
    }

    fun addSimpleRouteToMap(){
        if(!this::mapboxMap.isInitialized) return
        cameraAutoZoomToDesrination()
        mapboxMap.getStyle {
            //enableLocationComponent(it, true)

            it.getSourceAs<GeoJsonSource>("simpleRouteSource")?.setGeoJson(
                FeatureCollection.fromFeatures(
                    arrayOf(
                        Feature.fromGeometry(
                            simpleRoute.geometry()?.let { it1 -> LineString.fromPolyline(it1,Constants.PRECISION_6) }
                        )
                    )
                )
            )

            val layer = it.getLayer("simple_route_layer")
            if(layer != null){
                layer.setProperties(visibility(Property.VISIBLE))
            }

        }

        mapboxNavigation.cancelRouteRequest(routeRequestID)

        /*-- Set the camera's animation --*/
        mapboxMap.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(
                    CameraPosition.Builder()
                        .zoom(10.5)
                        .build()
                ), 3000
        )
    }

    fun clearSimplePath(){
        mapboxMap.getStyle {
            val layer = it.getLayer("simple_route_layer")
            if(layer != null){
                layer.setProperties(visibility(Property.NONE))
            }
        }
    }


    fun addDestinationToMap(){
        updateDestinationPoint()

        /*-- Set the camera's animation --*/
        mapboxMap.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(
                    CameraPosition.Builder()
                        .zoom(10.5)
                        .build()
                ), 3000
        )
    }

    fun updateDestinationPoint(){
        if(!this::mapboxMap.isInitialized) return
        cameraAutoZoomToDesrination()
        mapboxMap.getStyle {
            //enableLocationComponent(it, true)

            // Destination source
            it.getSourceAs<GeoJsonSource>("destinationSource")?.setGeoJson(
                FeatureCollection.fromFeatures(
                    ArrayList<Feature>(
                        destinationFeature
                    )
                )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val selectedCarmenFeature = PlaceAutocomplete.getPlace(data)
            val long = selectedCarmenFeature.center()?.longitude()!!
            val lat = selectedCarmenFeature.center()?.latitude()!!
            val title = selectedCarmenFeature.placeName()

            destinationFeature.clear()
            val eachPoint = Point.fromLngLat(long,lat)
            val eachFeature = Feature.fromGeometry(eachPoint)
            eachFeature.addNumberProperty("long",long)
            eachFeature.addNumberProperty("lat", lat)
            eachFeature.addStringProperty("title", title)
            destinationFeature.add(eachFeature)
            addDestinationToMap()
        }
    }


    /*
        Create listener for all float button
     */
    private fun setZooming(){
        floatButtonZoomIn.setOnClickListener {
            updateCamera(1.0)
        }
        floatButtonZoomIn.setOnLongClickListener {
            updateCamera(5.0)
            false
        }
        floatButtonZoomOut.setOnClickListener {
            updateCamera(-1.0)
        }
        floatButtonZoomOut.setOnLongClickListener {
            updateCamera(-5.0)
            false
        }
    }

    /*
            Update the camera base on new zoom level.
     */
    private fun updateCamera(zoomLevel: Double){
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
    private fun cameraAutoZoomToSuburb(){

        if (this::mapboxMap.isInitialized && feature.size != 0 && locationList.size != 0) {
            suburbPoint = locationList[0]
            val position = CameraPosition.Builder()
                .target(LatLng(suburbPoint.latitude(), suburbPoint.longitude()))
                .zoom(9.0)
                .build()
            mapboxMap.cameraPosition = position
        }
    }

    /*-- Camera auto zoom to the suburb area --*/
    private fun cameraAutoZoomToDesrination(){
        if (this::mapboxMap.isInitialized && destinationFeature.size != 0) {
            val lat = destinationFeature[0].getNumberProperty("lat")
            val long = destinationFeature[0].getNumberProperty("long")
            val position = CameraPosition.Builder()
                .target(LatLng(lat as Double, long as Double))
                .zoom(10.0)
                .build()
            mapboxMap.cameraPosition = position
        }
    }

    fun simpleFindRoute(point: Point){
        dialog.show()
        mapboxMap.getStyle { it -> enableLocationComponent(it,true) }
        val origin = mapboxMap.locationComponent.lastKnownLocation?.let {
            Point.fromLngLat(it.longitude, it.latitude)
        } ?: return

        routeRequestID = mapboxNavigation.requestRoutes(
            RouteOptions.builder()
                .applyDefaultNavigationOptions()
                .profile(DirectionsCriteria.PROFILE_CYCLING)
                .applyLanguageAndVoiceUnitOptions(mainActivity)
                .coordinatesList(listOf(origin, point))
                .build(),
            object : RouterCallback {
                override fun onRoutesReady(
                    routes: List<DirectionsRoute>,
                    routerOrigin: RouterOrigin
                ) {
                    simpleRoute =  routes.first()
                    addSimpleRouteToMap()
                    dialog.dismiss()
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

    /*-- Make a toast when data is updating --*/
    private fun checkDataEmpty(){
        if (feature.size == 0 && locationList.size == 0) {
            toast.setText(getString(R.string.no_data))
            toast.show()
        }
    }


    /*-- get the location permission --*/
    @SuppressWarnings("MissingPermission")
    fun enableLocationComponent(loadMapStyle: Style,boolean: Boolean) {
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
                if(boolean && locationComponent.lastKnownLocation == null ){
                    toast.setText(getString(R.string.ask_allow_location_service))
                    toast.show()
                    dialog.dismiss()
                }
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
        dialog.dismiss()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(this::mapView.isInitialized)
            mapView.onSaveInstanceState(outState)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog.dismiss()
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
                enableLocationComponent(it,false)
            }
        } else {
            Toast.makeText(context, getString(R.string.location_granted), Toast.LENGTH_LONG)
                .show()
        }
    }


    /* --
            UI part - Hide or Show the crash relative view.
    -- */
    private fun changeToNav() {
        removeAlertBubble()
        mapboxMap.getStyle {
            val bcLayer = it.getLayer("basic_circle_layer")
            val scLayer = it.getLayer("shadow_circle_layer")
            val siLayer = it.getLayer("icon_layer")
            if (bcLayer != null) {
                if (View.VISIBLE == mapView.visibility) {
                    setToolbarReturn(toolbar)
                    mainActivity.isBottomNavigationVisible(false)
                    mapView2.visibility = View.VISIBLE
                    mapView.visibility = View.INVISIBLE
                    binding.recenter.visibility = View.VISIBLE
                    binding.floatButtonStop.visibility = View.VISIBLE
                    initNav()
                    binding.floatButtonNav.setImageResource(R.drawable.crash_36)
                    binding.searchMap1.visibility = View.INVISIBLE
                    binding.filterCards.root.visibility = View.INVISIBLE
                    bcLayer.setProperties(visibility(Property.NONE))
                    scLayer?.setProperties(visibility(Property.NONE))
                    siLayer?.setProperties(visibility(Property.NONE))

                }
                else {
                    val dialog2 = MaterialDialog(mainActivity)
                    dialog2.show {
                        message(text = getString(R.string.ask_leave))
                        positiveButton(R.string.yes) {
                            setToolbarBasic(toolbar)
                            mainActivity.isBottomNavigationVisible(true)
                            mapView2.visibility = View.INVISIBLE
                            binding.floatButtonStop.visibility = View.INVISIBLE
                            mapView.visibility = View.VISIBLE
                            recenter.visibility = View.INVISIBLE
                            binding.floatButtonNav.setImageResource(R.drawable.baseline_assistant_direction_black_36)
                            binding.searchMap1.visibility = View.VISIBLE
                            binding.filterCards.root.visibility = View.VISIBLE
                            bcLayer.setProperties(visibility(Property.VISIBLE))
                            scLayer?.setProperties(visibility(Property.VISIBLE))
                            siLayer?.setProperties(visibility(Property.VISIBLE))
                            clearRouteAndStopNavigation()
                        }
                        negativeButton(R.string.no) {
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
        navigationCamera.registerNavigationCameraStateChangeObserver { navigationCameraState ->
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
            Style2.TRAFFIC_DAY
        ) {
            // add long click listener that search for a route to the clicked destination
            mapView2.gestures.addOnMapLongClickListener { point ->
                findRoute(point)
                true
            }
        }

        binding.floatButtonStop.setOnClickListener {
            clearRouteAndStopNavigation()
        }

        binding.recenter.setOnClickListener {
            navigationCamera.requestNavigationCameraToFollowing()
        }

        mapboxNavigation.startTripSession()
    }




    /*
        Update the navigation camera
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
        Find route of the navigation
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
        Clear the route of the navigation function
     */
    private fun clearRouteAndStopNavigation() {
        // clear
        mapboxNavigation.setRoutes(listOf())
        binding.maneuverView.visibility = View.INVISIBLE
        tripProgressCard.visibility = View.INVISIBLE
        changeFloatButtonHeight()

    }


    /*
        Change the height(margin bottom) of the float buttons
     */
    private fun changeFloatButtonHeight() {
        // change the float button height
        val fBHeight = floatButton.layoutParams as CoordinatorLayout.LayoutParams
        if (tripProgressCard.visibility == View.VISIBLE)
            fBHeight.bottomMargin = tripProgressCard.height + 20
        else
            fBHeight.bottomMargin = mainActivity.bottomNavHeight() + 20
        floatButton.layoutParams = fBHeight

        // change the float button Nav height
        val fBHeightNav = floatButtonNav.layoutParams as CoordinatorLayout.LayoutParams
        fBHeightNav.bottomMargin = fBHeight.bottomMargin
        floatButtonNav.layoutParams = fBHeightNav

        // change the float button Stop height
        val fBHeightStop = floatButtonStop.layoutParams as CoordinatorLayout.LayoutParams
        fBHeightStop.bottomMargin = fBHeight.bottomMargin * 2
        floatButtonStop.layoutParams = fBHeightStop

        val fBHeightZoomOut = floatButtonZoomOut.layoutParams as CoordinatorLayout.LayoutParams
        fBHeightZoomOut.bottomMargin = fBHeight.bottomMargin * 3
        floatButtonZoomOut.layoutParams = fBHeightZoomOut

        val fBHeightZoomIn = floatButtonZoomIn.layoutParams as CoordinatorLayout.LayoutParams
        fBHeightZoomIn.bottomMargin = fBHeight.bottomMargin * 4
        floatButtonZoomIn.layoutParams = fBHeightZoomIn

        val fBHeightDestination = floatButtonDestination.layoutParams as CoordinatorLayout.LayoutParams
        fBHeightDestination.bottomMargin = fBHeight.bottomMargin * 2
        floatButtonDestination.layoutParams = fBHeightDestination

        spinnerText.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.baseline_search_black_36,0,0,0
        )
    }

    /*
        Call the retrofit client to get all data in the map
     */
    private fun callAllClient(boolean: Boolean){
        if (!mainActivity.isNetworkEnabled()) {
            toast.setText(getString(R.string.ask_allow_network_service))
            toast.show()
            dialog.dismiss()
            return
        }
        val mapIsInitialize = this::mapboxMap.isInitialized
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

                    /*
                        Get specific data
                     */
                    val result = response.body()!!.suburbAllAccidents
                    val accidents = result.accidents
                    val paths = result.paths
                    val alerts = result.alerts

                    /*
                        Add alert data to alert
                     */
                    if (alerts.isNotEmpty()) {
                        for (each in alerts) {
                            val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
                            val eachFeature = Feature.fromGeometry(eachPoint)
                            eachFeature.addStringProperty("type", each.type)
                            eachFeature.addNumberProperty("long",each.location.long)
                            eachFeature.addNumberProperty("lat",each.location.lat)
                            alertsFeature.add(eachFeature)
                        }
                    }

                    /*
                        Add accident data to feature
                     */
                    if (accidents.isNotEmpty()) {
                        for (each in accidents) {
                            val eachPoint = Point.fromLngLat(each.location.long, each.location.lat)
                            locationList.add(eachPoint)
                            val eachFeature = Feature.fromGeometry(eachPoint)
                            eachFeature.addStringProperty("type",each.type)
                            eachFeature.addStringProperty("road",each.road_name)
                            eachFeature.addNumberProperty("year",each.year.toInt())
                            eachFeature.addStringProperty("severity",each.severity)
                            eachFeature.addNumberProperty("long",each.location.long)
                            eachFeature.addNumberProperty("lat",each.location.lat)
                            feature.add(eachFeature)
                        }
                    }

                    /*
                        Add paths data to pathList
                     */
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
                    if(boolean && mapIsInitialize) onMapUpdate()
                    else mapView.getMapAsync(fragmentNow)
                }
                else {
                    dialog.dismiss()
                    Timber.d("Response failed")
                }
            }

            override fun onFailure(call: Call<SuburbAllResponse?>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(activity, getString(R.string.map_null), Toast.LENGTH_SHORT).show()
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

            filterCardBinding.filterPaths.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_path, 0, 0, 0
            )

            filterCardBinding.filterTraffic.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_traffic,0,0,0
            )

            filterCardBinding.filterAccidents.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.filter_accident,0,0,0
            )

            filterCardBinding.filterAlerts.setCompoundDrawablesWithIntrinsicBounds(
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
    private fun filterTrafficListener(){
        val filterTraffic =  filterCardBinding.filterTraffic
        filterTraffic.setOnClickListener {
            if(!trafficPlugin.isVisible){
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
    private fun filterPathsListener(){
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
    private fun filterAccidentListener(){
        val filterAccidents = filterCardBinding.filterAccidents
        var filterStatus = true
        filterAccidents.setOnClickListener {
            if(filterStatus){
                mapboxMap.getStyle {
                    val layer1 = it.getLayer("basic_circle_layer")
                    val layer2 = it.getLayer("shadow_circle_layer")
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
                    val layer1 = it.getLayer("basic_circle_layer")
                    val layer2 = it.getLayer("shadow_circle_layer")
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
    private fun filterAlertsListener(){
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


    /*
        Handle click item in spinner
     */
    override fun onClick(position: Int, searchListItem: SearchListItem) {
        searchableDialog.dismiss()
        removeAlertBubble()
        setDialog()
        spinnerTimes++ // calculate the times to test
        if (spinnerTimes >= 1) {
            suburb = searchListItem.title
            callAllClient(true)
            spinnerIndex = searchListItem.id
            spinnerText.text = suburb
        }
    }

}






