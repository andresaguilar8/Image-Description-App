package tesis.image_description_app.data.network.image_description.http_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Choice(
    @Json(name = "finish_reason")
    val finishReason: String,
    @Json(name = "index")
    val index: Int,
    @Json(name = "message")
    val message: Message
)