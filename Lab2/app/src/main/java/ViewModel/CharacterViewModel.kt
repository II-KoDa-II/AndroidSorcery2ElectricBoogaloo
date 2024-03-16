package ViewModel

import Data.CharacterAPI
import Data.RickAndMortyApiService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterViewModel() : ViewModel() {
    var CharacterData: MutableLiveData<CharacterAPI> = MutableLiveData()
    var page = 19

    fun parsData(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val productAPI = retrofit.create(RickAndMortyApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val characters = productAPI.getCharacters(page)
            CharacterData.postValue(characters)
        }
    }

    fun upPage(): Boolean{
        if(CharacterData.value?.info?.next != null){
            page += 1
            return true
        } else{
            return false
        }
    }

    fun downPage(): Boolean{
        if(CharacterData.value?.info?.prev != null){
            page -= 1
            return true
        } else{
            return false
        }
    }
}