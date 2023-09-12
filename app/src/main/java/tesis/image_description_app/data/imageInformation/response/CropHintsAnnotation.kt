package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CropHintsAnnotation(
    @Json(name = "cropHints")
    val cropHints: List<CropHint>
)