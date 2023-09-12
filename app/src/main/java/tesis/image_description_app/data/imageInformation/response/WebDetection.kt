package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebDetection(
    @Json(name = "bestGuessLabels")
    val bestGuessLabels: List<BestGuessLabel>,
    @Json(name = "visuallySimilarImages")
    val visuallySimilarImages: List<VisuallySimilarImage>,
    @Json(name = "webEntities")
    val webEntities: List<WebEntity>
)