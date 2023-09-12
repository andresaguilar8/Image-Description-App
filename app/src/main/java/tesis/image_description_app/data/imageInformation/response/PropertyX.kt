package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tesis.image_description_app.data.imageInformation.response.DetectedBreak

@JsonClass(generateAdapter = true)
data class PropertyX(
    @Json(name = "detectedBreak")
    val detectedBreak: DetectedBreak
)