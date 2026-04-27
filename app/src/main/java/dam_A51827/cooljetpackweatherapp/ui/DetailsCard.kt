package dam_A51827.cooljetpackweatherapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import dam_A51827.cooljetpackweatherapp.R

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
                            onClick = { /* ligação para a activity */ }
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
                Text(stringResource(id = R.string.btn_update_weather), color = nightPrimaryText, fontWeight = FontWeight.Bold)
            }
        }
    }
}
