package com.example.mobilelab2_2

import Data.CharacterAPI
import ViewModel.CharacterAdapter
import ViewModel.CharacterViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val nextButton: Button = findViewById(R.id.Nextbutton)
        val backButton: Button = findViewById(R.id.Backbutton)
        val PageText: TextView = findViewById(R.id.pageText)

        val CharacterModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        CharacterModel.parsData()

        CharacterModel.CharacterData.observe(this, Observer<CharacterAPI> {
            value: CharacterAPI ->
            PageText.text = CharacterModel.page.toString()
            recyclerView.adapter = CharacterAdapter(this@MainActivity, value.results)
        })

        nextButton.setOnClickListener{
            if(CharacterModel.upPage()){
                CharacterModel.parsData()
            } else {
                Toast.makeText(this, "End of list", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener{
            if(CharacterModel.downPage()){
                CharacterModel.parsData()
            } else {
                Toast.makeText(this, "End of list", Toast.LENGTH_SHORT).show()
            }
        }
    }
}