package dam_A51827.cooljetpackweatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dam_A51827.cooljetpackweatherapp.R

@Composable
fun LandscapeWeatherUI(
    wIcon: Int,
    latitude: Float,
    longitude: Float,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Float,
    time: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit
) {
    // Fundo principal com Imagem (Landscape)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.coolweatherapp_bg_landscape),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                MainWeatherInfo(wIcon, temperature, windSpeed)
            }

            Box(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Column(verticalArrangement = Arrangement.Center) {
                    DetailsCard(
                        latitude = latitude,
                        longitude = longitude,
                        seaLevelPressure = seaLevelPressure,
                        windDirection = windDirection,
                        time = time,
                        onLatitudeChange = onLatitudeChange,
                        onLongitudeChange = onLongitudeChange,
                        onUpdateButtonClick = onUpdateButtonClick
                    )
                }
            }
        }
    }
}
