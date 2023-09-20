package tesis.image_description_app.data.imageDescription.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDescriptionBodyRequest(
    @Json(name = "model")
    val model: String,
    @Json(name = "messages")
    val messages: List<Message>,
    @Json(name = "temperature")
    val temperature: Double
)