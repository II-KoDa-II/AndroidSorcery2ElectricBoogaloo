package com.example.myapplication

import Data.ApiWeather
import ViewModel.WeatherViewModel
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherModel = ViewModelProvider(this)[WeatherViewModel::class.java]
//Получение данных из View Model

        val inputText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val cityText = findViewById<TextView>(R.id.textView3)
        val weatherText = findViewById<TextView>(R.id.textView4)
        val descriptionText = findViewById<TextView>(R.id.textView5)
        val weatherIcon = findViewById<ImageView>(R.id.imageView)

        button.setOnClickListener{
            weatherModel.parsingFunction(inputText.text.toString())
        }

        weatherModel.WeatherData.observe(this, Observer<ApiWeather>{
                value: ApiWeather ->
            cityText.text = "Город: " + value.city?.name
            weatherText.text = "Погода: " + value.list?.get(0)?.main?.temp?.minus(272.15)
                ?.toBigDecimal()?.setScale(2,RoundingMode.UP).toString()
            descriptionText.text = "Описание: " + (value.list?.get(0)?.weather?.get(0)?.description)
            val icnURL = "https://openweathermap.org/img/w/" + (value.list?.get(0)?.weather?.get(0)?.icon) + ".png"
            Picasso.get().load(icnURL).into(weatherIcon)
        })
//Вывод полученных API данных
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val cityText = findViewById<EditText>(R.id.editTextText)
        outState.putString("city", cityText.text.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val сityText = findViewById<EditText>(R.id.editTextText)
        val сityName: String = savedInstanceState.getString("city").toString()
        сityText.setText(сityName)
    }
//Обработка поворота экрана
}