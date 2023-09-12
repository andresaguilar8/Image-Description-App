package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Color(
    @Json(name = "color")
    val color: ColorX,
    @Json(name = "pixelFraction")
    val pixelFraction: Double,
    @Json(name = "score")
    val score: Double
)