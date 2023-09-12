package tesis.image_description_app.data.imageInformation.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "source")
    val source: Source
)