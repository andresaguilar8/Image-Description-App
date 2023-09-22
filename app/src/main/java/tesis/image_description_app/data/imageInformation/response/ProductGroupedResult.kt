package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductGroupedResult(
    @Json(name = "boundingPoly")
    val boundingPoly: BoundingPoly?,
    @Json(name = "objectAnnotations")
    val objectAnnotations: List<ObjectAnnotation>?,
    @Json(name = "results")
    val results: List<Result>?
)