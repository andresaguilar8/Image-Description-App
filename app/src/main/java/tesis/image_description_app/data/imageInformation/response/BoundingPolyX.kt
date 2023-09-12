package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoundingPolyX(
    @Json(name = "normalizedVertices")
    val normalizedVertices: List<NormalizedVertice>
)