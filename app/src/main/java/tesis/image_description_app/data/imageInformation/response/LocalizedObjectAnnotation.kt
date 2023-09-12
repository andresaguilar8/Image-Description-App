package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tesis.image_description_app.data.imageInformation.response.BoundingPolyX

@JsonClass(generateAdapter = true)
data class LocalizedObjectAnnotation(
    @Json(name = "boundingPoly")
    val boundingPoly: BoundingPolyX,
    @Json(name = "mid")
    val mid: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "score")
    val score: Double
)