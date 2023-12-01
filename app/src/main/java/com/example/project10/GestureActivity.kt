package com.example.project10
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class GestureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestureScreen()
        }
    }
}

@Composable
fun GestureScreen() {
    var ballPosition by remember { mutableStateOf(Offset(100f, 100f)) }
    var gestureLog by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(ballPosition, onBallMove = { offset ->
            ballPosition = offset
            gestureLog = gestureLog + "Moved to (${offset.x}, ${offset.y})"
        })

        BottomSection(gestureLog)
    }
}

@Composable
fun TopSection(ballPosition: Offset, onBallMove: (Offset) -> Unit) {
    Box(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth().pointerInput(Unit) {
            detectDragGestures { change, _ ->
                onBallMove(change.position)
            }
        }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color.Red, center = ballPosition, radius = 30f)
        }
    }
}
@Composable
fun BottomSection(gestureLog: List<String>) {
    Column(modifier = Modifier.fillMaxHeight().padding(16.dp)
    ) {
        gestureLog.forEach { log ->
            Text(log)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GestureScreenPreview() {
    GestureScreen()
}