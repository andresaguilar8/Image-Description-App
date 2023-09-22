package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Property(
    @Json(name = "detectedBreak")
    val detectedBreak: DetectedBreak?,
    @Json(name = "detectedLanguages")
    val detectedLanguages: List<DetectedLanguage?>?
)