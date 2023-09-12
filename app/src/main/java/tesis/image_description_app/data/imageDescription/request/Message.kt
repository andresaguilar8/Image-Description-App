package tesis.image_description_app.data.imageDescription.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "role")
    val role: String,
    @Json(name = "content")
    val content: String

)