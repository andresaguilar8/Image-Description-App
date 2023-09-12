package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Symbol(
    @Json(name = "boundingBox")
    val boundingBox: BoundingBox,
    @Json(name = "confidence")
    val confidence: Double,
    @Json(name = "property")
    val `property`: PropertyX,
    @Json(name = "text")
    val text: String
)