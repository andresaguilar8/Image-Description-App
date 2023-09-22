package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductLabel(
    @Json(name = "key")
    val key: String?,
    @Json(name = "value")
    val value: String?
)