package dam_A51827.cooljetpackweatherapp.ui

data class WeatherUIState(
    val latitude: Float = 38.72f, 
    val longitude: Float = -9.14f, 
    val temperature: Float = 0f,
    val windspeed: Float = 0f,
    val winddirection: Int = 0,
    val weathercode: Int = 0,
    val seaLevelPressure: Float = 0f,
    val time: String = ""
)