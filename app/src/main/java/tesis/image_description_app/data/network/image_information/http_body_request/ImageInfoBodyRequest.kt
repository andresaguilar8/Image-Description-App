package tesis.image_description_app.data.network.image_information.http_body_request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageInfoBodyRequest(
    @Json(name = "requests")
    val requests: List<Request>
)