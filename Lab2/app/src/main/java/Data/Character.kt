package Data

import retrofit2.http.GET
import retrofit2.http.Query

const val IMAGE_TYPE = 1
const val NAME_TYPE = 2
const val SPECIES_TYPE = 3

data class CharacterAPI(
    val info: Info? = null,
    val results: Array<Results>
)

data class Info(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: String? = null
)

data class Origin(
    val name: String? = null,
    val url: String? = null
)

data class Location(
    val name: String? = null,
    val url: String? = null
)

data class Results(
    val id: Int? = null,
    val name: String,
    val status: String? = null,
    val species: String,
    val type: String? = null,
    val gender: String? = null,
    val origin: Origin? = null,
    val location: Location? = null,
    val image: String,
    val episode: Array<String>? = null,
    val url: String? = null,
    val created: String? = null
) {
    fun getType(): Int {
        if(species == "Human"){
            return IMAGE_TYPE
        }
        else if(species == "Alien"){
            return NAME_TYPE
        }
        else {
            return SPECIES_TYPE
        }
    }
}

interface RickAndMortyApiService{
    @GET("/api/character/?")
    suspend fun getCharacters(
        @Query("page") page: Int
    ) : CharacterAPI
}
