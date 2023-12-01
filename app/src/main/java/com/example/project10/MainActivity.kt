package com.example.project10
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

 class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContent {
             MainScreen()
         }
     }
 }

 @Composable
 fun MainScreen() {
     val context = LocalContext.current
     var startSensorActivity by remember { mutableStateOf(false) }

     Column {
         Text("Welcome to the Sensor and Gesture App")
         Spacer(modifier = Modifier.height(16.dp))
         Button(onClick = { startSensorActivity = true }) {
             Text("Go to Sensor Activity")
         }
         if (startSensorActivity) {
             LaunchedEffect(Unit) {
                 context.startActivity(Intent(context, SensorActivity::class.java))
                 startSensorActivity = false
             }
         }
     }
 }

 @Preview(showBackground = true)
 @Composable
 fun DefaultPreview() {
     MainScreen()
 }