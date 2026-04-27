package dam_A51827.cooljetpackweatherapp.ui

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dam_A51827.cooljetpackweatherapp.data.WMO_WeatherCode
import dam_A51827.cooljetpackweatherapp.data.getWeatherCodeMap
import dam_A51827.cooljetpackweatherapp.viewmodel.WeatherViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherUI(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    
    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val windSpeed = weatherUIState.windspeed
    val windDirection = weatherUIState.winddirection
    val weathercode = weatherUIState.weathercode
    val seaLevelPressure = weatherUIState.seaLevelPressure
    val time = weatherUIState.time
    
    val configuration = LocalConfiguration.current
    //hora está depois do T e antes do : - se for null mete 12
    val hour = time.substringAfter("T").substringBefore(":").toIntOrNull() ?: 12
    // É dia antes das 19h e dps das 6
    val day = hour in 6..18
    
    val mapt = getWeatherCodeMap()
    val wCode = mapt.get(weathercode)
    
    val wImage = when (wCode) {
        WMO_WeatherCode.CLEAR_SKY -> if (day) "day" else "night"
        WMO_WeatherCode.MAINLY_CLEAR -> if (day) "cloudy_day_1" else "cloudy_night_1"
        WMO_WeatherCode.PARTLY_CLOUDY -> if (day) "cloudy_day_2" else "cloudy_night_2"
        else -> wCode?.image
    }
    
    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(wImage, "drawable", context.packageName)
    
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherUI(
            wIcon = wIcon,
            latitude = latitude,
            longitude = longitude,
            temperature = temperature,
            windSpeed = windSpeed,
            windDirection = windDirection,
            weathercode = weathercode,
            seaLevelPressure = seaLevelPressure,
            time = time,
            onLatitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }
            },
            onLongitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    } else {
        PortraitWeatherUI(
            wIcon = wIcon,
            latitude = latitude,
            longitude = longitude,
            temperature = temperature,
            windSpeed = windSpeed,
            windDirection = windDirection,
            weathercode = weathercode,
            seaLevelPressure = seaLevelPressure,
            time = time,
            onLatitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }
            },
            onLongitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    }
}

