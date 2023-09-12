package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vertice(
    @Json(name = "x")
    val x: Int,
    @Json(name = "y")
    val y: Int
)