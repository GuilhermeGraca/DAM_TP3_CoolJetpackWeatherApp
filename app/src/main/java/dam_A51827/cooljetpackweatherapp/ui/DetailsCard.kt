package dam_A51827.cooljetpackweatherapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import dam_A51827.cooljetpackweatherapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star

data class FavoriteLocation(val name: String, val lat: Float, val lon: Float)

fun loadFavorites(context: android.content.Context): List<FavoriteLocation> {
    /*Os favoritos são guardados em "SharedPreferences" como "nome:latitude,longitude"
    O objetivo é transformar isso de volta em latitude e longitude e criar um objeto FavoriteLocation
    para cada favorito. Depois, cria-se uma lista de favoritos e retorna-se */

    //As sharedPreferences são como um "ficheiro"  nativo do android onde se guardam informações
    //são guardados em chave:valor, 
    //Neste caso, a "chave" é o nome da cidade e o "valor" é a latitude e longitude
    val prefs = context.getSharedPreferences("weather_favorites", android.content.Context.MODE_PRIVATE)
    
    val allFavorites = prefs.all
    val favoriteList = mutableListOf<FavoriteLocation>()
    
    // Passamos por todos os items guardados na memória do telemóvel
    for (entry in allFavorites) {
        val cityName = entry.key
        val coordinatesString = entry.value as? String
        
        // Verifica se o texto guardado está "preenchido"
        if (coordinatesString != null) {
            // Divide a string latitude,longitude pela vírgula
            val parts = coordinatesString.split(",")
            
            // Confirma que existem duas partes - latitude e longitude
            if (parts.size == 2) {
                val latitudeString = parts[0]
                val longitudeString = parts[1]
                
                // Converte as strings em números Float e cria o Favorito
                val latitudeFloat = latitudeString.toFloatOrNull() ?: 0f
                val longitudeFloat = longitudeString.toFloatOrNull() ?: 0f
                
                val newFavorite = FavoriteLocation(cityName, latitudeFloat, longitudeFloat)
                favoriteList.add(newFavorite)
            }
        }
    }
    
    return favoriteList
}

