package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagesWithMatchingImage(
    @Json(name = "fullMatchingImages")
    val fullMatchingImages: List<FullMatchingImage>?,
    @Json(name = "pageTitle")
    val pageTitle: String?,
    @Json(name = "partialMatchingImages")
    val partialMatchingImages: List<PartialMatchingImage>?,
    @Json(name = "score")
    val score: Double?,
    @Json(name = "url")
    val url: String?
)