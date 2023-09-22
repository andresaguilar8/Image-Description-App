package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Landmark(
    @Json(name = "position")
    val position: Position?,
    @Json(name = "type")
    val type: String?
)