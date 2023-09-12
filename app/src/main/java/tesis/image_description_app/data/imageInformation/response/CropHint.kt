package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tesis.image_description_app.data.imageInformation.response.BoundingPoly

@JsonClass(generateAdapter = true)
data class CropHint(
    @Json(name = "boundingPoly")
    val boundingPoly: BoundingPoly,
    @Json(name = "confidence")
    val confidence: Double,
    @Json(name = "importanceFraction")
    val importanceFraction: Double
)