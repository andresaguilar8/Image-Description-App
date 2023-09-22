package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Block(
    @Json(name = "blockType")
    val blockType: String?,
    @Json(name = "boundingBox")
    val boundingBox: BoundingBox?,
    @Json(name = "confidence")
    val confidence: Double?,
    @Json(name = "paragraphs")
    val paragraphs: List<Paragraph>?,
    @Json(name = "property")
    val `property`: Property?
)