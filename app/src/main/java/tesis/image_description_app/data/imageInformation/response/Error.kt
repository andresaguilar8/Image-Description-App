package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "details")
    val details: List<Detail?>?,
    @Json(name = "message")
    val message: String?
)