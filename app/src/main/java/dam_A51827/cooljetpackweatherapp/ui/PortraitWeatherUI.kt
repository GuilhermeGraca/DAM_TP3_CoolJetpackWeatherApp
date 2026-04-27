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
fun PortraitWeatherUI(
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
    // Fundo principal com Imagem (Portrait)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.coolweatherapp_bg_img_upscaled),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Layout principal em Coluna/Vertical
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .offset(y = (-24).dp), // Puxa tudo  para cima
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainWeatherInfo(wIcon, temperature, windSpeed)
            Spacer(modifier = Modifier.height(32.dp))
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
