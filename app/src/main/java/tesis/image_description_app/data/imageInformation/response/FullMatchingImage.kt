package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FullMatchingImage(
    @Json(name = "score")
    val score: Double?,
    @Json(name = "url")
    val url: String?
)