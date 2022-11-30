package com.fime.roomier

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import org.json.JSONException


class Mapa : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private val DEFAULT_ZOOM = 15f
    val TAG:String = "main"

    var address:String = ""
    lateinit var addressTxt: TextView
    lateinit var txtCosto: TextView
    lateinit var btnUpdate: Button

    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationPermissionsGranted = false

    private lateinit var currentLocation: Location
    private var userLat:Double = 0.0
    private var userLong:Double = 0.0

    val items = arrayOf("restaurant","store", "gym")
    var markers = arrayListOf<MarkerOptions>()
    lateinit var autoCompleteText:AutoCompleteTextView
    lateinit var adapterItems: ArrayAdapter<String>
    lateinit var puntero: MarkerOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        addressTxt= findViewById(R.id.txtAddress)
        autoCompleteText = findViewById(R.id.autoCompleteText)
        txtCosto = findViewById(R.id.txtCosto)
        btnUpdate = findViewById(R.id.btn_update)

        adapterItems = ArrayAdapter(this, R.layout.list_options, items)
        autoCompleteText.setAdapter(adapterItems)




        btnUpdate.setOnClickListener{
            var ubicacion:LatLng
            ubicacion = puntero.position
            userLat = ubicacion.latitude
            userLong = ubicacion.longitude

            markers.removeAll(markers)
            updateMap()
            getAddressName()
            moveCamera(LatLng(userLat, userLong), 16f, "My location")
            //drawCircle(LatLng(userLat, userLong), 400.0)

        }
        // When click the hint selection, will trigger close keyboard function
        autoCompleteText.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
                var item:String =  parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Item: ${item}", Toast.LENGTH_SHORT).show()
                getInfo(item)
            }







        getLocationPermission()


    }

    private fun initMap() {
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)





    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)

        mMap.setOnMapClickListener(OnMapClickListener { point ->
            verifyMarker()
            val marker = MarkerOptions().position(LatLng(point.latitude, point.longitude))
                .title("New")
            puntero = marker
            markers.add(marker)
            updateMap()
            //mMap.addMarker(marker)



            println(point.latitude.toString() + "---" + point.longitude)
        })


        //createMarker(25.7299374,-100.2096866)

        if (mLocationPermissionsGranted) {
            getDeviceLocation()

            //Esta linea solo se pone para que nops deje poner el icono azuliño

            //Esta linea solo se pone para que nops deje poner el icono azuliño
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }

            //Añade icono azul de la current location del dispositivo
            //Añade icono azul de la current location del dispositivo
            mMap.isMyLocationEnabled = true




        }


    }

    fun createMarker(lati:Double, longi:Double, name: String, type:Int) {
        //val coordinates = LatLng(25.7299374,-100.2096866)
        val coordinates = LatLng(lati,longi)
        val marker:MarkerOptions
        if(type == 1){
            marker = MarkerOptions().position(coordinates).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_icon))
            markers.add(marker)
        }else if(type == 2){
            marker = MarkerOptions().position(coordinates).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.store_icon))
            markers.add(marker)
        }else if(type == 3){
            marker = MarkerOptions().position(coordinates).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.gym_icon))
            markers.add(marker)
        }else{
            marker = MarkerOptions().position(coordinates).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.user_blue_icon))
            markers.add(marker)
        }



        //val pointer = mMap.addMarker(marker)
        //pointer?.tag = 0

    }

    fun getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                var location: Task<*> = mFusedLocationProviderClient.getLastLocation()
                location.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d(TAG, "getUserLocation: Found Location")
                        currentLocation = task.getResult() as Location
                        val arr1 = floatArrayOf(.1f)



                        val home = Location("")
                        home.latitude = 25.7299374
                        home.longitude = -100.2096866

                        userLat = currentLocation.latitude
                        userLong = currentLocation.longitude


                        var distanceInMeters = currentLocation.distanceTo(home)
                        Log.d(TAG, "Distance in meters ->  ${distanceInMeters}")

                        //addLine(currentLocation,home)

                        //Location.distanceBetween(userLat, userLong, 25.7299374, -100.2096866, arr1)
                        //Log.d(TAG, "Distance -> ${arr1[0]} ")




                        moveCamera(LatLng(userLat, userLong), 16f, "My location")
                        drawCircle(LatLng(userLat, userLong), 400.0)




                        getAddressName()

                        //cargaTabla()

                    }else{
                        Log.d(TAG, "onComplete: current location is null")
                        Toast.makeText(this@Mapa, "Unable to get current location", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }catch (e: SecurityException){
            Log.d(TAG, "getUserLocation: Security Exception ${e.message}")
        }
    }

    fun cargaTabla(){

        var queue = Volley.newRequestQueue(this)
        var lati:Double
        var longi:Double
        var precios:Double = 0.0
        var cont:Int = 0
        var promedio:Double = 0.0
        var distance:Double = 99999.0
        var distanceInMeters:Float

        var address:String = ""
        var actualName:String = ""



        Log.d(TAG, "UserLat -> ${userLat} ")
        Log.d(TAG, "UserLat -> ${userLong} ")
        var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=price_level&location=${userLat}%2C${userLong}&radius=2000&type=restaurant&key=AIzaSyDV6aFItX960hrbAaI229-8iDa3xTZ-RXU"
        //Log.d(TAG, "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=price_level&location=${userLat}%2C${userLong}&radius=2500&type=restaurant&key=AIzaSyDV6aFItX960hrbAaI229-8iDa3xTZ-RXU")
        var myJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                    response ->  try{
                var myJsonArray = response.getJSONArray("results")
                for(i in 0 until myJsonArray.length()){
                    var myJSONObject = myJsonArray.getJSONObject(i)
                    /*
                    val registro = LayoutInflater.from(this).inflate(R.layout.table_row_np,null,false)
                    val colName = registro.findViewById<View>(R.id.columnaNombre) as TextView
                    val colPrice = registro.findViewById<View>(R.id.columnaEmail) as TextView
                    val colLatitude = registro.findViewById<View>(R.id.colEditar)
                    val colBorrar = registro.findViewById<View>(R.id.colBorrar)
                    */


                    var name = myJSONObject.getString("name")
                    Log.d(TAG, "Nombre:  ${name}" )

                    try{
                        var price = myJSONObject.getString("price_level")
                        Log.d(TAG, "Price rating:  ${price}" )
                        precios += price.toDouble()
                    }catch (e: Exception){
                        Log.d(TAG, "ERROR -> :  ${ e.message}" )
                        cont++
                    }

                    //colPrice.text=myJSONObject.getString("price_level")



                    var geometry = myJSONObject.getJSONObject("geometry")
                    var location = geometry.getJSONObject("location")

                    lati = location.getString("lat").toDouble()
                    longi = location.getString("lng").toDouble()
                    Log.d(TAG, "Latitude: ${location.getString("lat")}")
                    Log.d(TAG, "Latitude: ${location.getString("lng")}")



                    createMarker(lati, longi, name,1)

                    val loc2 = Location("")
                    loc2.latitude = lati
                    loc2.longitude = longi

                    distanceInMeters = currentLocation.distanceTo(loc2)
                    if(distanceInMeters < distance ){
                        distance = distanceInMeters.toDouble()
                        actualName = name
                        address = myJSONObject.getString("vicinity")


                    }

                    addLine(currentLocation, loc2)





                    //colEditar.id=myJSONObject.getString("id").toInt()
                    //colBorrar.id=myJSONObject.getString("id").toInt()



                }

                promedio = precios / (myJsonArray.length() - cont)

                if(promedio <= 1.99){
                    txtCosto.setText("Barato")
                    txtCosto.setTextColor(Color.parseColor("#66bb6a"))
                }else if(promedio >= 2 && promedio < 3){
                    txtCosto.setText("Moderado")
                    txtCosto.setTextColor(Color.parseColor("#ffeb3b"))
                }else if(promedio >= 3){
                    txtCosto.setText("Caro")
                    txtCosto.setTextColor(Color.parseColor("#f44336"))
                }
                Log.d(TAG, "EL PROMEDIO DE COSTO DE LA ZONA ES -> ${promedio} ")
                Log.d(TAG, "cargaTabla: MENOR DISTANCIA -> $distance")
                Log.d(TAG, "cargaTabla: PLACE NAME -> $actualName")
                Log.d(TAG, "cargaTabla: Address -> $address")

            }catch (e: JSONException){
                e.printStackTrace()
            }
            }, {
                    error ->  Toast.makeText(this,"Error $error", Toast.LENGTH_LONG).show()
            })
        queue.add(myJsonObjectRequest)
    }

    fun moveCamera(lati: LatLng, zoom:Float, title:String ){
        Log.d(TAG, "moveCamera: moving camero to -> lat: " + lati.latitude + ", longitude: " + lati.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lati, zoom))

    }



    //PERMISOS
    fun getLocationPermission(){
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ContextCompat.checkSelfPermission(this.applicationContext, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.applicationContext, COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true
                initMap()
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d(TAG, "onMarkerClick: Clicking")
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(this, "${marker.title} has been clicked $newClickCount times.", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onMarkerClick: ${marker.title} has been clicked $newClickCount times.")
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    private fun drawCircle(point: LatLng, radio:Double) {

        // Instantiating CircleOptions to draw a circle around the marker
        val circleOptions = CircleOptions()

        // Specifying the center of the circle
        circleOptions.center(point)

        // Radius of the circle
        circleOptions.radius(radio)

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK)

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000)

        // Border width of the circle
        circleOptions.strokeWidth(2f)

        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions)
    }

    private fun addLine(loc1: Location, loc2: Location){
        // Instantiates a new Polyline object and adds points to define a rectangle
        val polylineOptions = PolylineOptions()
            .add(LatLng(loc1.latitude, loc1.longitude))
            .add(LatLng(loc2.latitude, loc2.longitude))




        // Get back the mutable Polyline
        val polyline = mMap.addPolyline(polylineOptions)
    }

    private fun getAddressName(){
        var queue = Volley.newRequestQueue(this)
        var lati:Double
        var longi:Double
        var precios:Double = 0.0
        var cont:Int = 0
        var promedio:Double = 0.0
        var distance:Double = 99999.0
        var distanceInMeters:Float

        var address:String = ""
        var actualName:String = ""

        lateinit var actualLocation: Location



        Log.d(TAG, "UserLat -> ${userLat} ")
        Log.d(TAG, "UserLat -> ${userLong} ")
        var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=price_level&location=${userLat}%2C${userLong}&radius=400&type=any&key=AIzaSyDV6aFItX960hrbAaI229-8iDa3xTZ-RXU"
        //Log.d(TAG, "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=price_level&location=${userLat}%2C${userLong}&radius=2500&type=restaurant&key=AIzaSyDV6aFItX960hrbAaI229-8iDa3xTZ-RXU")
        var myJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                    response ->  try{
                var myJsonArray = response.getJSONArray("results")
                for(i in 0 until myJsonArray.length()){
                    var myJSONObject = myJsonArray.getJSONObject(i)
                    /*
                    val registro = LayoutInflater.from(this).inflate(R.layout.table_row_np,null,false)
                    val colName = registro.findViewById<View>(R.id.columnaNombre) as TextView
                    val colPrice = registro.findViewById<View>(R.id.columnaEmail) as TextView
                    val colLatitude = registro.findViewById<View>(R.id.colEditar)
                    val colBorrar = registro.findViewById<View>(R.id.colBorrar)
                    */


                    var name = myJSONObject.getString("name")
                    Log.d(TAG, "Nombre:  ${name}" )



                    //colPrice.text=myJSONObject.getString("price_level")


                    var geometry = myJSONObject.getJSONObject("geometry")
                    var location = geometry.getJSONObject("location")

                    lati = location.getString("lat").toDouble()
                    longi = location.getString("lng").toDouble()
                    Log.d(TAG, "Latitude: ${location.getString("lat")}")
                    Log.d(TAG, "Latitude: ${location.getString("lng")}")



                    //createMarker(lati, longi, name)

                    val loc2 = Location("")
                    loc2.latitude = lati
                    loc2.longitude = longi

                    distanceInMeters = currentLocation.distanceTo(loc2)
                    if(distanceInMeters < distance ){
                        distance = distanceInMeters.toDouble()
                        actualName = name
                        address = myJSONObject.getString("vicinity")

                        actualLocation = loc2

                    }

                    //addLine(currentLocation, loc2)





                    //colEditar.id=myJSONObject.getString("id").toInt()
                    //colBorrar.id=myJSONObject.getString("id").toInt()



                }

                //addLine(currentLocation, actualLocation)
                //createMarker(actualLocation.latitude, actualLocation.longitude, "Closest one")

                Log.d(TAG, "cargaTabla: MENOR DISTANCIA -> $distance")
                Log.d(TAG, "cargaTabla: PLACE NAME -> $actualName")
                Log.d(TAG, "cargaTabla: Address -> $address")

                addressTxt.setText(address)

            }catch (e: JSONException){
                e.printStackTrace()
            }
            }, {
                    error ->  Toast.makeText(this,"Error $error", Toast.LENGTH_LONG).show()
            })
        queue.add(myJsonObjectRequest)
    }

    private fun getInfo(type:String)
    {
        mMap.clear()
        markers.removeAll(markers)
        var queue = Volley.newRequestQueue(this)
        var lati:Double
        var longi:Double
        var precios:Double = 0.0
        var cont:Int = 0
        var promedio:Double = 0.0
        var distance:Double = 99999.0
        var distanceInMeters:Float

        var address:String = ""
        var actualName:String = ""



        Log.d(TAG, "UserLat -> ${userLat} ")
        Log.d(TAG, "UserLat -> ${userLong} ")
        var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=price_level&location=${userLat}%2C${userLong}&radius=2000&type=${type}&key=AIzaSyDV6aFItX960hrbAaI229-8iDa3xTZ-RXU"
        //Log.d(TAG, "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=price_level&location=${userLat}%2C${userLong}&radius=2500&type=restaurant&key=AIzaSyDV6aFItX960hrbAaI229-8iDa3xTZ-RXU")
        var myJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                    response ->  try{
                var myJsonArray = response.getJSONArray("results")
                for(i in 0 until myJsonArray.length()){
                    var myJSONObject = myJsonArray.getJSONObject(i)
                    /*
                    val registro = LayoutInflater.from(this).inflate(R.layout.table_row_np,null,false)
                    val colName = registro.findViewById<View>(R.id.columnaNombre) as TextView
                    val colPrice = registro.findViewById<View>(R.id.columnaEmail) as TextView
                    val colLatitude = registro.findViewById<View>(R.id.colEditar)
                    val colBorrar = registro.findViewById<View>(R.id.colBorrar)
                    */


                    var name = myJSONObject.getString("name")
                    Log.d(TAG, "Nombre:  ${name}" )

                    try{
                        var price = myJSONObject.getString("price_level")
                        Log.d(TAG, "Price rating:  ${price}" )
                        precios += price.toDouble()
                    }catch (e: Exception){
                        Log.d(TAG, "ERROR -> :  ${ e.message}" )
                        cont++
                    }

                    //colPrice.text=myJSONObject.getString("price_level")



                    var geometry = myJSONObject.getJSONObject("geometry")
                    var location = geometry.getJSONObject("location")

                    lati = location.getString("lat").toDouble()
                    longi = location.getString("lng").toDouble()
                    Log.d(TAG, "Latitude: ${location.getString("lat")}")
                    Log.d(TAG, "Latitude: ${location.getString("lng")}")



                    if(type.equals("restaurant")){
                        createMarker(lati, longi, name,1)
                    }else if(type.equals("store")){
                        createMarker(lati, longi, name,2)
                    }else if(type.equals("gym")){
                        createMarker(lati, longi, name,3)
                    }




                    val loc2 = Location("")
                    loc2.latitude = lati
                    loc2.longitude = longi

                    distanceInMeters = currentLocation.distanceTo(loc2)
                    if(distanceInMeters < distance ){
                        distance = distanceInMeters.toDouble()
                        actualName = name
                        address = myJSONObject.getString("vicinity")


                    }

                    addLine(currentLocation, loc2)





                    //colEditar.id=myJSONObject.getString("id").toInt()
                    //colBorrar.id=myJSONObject.getString("id").toInt()



                }

                updateMap()

                promedio = precios / (myJsonArray.length() - cont)

                if(promedio < 1.50){
                    txtCosto.setText("Barato")
                    txtCosto.setTextColor(Color.parseColor("#66bb6a"))
                }else if(promedio >= 1.50 && promedio < 2.00 ){
                    txtCosto.setText("Moderado")
                    txtCosto.setTextColor(Color.parseColor("#f9a825"))
                }else if(promedio >= 2.00){
                    txtCosto.setText("Caro")
                    txtCosto.setTextColor(Color.parseColor("#d32f2f"))
                }else{
                    txtCosto.setText("Moderado")
                    txtCosto.setTextColor(Color.parseColor("#f9a825"))
                }




                Log.d(TAG, "EL PROMEDIO DE COSTO DE LA ZONA ES -> ${promedio} ")
                Log.d(TAG, "cargaTabla: MENOR DISTANCIA -> $distance")
                Log.d(TAG, "cargaTabla: PLACE NAME -> $actualName")
                Log.d(TAG, "cargaTabla: Address -> $address")

                moveCamera(LatLng(userLat, userLong), 13.5f, "My location")
                //drawCircle(LatLng(userLat, userLong), 2000.0)

            }catch (e: JSONException){
                e.printStackTrace()
            }
            }, {
                    error ->  Toast.makeText(this,"Error $error", Toast.LENGTH_LONG).show()
            })
        queue.add(myJsonObjectRequest)
    }

    private fun updateMap(){

        mMap.clear()
        for (marker:MarkerOptions in markers){
            mMap.addMarker(marker)
        }

        drawCircle(LatLng(userLat, userLong), 2000.0)
    }

    private fun verifyMarker(){
        for(marker:MarkerOptions in markers){
            if(marker.title.equals("New")){
                val indexMarker = markers.indexOf(marker)
                markers.removeAt(indexMarker)
            }
        }
    }


}


class User(latitude: Double, longitude: Double){
    lateinit var location: Location

    init{
        location.latitude = latitude
        location.longitude = longitude
    }


}


