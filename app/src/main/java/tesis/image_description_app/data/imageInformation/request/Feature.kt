package tesis.image_description_app.data.imageInformation.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Feature(
    @Json(name = "maxResults")
    val maxResults: Int = 8,
    @Json(name = "type")
    val type: String
)