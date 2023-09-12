package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SafeSearchAnnotation(
    @Json(name = "adult")
    val adult: String,
    @Json(name = "medical")
    val medical: String,
    @Json(name = "racy")
    val racy: String,
    @Json(name = "spoof")
    val spoof: String,
    @Json(name = "violence")
    val violence: String
)