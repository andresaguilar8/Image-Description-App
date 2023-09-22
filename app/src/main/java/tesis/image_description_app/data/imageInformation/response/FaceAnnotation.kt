package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FaceAnnotation(
    @Json(name = "angerLikelihood")
    val angerLikelihood: String?,
    @Json(name = "blurredLikelihood")
    val blurredLikelihood: String?,
    @Json(name = "boundingPoly")
    val boundingPoly: BoundingPoly?,
    @Json(name = "detectionConfidence")
    val detectionConfidence: Double?,
    @Json(name = "fdBoundingPoly")
    val fdBoundingPoly: FdBoundingPoly?,
    @Json(name = "headwearLikelihood")
    val headwearLikelihood: String?,
    @Json(name = "joyLikelihood")
    val joyLikelihood: String?,
    @Json(name = "landmarkingConfidence")
    val landmarkingConfidence: Double?,
    @Json(name = "landmarks")
    val landmarks: List<Landmark>?,
    @Json(name = "panAngle")
    val panAngle: Double?,
    @Json(name = "rollAngle")
    val rollAngle: Double?,
    @Json(name = "sorrowLikelihood")
    val sorrowLikelihood: String?,
    @Json(name = "surpriseLikelihood")
    val surpriseLikelihood: String?,
    @Json(name = "tiltAngle")
    val tiltAngle: Double?,
    @Json(name = "underExposedLikelihood")
    val underExposedLikelihood: String?
)