package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tesis.image_description_app.data.imageInformation.response.BoundingPolyX

@JsonClass(generateAdapter = true)
data class TextAnnotation(
    @Json(name = "boundingPoly")
    val boundingPoly: BoundingPolyX,
    @Json(name = "description")
    val description: String,
    @Json(name = "locale")
    val locale: String
)