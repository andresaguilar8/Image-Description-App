package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LabelAnnotation(
    @Json(name = "description")
    val description: String,
    @Json(name = "mid")
    val mid: String,
    @Json(name = "score")
    val score: Double,
    @Json(name = "topicality")
    val topicality: Double
)