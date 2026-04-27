package dam_A51827.cooljetpackweatherapp.ui

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dam_A51827.cooljetpackweatherapp.R
import dam_A51827.cooljetpackweatherapp.data.WMO_WeatherCode
import dam_A51827.cooljetpackweatherapp.data.getWeatherCodeMap
import dam_A51827.cooljetpackweatherapp.viewmodel.WeatherViewModel

// Cores retiradas da app antiga
val nightPrimaryText = Color(0xFFFFFFFF)
val nightAccentValue = Color(0xFFa9cdce)
val nightSecondaryLabel = Color(0xFFb2b8bf)
val nightInternalCardBg = Color(0xFF212b37)
val nightExternalCardBg = Color(0xB3384754)
val nightExternalCardBorder = Color(0xCC566772)
val nightButtonBg = Color(0xFF5e35b1)

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
    //TODO
    // Fundo principal com Imagem (Portrait)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.coolweatherapp_bg_img_upscaled),
            contentDescription = "Background",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Layout principal em Coluna/Vertical
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 80.dp),
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
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MainWeatherInfo(wIcon, temperature, windSpeed)

            Box(modifier = Modifier.weight(1f).padding(start = 32.dp)) {
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

@Composable
fun MainWeatherInfo(wIcon: Int, temperature: Float, windSpeed: Float) {
    // Zona superior, com o ícone do clima, a temperatura e o vento
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (wIcon != 0) {
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(240.dp)
            )
        }

        Text(
            text = "${temperature}ºC",
            color = nightPrimaryText,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            )
        )
        Text(
            text = "TEMPERATURE",
            color = nightSecondaryLabel,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "$windSpeed",
                color = nightAccentValue,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " km/h",
                color = nightAccentValue,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Text(
            text = "WINDSPEED",
            color = nightSecondaryLabel,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DetailsCard(
    latitude: Float,
    longitude: Float,
    seaLevelPressure: Float,
    windDirection: Int,
    time: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit
) {
    var latText by remember(latitude) { mutableStateOf(latitude.toString()) }
    var lonText by remember(longitude) { mutableStateOf(longitude.toString()) }

    // Caixa exterior
    Card(
        colors = CardDefaults.cardColors(containerColor = nightExternalCardBg),
        border = BorderStroke(1.dp, nightExternalCardBorder),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Caixa interior sólida
            Card(
                colors = CardDefaults.cardColors(containerColor = nightInternalCardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Zona de input da Latitude
                        Column(modifier = Modifier.weight(1f)) {
                            Text("LATITUDE", color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            TextField(
                                value = latText,
                                onValueChange = { latText = it },
                                textStyle = TextStyle(color = nightPrimaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = nightSecondaryLabel,
                                    unfocusedIndicatorColor = nightSecondaryLabel,
                                    cursorColor = nightPrimaryText
                                ),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        // Zona de input da Longitude
                        Column(modifier = Modifier.weight(1f)) {
                            Text("LONGITUDE", color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            TextField(
                                value = lonText,
                                onValueChange = { lonText = it },
                                textStyle = TextStyle(color = nightPrimaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = nightSecondaryLabel,
                                    unfocusedIndicatorColor = nightSecondaryLabel,
                                    cursorColor = nightPrimaryText
                                ),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
        
                    // Zona da Pressão e Direção do Vento
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("PRESSURE", color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("$seaLevelPressure hPa", color = nightPrimaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("WIND DIR.", color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("${windDirection}º", color = nightPrimaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    // Linha separadora horizontal hr
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = nightSecondaryLabel.copy(alpha = 0.5f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
        
                    // Zona da Hora
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.clock_regular_full_icon),
                            contentDescription = "Clock",
                            tint = nightSecondaryLabel,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(time, color = nightSecondaryLabel, fontSize = 14.sp)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        
            // Botão de Atualizar dentro do card transparente
            Button(
                onClick = { 
                    onLatitudeChange(latText)
                    onLongitudeChange(lonText)
                    onUpdateButtonClick() 
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = nightButtonBg),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("UPDATE WEATHER", color = nightPrimaryText, fontWeight = FontWeight.Bold)
            }
        }
    }
}