package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tesis.image_description_app.data.imageInformation.response.DetectedLanguageX

@JsonClass(generateAdapter = true)
data class PropertyXX(
    @Json(name = "detectedLanguages")
    val detectedLanguages: List<DetectedLanguageX>
)