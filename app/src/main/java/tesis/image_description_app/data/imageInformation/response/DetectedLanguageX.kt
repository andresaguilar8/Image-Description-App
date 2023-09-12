package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetectedLanguageX(
    @Json(name = "confidence")
    val confidence: Double,
    @Json(name = "languageCode")
    val languageCode: String
)