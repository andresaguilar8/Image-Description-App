package tesis.image_description_app.data.network.image_description.http_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDescriptionResponse(
    @Json(name = "choices")
    val choices: List<Choice>,
    @Json(name = "created")
    val created: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "object")
    val objectX: String,
    @Json(name = "usage")
    val usage: Usage
)