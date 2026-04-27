package dam_A51827.cooljetpackweatherapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam_A51827.cooljetpackweatherapp.R

@Composable
fun MainWeatherInfo(wIcon: Int, temperature: Float, windSpeed: Float) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val iconSize = if (isLandscape) 140.dp else 240.dp

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (wIcon != 0) {
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(iconSize)
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
            text = stringResource(id = R.string.label_temperature),
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
                text = " " + stringResource(id = R.string.label_wind_speed_metric),
                color = nightAccentValue,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.label_windspeed),
            color = nightSecondaryLabel,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
