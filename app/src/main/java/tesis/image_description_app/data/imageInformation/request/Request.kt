package tesis.image_description_app.data.imageInformation.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    @Json(name = "image")
    val image: Image,
    @Json(name = "features")
    val features: List<Feature>

)