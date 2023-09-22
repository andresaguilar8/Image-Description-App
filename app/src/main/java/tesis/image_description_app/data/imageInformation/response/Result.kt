package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "image")
    val image: String?,
    @Json(name = "product")
    val product: Product?,
    @Json(name = "score")
    val score: Double?
)