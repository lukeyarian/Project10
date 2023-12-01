package com.example.project10
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

@Suppress("DEPRECATION")
class SensorActivity : ComponentActivity(), SensorEventListener, LocationListener {

    private lateinit var sensorManager: SensorManager
    private var temperatureSensor: Sensor? = null
    private var pressureSensor: Sensor? = null
    private lateinit var locationManager: LocationManager

    private var temperature by mutableStateOf("Temperature: ")
    private var pressure by mutableStateOf("Pressure: ")
    private var locationInfo by mutableStateOf("Location: ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        requestLocationUpdates()

        setContent {
            SensorScreen(name = "Your Name", location = locationInfo, temperature = temperature, pressure = pressure)
        }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this)
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses?.get(0)
                    if (address != null) {
                        locationInfo = "Location: ${address.locality}, ${address.adminArea}"
                    }
                }
            }
        } catch (e: IOException) {
            locationInfo = "Location: Error finding location"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                temperature = "Temperature: ${event.values[0]}°C"
            }
            Sensor.TYPE_PRESSURE -> {
                pressure = "Pressure: ${event.values[0]} hPa"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //if needed
    }

    override fun onResume() {
        super.onResume()
        temperatureSensor?.also { temp ->
            sensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL)
        }
        pressureSensor?.also { press ->
            sensorManager.registerListener(this, press, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        locationManager.removeUpdates(this)
    }

    @Composable
    fun SensorScreen(name: String, location: String, temperature: String, pressure: String) {
        var navigateToGesture by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Column {
            Text(name)
            Text(location)
            Text(temperature)
            Text(pressure)
            Button(
                onClick = {},
                modifier = Modifier.pointerInput(Unit) {
                    detectDragGestures { _, _ ->
                        navigateToGesture = true
                    }
                }
            ) {
                Text("Gesture Playground")
            }

            if (navigateToGesture) {
                LaunchedEffect(Unit) {
                    context.startActivity(Intent(context, GestureActivity::class.java))
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SensorScreen(name = "Your Name", location = "City, State", temperature = "Temperature: XX°C", pressure = "Pressure: YY hPa")
    }
}