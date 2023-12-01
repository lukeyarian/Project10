package com.example.project10
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.input.pointer.pointerInput

class SensorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorScreen()
        }
    }
}

@Composable
fun SensorScreen() {
    // Dummy data, replace with actual sensor data retrieval
    var name = "Your Name"
    var location = "Your City, Your State"
    var temperature = "Temperature: XX°C"
    var otherSensorValue = "Other Sensor: YY"

    var navigateToGesture by remember { mutableStateOf(false) }

    Column {
        Text(text = name)
        Text(text = location)
        Text(text = temperature)
        Text(text = otherSensorValue)
        Spacer(modifier = Modifier.height(16.dp))
        GestureButton { navigateToGesture = true }
    }

    if (navigateToGesture) {
        // Logic to navigate to Gesture Activity
        // Example: Navigation.findNavController().navigate(...)
    }
}

@Composable
fun GestureButton(onFling: () -> Unit) {
    // Dummy button, replace with actual fling detection
    Button(
        onClick = {},
        modifier = Modifier.pointerInput(Unit) {
            detectDragGestures { _, _ ->
                onFling()
            }
        }
    ) {
        Text("Gesture Playground")
    }
}

@Preview(showBackground = true)
@Composable
fun SensorScreenPreview() {
    SensorScreen()
}