package tesis.image_description_app.data.imageInformation.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageInfoBodyRequest(
    @Json(name = "requests")
    val requests: List<Request>
)