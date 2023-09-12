package tesis.image_description_app.data.imageDescription.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//TODO ver si es necesaria
@JsonClass(generateAdapter = true)
data class MessageX(
    @Json(name = "content")
    val content: String,
    @Json(name = "role")
    val role: String
)