fun saveFavorite(context: android.content.Context, name: String, lat: Float, lon: Float) {
    val prefs = context.getSharedPreferences("weather_favorites", android.content.Context.MODE_PRIVATE)
    prefs.edit().putString(name, "$lat,$lon").apply()
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

    // by remember serve para guardar o valor entre recomposições - sempre que a variavel muda, guarda o valor
    //latitude é a chave - quando a latitude mudar, o valor será atualizado
    //mutableStateOf cria um estado observavel q notifica sempre q muda
    var latText by remember(latitude) { mutableStateOf(latitude.toString()) }
    var lonText by remember(longitude) { mutableStateOf(longitude.toString()) }
    
    val context = LocalContext.current
    var favorites by remember { mutableStateOf(loadFavorites(context)) }
    var showDialog by remember { mutableStateOf(false) }
    var newFavName by remember { mutableStateOf("") }
    
    // cria-se um contrato que diz ao Android que se quer começar uma activity e receber um resultado de volta
    //ActivityResultContracts é uma classe do Android que define os contratos para iniciar activities
    val contract = ActivityResultContracts.StartActivityForResult()

    // registra-se o launcher na UI do Compose. Esta função é ativada quando a janela do mapa fecha
    //rememberLauncherForActivityResult é uma função do Compose que cria um launcher para uma activity
    val locationPickerLauncher = rememberLauncherForActivityResult(contract) { result ->
        
        // Verifica se o user clicou no confirmar e não no botão de voltar para trás
        val userConfirmed = (result.resultCode == Activity.RESULT_OK)
        
        if (userConfirmed) {
            val intentData = result.data //intentData é a informacao que volta do mapa para a app
            
            if (intentData != null) {
                // Lê-se a latitude e longitude que o mapa enviou
                val newLatitude = intentData.getFloatExtra("LATITUDE", latitude)
                val newLongitude = intentData.getFloatExtra("LONGITUDE", longitude)
                
                // Atualiza-se as caixas de texto com os novos valores
                latText = newLatitude.toString()
                lonText = newLongitude.toString()
            }
        }
    }

    // Caixa exterior
    Card(
        colors = CardDefaults.cardColors(containerColor = nightExternalCardBg),
        border = BorderStroke(1.dp, nightExternalCardBorder),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(32.dp)
        ) {
            // Caixa interior sólida
            Card(
                colors = CardDefaults.cardColors(containerColor = nightInternalCardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Zona de input da Latitude
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(id = R.string.label_latitude), color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
                                modifier = Modifier.width(90.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        // Zona de input da Longitude
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(id = R.string.label_longitude), color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
                                modifier = Modifier.width(90.dp)
                            )
                        }
                        
                        // Botão do Location Picker
                        IconButton(
                            onClick = { 
                                val intent = Intent(context, LocationPickerActivity::class.java).apply {
                                    putExtra("LATITUDE", latText.toFloatOrNull() ?: latitude)
                                    putExtra("LONGITUDE", lonText.toFloatOrNull() ?: longitude)
                                }
                                locationPickerLauncher.launch(intent)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.earth_americas_solid_full),
                                contentDescription = "Location Picker",
                                tint = nightSecondaryLabel,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
        
                    // Zona da Pressão e Direção do Vento
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(stringResource(id = R.string.label_pressure), color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("$seaLevelPressure hPa", color = nightPrimaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(stringResource(id = R.string.label_wind_dir), color = nightSecondaryLabel, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("${windDirection}º", color = nightPrimaryText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    // Linha separadora horizontal hr
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = nightSecondaryLabel.copy(alpha = 0.5f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))
        
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
            
            Spacer(modifier = Modifier.height(16.dp))
            // Row com Botão de Atualizar e Guardar Favorito
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { 
                        onLatitudeChange(latText)
                        onLongitudeChange(lonText)
                        onUpdateButtonClick() 
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = nightButtonBg),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(id = R.string.btn_update_weather), color = nightPrimaryText, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Botão de Guardar Favorito
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.size(50.dp).background(nightExternalCardBorder, RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Star, contentDescription = "Save Favorite", tint = nightAccentValue)
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Favorite Locations", color = nightPrimaryText) },
            text = {
                Column {
                    Text("Save Current Location:", color = nightSecondaryLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = newFavName,
                        onValueChange = { newFavName = it },
                        label = { Text("Location Name", color = nightSecondaryLabel) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = nightInternalCardBg,
                            unfocusedContainerColor = nightInternalCardBg,
                            focusedTextColor = nightPrimaryText,
                            unfocusedTextColor = nightPrimaryText
                        )
                    )
                    
                    if (favorites.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Your Saved Locations:", color = nightSecondaryLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(favorites) { fav ->
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = nightInternalCardBg,
                                    border = BorderStroke(1.dp, nightExternalCardBorder),
                                    onClick = {
                                        latText = fav.lat.toString()
                                        lonText = fav.lon.toString()
                                        onLatitudeChange(latText)
                                        onLongitudeChange(lonText)
                                        onUpdateButtonClick()
                                        showDialog = false
                                    }
                                ) {
                                    val displayName = if (fav.name.isBlank()) "Unknown" else fav.name
                                    Text(
                                        text = displayName,
                                        color = nightPrimaryText,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            },
            containerColor = nightExternalCardBg,
            confirmButton = {
                TextButton(onClick = {
                    if (newFavName.isNotBlank()) {
                        val currentLat = latText.toFloatOrNull() ?: latitude
                        val currentLon = lonText.toFloatOrNull() ?: longitude
                        saveFavorite(context, newFavName, currentLat, currentLon)
                        favorites = loadFavorites(context)
                        newFavName = ""
                        showDialog = false
                    }
                }) {
                    Text("Save", color = nightAccentValue, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = nightSecondaryLabel)
                }
            }
        )
    }
}
