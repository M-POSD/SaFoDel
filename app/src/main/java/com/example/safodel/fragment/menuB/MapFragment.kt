package com.example.safodel.fragment.menuB

import android.annotation.SuppressLint
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
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
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
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius


private val locationList: ArrayList<Point> = ArrayList()
private var feature: ArrayList<Feature> = ArrayList()
private var lga: String = "MELBOURNE"
private var spinnerTimes = 0
private lateinit var mThread:Thread


class MapFragment: BasicFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback,PermissionsListener, AdapterView.OnItemSelectedListener {

    // class toast value
    private lateinit var toast: Toast

    // Map
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mainActivity : MainActivity
    private lateinit var mapView: MapView
    private lateinit var LGAPoint:Point

    // Navigation
    private lateinit var mapboxNavigation: MapboxNavigation

    // Permission
    private lateinit var permissionsManager: PermissionsManager

    // Basic value
    private val LGAlist = LGAList.init()
    
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
        mainActivity = activity as MainActivity
        val toolbar = binding.toolbar.root
        mapView = binding.mapView
        setToolbarBasic(toolbar)


        // init the Navigation
        val mapboxNavigationOptions = MapboxNavigation
            .defaultNavigationOptionsBuilder(mainActivity, getString(R.string.mapbox_access_token))
            .build()
        //mapboxNavigation = MapboxNavigation(mapboxNavigationOptions)


        // request permission of user location
        permissionsManager = PermissionsManager(this)
        permissionsManager.requestLocationPermissions(activity)

        // spinner init
        val spinner: Spinner = binding.spinner
        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter<String>(mainActivity,android.R.layout.simple_spinner_item,LGAlist)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

        // change the float buuton height
        val FBHeight = binding.floatButton.layoutParams as CoordinatorLayout.LayoutParams
        FBHeight.bottomMargin = mainActivity.bottomNavHeight() + 20
        Log.d("FBHeight 1 ", FBHeight.bottomMargin .toString())
        binding.floatButton.layoutParams = FBHeight

        // change the float buuton Nav height
        val FBHeightNav = binding.floatButtonNav.layoutParams as CoordinatorLayout.LayoutParams
        FBHeightNav.bottomMargin = FBHeight.bottomMargin * 2
        Log.d("FBHeight 2 ", FBHeightNav.bottomMargin .toString())
        binding.floatButtonNav.layoutParams = FBHeightNav


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
        }


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
                            R.drawable.mapbox_marker_icon_default
                        )
                    }
                )!!)

            /*-- Add source --*/
            it.addSource(GeoJsonSource("source", FeatureCollection.fromFeatures(ArrayList<Feature>(
                feature))))
            val baseicCircle:CircleLayer = CircleLayer("basic_circle_cayer","source").withProperties(
                circleColor(Color.parseColor("#8AD0AB")),
                visibility(Property.VISIBLE),
                circleRadius(
                    interpolate(
                        linear(), zoom(),
                        stop(11f, 3.4f),
                        stop(12f, 14f)
                )
            ))

            /*-- Add layer --*/
            it.addLayer(baseicCircle)

            /*-- Add circle layer --*/
            val shadowTransitionCircleLayer = CircleLayer("shadow_circle_cayer", "source")
                .withProperties(
                    circleColor(parseColor("#858585")),
                    circleRadius(14f),
                    visibility(Property.VISIBLE),
                    circleOpacity(
                        interpolate(
                            linear(), zoom(),
                            stop(11f - .5, 0),
                            stop(11f, .5f)
                        )
                    )
                )
            it.addLayerBelow(shadowTransitionCircleLayer, "basic_circle_cayer")

            /*-- Add symbol layer --*/
            val symbolIconLayer = SymbolLayer("icon_layer", "source")
            symbolIconLayer.withProperties(
                visibility(Property.VISIBLE),
                iconImage("icon_image"),
                iconSize(1.5f),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)
            )
            symbolIconLayer.minZoom = 12f
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

    /*-- Spinner listener --*/
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerTimes++ // calculate the times to test
        //Log.d("Hello spinner", parent?.getItemAtPosition(position).toString())
        if(spinnerTimes >= 1){
            //Log.d("Hello Spinner", spinnerTimes.toString())
            lga = parent?.getItemAtPosition(position).toString()
            mThread = fetchdata()
            mThread.start()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


    /* -- Hide or Show the crash relative view. -- */
    fun changeToNav(){
        mapboxMap.getStyle {
            val bcLayer =  it.getLayer("basic_circle_cayer")
            val scLayer = it.getLayer("shadow_circle_cayer")
            val siLayer = it.getLayer("icon_layer")
            if(bcLayer != null && scLayer != null && siLayer != null){
                if(Property.VISIBLE.equals(bcLayer.visibility.value)){
                    mapboxMap.setStyle(Style.TRAFFIC_DAY)
                    binding.floatButtonNav.setImageResource(R.drawable.crash_36)
                    binding.textTop.visibility = View.INVISIBLE
                    binding.spinner.visibility = View.INVISIBLE
                    binding.updateMap.visibility = View.INVISIBLE
                    bcLayer.setProperties(visibility(Property.NONE))
                    scLayer.setProperties(visibility(Property.NONE))
                    siLayer.setProperties(visibility(Property.NONE))
                }
            }else {
                mapView.getMapAsync(this)
                binding.floatButtonNav.setImageResource(R.drawable.baseline_assistant_direction_black_36)
                binding.textTop.visibility = View.VISIBLE
                binding.spinner.visibility = View.VISIBLE
                binding.updateMap.visibility = View.VISIBLE
                bcLayer?.setProperties(visibility(Property.VISIBLE))
                scLayer?.setProperties(visibility(Property.VISIBLE))
                siLayer?.setProperties(visibility(Property.VISIBLE))
            }
        }
    }

//    fun addSearchSheet(){
//        val inflater = LayoutInflater.from(context)
//        val searchSheet = inflater.inflate(R.layout.search_address,null,false)
//        binding.coordinator.addView(searchSheet)

        //binding.searchSheet.initializeSearch()
 //   }
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
            val url = URL("https://safodel-api.herokuapp.com/accidents?location=" + lga)
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







/* --- Bottom navigation hide, when touch the map. --- */
//        binding.mapView.setOnTouchListener { _, event ->
//            when(event.action){
//                MotionEvent.ACTION_DOWN -> mainActivity.isBottomNavigationVisible(false)
//                MotionEvent.ACTION_UP -> mainActivity.isBottomNavigationVisible(true)
//            }
//            false
//        }





