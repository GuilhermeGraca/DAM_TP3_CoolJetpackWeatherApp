package dam_A51827.cooljetpackweatherapp.data

import kotlin.collections.forEach

//Notação
@Serializable
data class WeatherData (
    var latitude : String ,
    var longitude : String ,
    var timezone : String ,
    var current_weather : CurrentWeather ,
    var hourly : Hourly
)

data class CurrentWeather (
    var temperature : Float ,
    var windspeed : Float ,
    var winddirection : Int ,
    var weathercode : Int ,
    var time : String
)

data class Hourly (
    var time : ArrayList<String>,
    var temperature_2m : ArrayList<Float>,
    var weathercode : ArrayList<Int>,
    var pressure_msl : ArrayList<Double>
)
enum class WMO_WeatherCode(var code: Int, var image: String) {
    CLEAR_SKY(0, "day"),
    MAINLY_CLEAR(1, "cloudy_day_1"),
    PARTLY_CLOUDY(2, "cloudy_day_2"),
    OVERCAST(3, "cloudy"),
    FOG(45, "cloudy"),
    DEPOSITING_RIME_FOG(48, "cloudy"),
    DRIZZLE_LIGHT(51, "rainy_1"),
    DRIZZLE_MODERATE(53, "rainy_2"),
    DRIZZLE_DENSE(55, "rainy_3"),
    FREEZING_DRIZZLE_LIGHT(56, "rainy_4"),
    FREEZING_DRIZZLE_DENSE(57, "rainy_5"),
    RAIN_SLIGHT(61, "rainy_1"),
    RAIN_MODERATE(63, "rainy_2"),
    RAIN_HEAVY(65, "rainy_5"),
    FREEZING_RAIN_LIGHT(66, "rainy_4"),
    FREEZING_RAIN_HEAVY(67, "rainy_5"),
    SNOW_FALL_SLIGHT(71, "snowy_1"),
    SNOW_FALL_MODERATE(73, "snowy_2"),
    SNOW_FALL_HEAVY(75, "snowy_3"),
    SNOW_GRAINS(77, "snowy_1"),
    RAIN_SHOWERS_SLIGHT(80, "rainy_5"),
    RAIN_SHOWERS_MODERATE(81, "rainy_6"),
    RAIN_SHOWERS_VIOLENT(82, "rainy_7"),
    SNOW_SHOWERS_SLIGHT(85, "snowy_4"),
    SNOW_SHOWERS_HEAVY(86, "snowy_5"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "thunder"),
    THUNDERSTORM_HAIL_SLIGHT(96, "thunder"),
    THUNDERSTORM_HAIL_HEAVY(99, "thunder")
}
fun getWeatherCodeMap () : Map <Int,WMO_WeatherCode> {
    var weatherMap = kotlin.collections.HashMap<Int, WMO_WeatherCode>()
    WMO_WeatherCode.values().forEach {
        weatherMap.put(it.code ,it)
    }
    return weatherMap
}