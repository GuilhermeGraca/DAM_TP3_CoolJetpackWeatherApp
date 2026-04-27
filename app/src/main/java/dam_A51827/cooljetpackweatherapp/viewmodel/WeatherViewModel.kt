package dam_A51827.cooljetpackweatherapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_A51827.cooljetpackweatherapp.data.WeatherApiClient
import dam_A51827.cooljetpackweatherapp.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class WeatherViewModel : ViewModel() {
    /*
    * Baseado na implementação do Unscramble
    * _uiState :
    *   É uma variável privada que é usada para armazenar o estado da UI
    *   Apenas é acessível dentro do viewmodel
    *   É um mutableStateFlow que é um stateflow que pode ser alterado
    * uiState :
    *   É uma variável pública que é usada para armazenar o estado da UI
    *   É um stateFlow - variavel reativa q pede sempre um valor inicial, guarda o valor mais recente e notifica quando é alterada. Neste caso é read only
    */
    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    init {
        fetchWeather()
    }

    fun updateLatitude(lat: Float) {
        _uiState.update { currentState ->
            currentState.copy(latitude = lat)
            //pega no que já existe no currentState e cria uma copia com o valor atualizado
        }
    }

    fun updateLongitude(lon: Float) {
        _uiState.update { currentState ->
            currentState.copy(longitude = lon)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeather() {
        val currentLat = _uiState.value.latitude
        val currentLon = _uiState.value.longitude

        viewModelScope.launch {
            val weatherData = WeatherApiClient.getWeather(currentLat, currentLon)
            
            if (weatherData != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        temperature = weatherData.current_weather.temperature,
                        windspeed = weatherData.current_weather.windspeed,
                        winddirection = weatherData.current_weather.winddirection,
                        weathercode = weatherData.current_weather.weathercode,
                        time = weatherData.current_weather.time,
                        seaLevelPressure = weatherData.hourly.pressure_msl.getOrNull(12)?.toFloat() ?: 0f
                    )
                }
            }
        }
    }
}
