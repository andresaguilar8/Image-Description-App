package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tesis.image_description_app.data.imageInformation.response.Color

@JsonClass(generateAdapter = true)
data class DominantColors(
    @Json(name = "colors")
    val colors: List<Color>
)