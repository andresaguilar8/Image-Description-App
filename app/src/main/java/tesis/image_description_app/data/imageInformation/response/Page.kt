package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Page(
    @Json(name = "blocks")
    val blocks: List<Block>?,
    @Json(name = "confidence")
    val confidence: Double?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "property")
    val `property`: Property?,
    @Json(name = "width")
    val width: Int?
)