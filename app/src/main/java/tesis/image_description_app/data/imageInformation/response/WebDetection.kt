package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebDetection(
    @Json(name = "bestGuessLabels")
    val bestGuessLabels: List<BestGuessLabel>?,
    @Json(name = "fullMatchingImages")
    val fullMatchingImages: List<FullMatchingImage>?,
    @Json(name = "pagesWithMatchingImages")
    val pagesWithMatchingImages: List<PagesWithMatchingImage>?,
    @Json(name = "partialMatchingImages")
    val partialMatchingImages: List<PartialMatchingImage>?,
    @Json(name = "visuallySimilarImages")
    val visuallySimilarImages: List<VisuallySimilarImage>?,
    @Json(name = "webEntities")
    val webEntities: List<WebEntity>?
)