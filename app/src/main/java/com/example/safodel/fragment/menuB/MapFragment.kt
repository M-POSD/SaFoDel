package com.example.safodel.fragment.menuB

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.parseColor
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainer
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonParser
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
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
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property.*
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.style.sources.VectorSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.navigation.base.internal.extensions.applyDefaultParams
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.core.directions.session.RoutesRequestCallback
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

import com.mapbox.mapboxsdk.style.expressions.Expression.stop

import com.mapbox.mapboxsdk.style.expressions.Expression.zoom

//mapbox dataset: kxuu0025.cksr78zv20npw27n2ctmlwar3-02exu

private val locationList: ArrayList<Point> = ArrayList()
private val feature: ArrayList<Feature> = ArrayList()


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback,PermissionsListener {

    // Map
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mainActivity : MainActivity
    private lateinit var mapView: MapView

    // Navigation
    private lateinit var mapboxNavigation: MapboxNavigation

    // Permission
    private lateinit var permissionsManager: PermissionsManager

    // Basic value
    private val defaultLatLng = LatLng(-37.876823, 145.045837)
    private val BASE_CIRCLE_INITIAL_RADIUS = 3.4f
    private val RADIUS_WHEN_CIRCLES_MATCH_ICON_RADIUS = 14f
    private val ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION = 11f
    private val ZOOM_LEVEL_FOR_SWITCH_FROM_CIRCLE_TO_ICON = 12f
    private val FINAL_OPACITY_OF_SHADING_CIRCLE = .5f
    private val BASE_CIRCLE_COLOR = "#3BC802"
    private val SHADING_CIRCLE_COLOR = "#858585"
    private val SOURCE_ID = "SOURCE_ID"
    private val ICON_LAYER_ID = "ICON_LAYER_ID"
    private val BASE_CIRCLE_LAYER_ID = "BASE_CIRCLE_LAYER_ID"
    private val SHADOW_CIRCLE_LAYER_ID = "SHADOW_CIRCLE_LAYER_ID"
    private val ICON_IMAGE_ID = "ICON_ID"

    // thread
    val mainHandler: Handler = Handler()


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
        val mThread = fetchdata()
        mThread.start()

        binding.updateMap.setOnClickListener {
            Log.d("Hello user", feature.size.toString())
            mapView.getMapAsync(this)
        }


        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

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

            //withSource(new GeoJsonSource(SOURCE_ID,
            //FeatureCollection.fromFeatures(initFeatureArray())))
            // Add the destination marker image
//           it.addSource(
//                VectorSource("mel","mapbox://kxuu0025.cksr78zv20npw27n2ctmlwar3-02exu")
//            )
//            var melLayer = CircleLayer("crashs","mel")
//            melLayer.sourceLayer = "mel-cusco"
//            melLayer.setProperties(
//                visibility(VISIBLE),
//                circleRadius(8f),
//                circleColor(Color.argb(255,55,148,179))
//            )
//            it.addLayer(melLayer)

            it.addImage(
                ICON_IMAGE_ID,
                BitmapUtils.getBitmapFromDrawable(
                    context?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1,
                            R.drawable.mapbox_marker_icon_default
                        )
                    }
                )!!)

            Log.d("In the main Thread Feature is ", feature.size.toString())
            it.addSource(GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(feature)))

            val baseicCircle:CircleLayer = CircleLayer(BASE_CIRCLE_LAYER_ID,SOURCE_ID).withProperties(
                circleColor(Color.parseColor(BASE_CIRCLE_COLOR)),
                circleRadius(
                    interpolate(
                        linear(), zoom(),
                        stop(ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION, BASE_CIRCLE_INITIAL_RADIUS),
                        stop(ZOOM_LEVEL_FOR_SWITCH_FROM_CIRCLE_TO_ICON, RADIUS_WHEN_CIRCLES_MATCH_ICON_RADIUS)
                )
            ))
            it.addLayer(baseicCircle)

            val shadowTransitionCircleLayer = CircleLayer(SHADOW_CIRCLE_LAYER_ID, SOURCE_ID)
                .withProperties(
                    circleColor(parseColor(SHADING_CIRCLE_COLOR)),
                    circleRadius(RADIUS_WHEN_CIRCLES_MATCH_ICON_RADIUS),
                    circleOpacity(
                        interpolate(
                            linear(), zoom(),
                            stop(ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION - .5, 0),
                            stop(
                                ZOOM_LEVEL_FOR_START_OF_BASE_CIRCLE_EXPANSION,
                                FINAL_OPACITY_OF_SHADING_CIRCLE
                            )
                        )
                    )
                )
            it.addLayerBelow(shadowTransitionCircleLayer, BASE_CIRCLE_LAYER_ID)

            val symbolIconLayer = SymbolLayer(ICON_LAYER_ID, SOURCE_ID)
            symbolIconLayer.withProperties(
                iconImage(ICON_IMAGE_ID),
                iconSize(1.5f),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)
            )

            symbolIconLayer.minZoom = ZOOM_LEVEL_FOR_SWITCH_FROM_CIRCLE_TO_ICON
            it.addLayer(symbolIconLayer)

            Toast.makeText(
                context,
                "zoom_map_in_and_out_circle_to_icon_transition", Toast.LENGTH_SHORT
            ).show()

            mapboxMap.animateCamera(
                CameraUpdateFactory
                    .newCameraPosition(
                        CameraPosition.Builder()
                            .zoom(12.5)
                            .build()
                    ), 3000
            )


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


}

class fetchdata: Thread() {

    var data: String = ""

    override fun run() {
        try {
            val url = URL("https://safodel-api.herokuapp.com/location/MELBOURNE/")
            val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream: InputStream = httpURLConnection.inputStream
            val bufferReader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String
            data = bufferReader.readLine()


            if(data.isNotEmpty()){
                var jsonObject = JSONObject(data)
                var points: JSONArray = jsonObject.getJSONArray("results")

                Log.d("hello pure",points.length().toString())
                locationList.clear()
                for(point in 0..points.length()-1){
                    var location: JSONObject = points.getJSONObject(point)
                    var eachPoint = Point.fromLngLat(location.get("long").
                    toString().toDouble(),
                        location.get("lat").toString().toDouble())
                    //var eachLatlng = LatLng(eachPoint.latitude(),eachPoint.longitude())
                    //Log.d("Hello size",location.get("long").toString())
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
                    feature.add(Feature.fromGeometry(locationList[each]))
                }
                Log.d("Hello PPPPPP", feature.size.toString())


            }
        }
    }
}





/* --- Bottom navigation hide, when touch the map. --- */
//        binding.mapView.setOnTouchListener { _, event ->
//            when(event.action){
//                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
//                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
//            }
//            false
//        }

//            it.addSource(
//                VectorSource("mel","mapbox://kxuu0025.cksr78zv20npw27n2ctmlwar3-02exu")
//            )
//            var melLayer = CircleLayer("crashs","mel")
//            melLayer.sourceLayer = "mel-cusco"
//            melLayer.setProperties(
//                visibility(VISIBLE),
//                circleRadius(8f),
//                circleColor(Color.argb(255,55,148,179))
//            )
//            it.addLayer(melLayer)