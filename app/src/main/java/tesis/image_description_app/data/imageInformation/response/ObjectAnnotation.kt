package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ObjectAnnotation(
    @Json(name = "languageCode")
    val languageCode: String?,
    @Json(name = "mid")
    val mid: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "score")
    val score: Double?
)