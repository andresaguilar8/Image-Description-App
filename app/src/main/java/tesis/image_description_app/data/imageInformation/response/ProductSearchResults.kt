package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductSearchResults(
    @Json(name = "indexTime")
    val indexTime: String?,
    @Json(name = "productGroupedResults")
    val productGroupedResults: List<ProductGroupedResult>?,
    @Json(name = "results")
    val results: List<Result>?
)