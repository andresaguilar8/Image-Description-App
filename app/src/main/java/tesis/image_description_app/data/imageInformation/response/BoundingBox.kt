package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoundingBox(
    @Json(name = "normalizedVertices")
    val normalizedVertices: List<NormalizedVertice>?,
    @Json(name = "vertices")
    val vertices: List<Vertice>?
)