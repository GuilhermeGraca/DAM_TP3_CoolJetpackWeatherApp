package dam_A51827.cooljetpackweatherapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.ui.res.stringResource
import dam_A51827.cooljetpackweatherapp.R

class LocationPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val initialLat = intent.getFloatExtra("LATITUDE", 38.7223f).toDouble()
        val initialLon = intent.getFloatExtra("LONGITUDE", -9.1393f).toDouble()
        val initialPosition = LatLng(initialLat, initialLon)

        setContent {
            var selectedLocation by remember { mutableStateOf(initialPosition) }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(initialPosition, 5f)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { latLng ->
                        selectedLocation = latLng
                    }
                ) {
                    Marker(
                        state = MarkerState(position = selectedLocation),
                        title = stringResource(id = R.string.marker_selected_location)
                    )
                }

                Button(
                    onClick = {
                        val resultIntent = Intent().apply {
                            putExtra("LATITUDE", selectedLocation.latitude.toFloat())
                            putExtra("LONGITUDE", selectedLocation.longitude.toFloat())
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = nightButtonBg),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(id = R.string.btn_confirm_location), color = nightPrimaryText, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
