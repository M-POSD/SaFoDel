package com.example.safodel.fragment.menuB

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.safodel.R
import com.example.safodel.databinding.FragmentMapBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.LGAList
import com.example.safodel.ui.main.MainActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
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

//mapbox dataset: kxuu0025.cksr78zv20npw27n2ctmlwar3-02exu

private val locationList: ArrayList<Point> = ArrayList()
private var feature: ArrayList<Feature> = ArrayList()
private var lga: String = "BAYSIDE"
private var spinnerTimes = 0


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback,PermissionsListener, AdapterView.OnItemSelectedListener {

    // Map
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mainActivity : MainActivity
    private lateinit var mapView: MapView

    // Navigation
    private lateinit var mapboxNavigation: MapboxNavigation

    // Permission
    private lateinit var permissionsManager: PermissionsManager

    // Basic value
    private val LGAlist = LGAList.init()
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

        // spinner init
        val spinner: Spinner = binding.spinner
        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter<String>(mainActivity,android.R.layout.simple_spinner_item,LGAlist)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this


        // basic location and position
        val mThread = fetchdata()
        mThread.start()

        /* --- Bottom navigation hide, when touch the map. --- */
        binding.mapView.setOnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
            }
            false
        }


        binding.updateMap.setOnClickListener {
            mapView.getMapAsync(this)
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setMaxZoomPreference(20.0)
        mapboxMap.setMinZoomPreference(5.0)
        this.mapboxMap = mapboxMap
        this.mapboxMap.setStyle(Style.LIGHT){
            val position =  CameraPosition.Builder().target(defaultLatLng).zoom(10.0).build()
            mapboxMap.cameraPosition = position
            enableLocationComponent(it)

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
            //it.addSource(GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(feature)))
            it.addSource(GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(ArrayList<Feature>(
                feature))))

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

            //Toast.makeText(context, "zoom_map_in_and_out_circle_to_icon_transition", Toast.LENGTH_SHORT).show()

            mapboxMap.animateCamera(
                CameraUpdateFactory
                    .newCameraPosition(
                        CameraPosition.Builder()
                            .zoom(10.5)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerTimes++
        Log.d("Hello spinner", parent?.getItemAtPosition(position).toString())
        if(spinnerTimes >1){
            Log.d("Hello Spinner", spinnerTimes.toString())
            lga = parent?.getItemAtPosition(position).toString()
            val mThread = fetchdata()
            mThread.start()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
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
            val url = URL("https://safodel-api.herokuapp.com/location/" + lga + "/")
            val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream: InputStream = httpURLConnection.inputStream
            val bufferReader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
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









