package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebEntity(
    @Json(name = "description")
    val description: String?,
    @Json(name = "entityId")
    val entityId: String?,
    @Json(name = "score")
    val score: Double?
)