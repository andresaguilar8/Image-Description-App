package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LabelAnnotation(
    @Json(name = "boundingPoly")
    val boundingPoly: BoundingPoly?,
    @Json(name = "confidence")
    val confidence: Double?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "locale")
    val locale: String?,
    @Json(name = "locations")
    val locations: List<Location>?,
    @Json(name = "mid")
    val mid: String?,
    @Json(name = "properties")
    val properties: List<Property>?,
    @Json(name = "score")
    val score: Double?,
    @Json(name = "topicality")
    val topicality: Double?
)