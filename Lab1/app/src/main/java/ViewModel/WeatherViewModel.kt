package ViewModel

import Data.ApiWeather
import Data.RetrofitCitysApi
import Data.RetrofitWeatherApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel() : ViewModel() {
    var WeatherData : MutableLiveData<ApiWeather> = MutableLiveData()

    fun parsingFunction(sinyName: String) {
        var result: ApiWeather = ApiWeather(null, null, null, null, null)
        val retrofitWeather = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val productApi = retrofitWeather.create(RetrofitCitysApi::class.java)

        val parsCorutin: Deferred<ApiWeather> = CoroutineScope(Dispatchers.Default).async {
            val cities = productApi.getCities(sinyName, "38a3dea867ce86500b70b5f451427e14")
            val weather = retrofitWeather.create(RetrofitWeatherApi::class.java)
            val weatherResult = weather.getWeather(cities[0].lat, cities[0].lon, "38a3dea867ce86500b70b5f451427e14")

            result = weatherResult
            return@async result
        }

        runBlocking {
            val SecondResult = parsCorutin.await()
            WeatherData.value = SecondResult
            return@runBlocking SecondResult
        }
    }
}