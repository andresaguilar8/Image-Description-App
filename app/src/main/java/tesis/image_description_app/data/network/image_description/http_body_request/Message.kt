package tesis.image_description_app.data.network.image_description.http_body_request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "role")
    val role: String,
    @Json(name = "content")
    val content: String

)