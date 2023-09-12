package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NormalizedVertice(
    @Json(name = "x")
    val x: Double,
    @Json(name = "y")
    val y: Double
)