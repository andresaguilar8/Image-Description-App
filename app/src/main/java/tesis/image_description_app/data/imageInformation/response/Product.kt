package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "description")
    val description: String?,
    @Json(name = "displayName")
    val displayName: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "productCategory")
    val productCategory: String?,
    @Json(name = "productLabels")
    val productLabels: List<ProductLabel?>?
)