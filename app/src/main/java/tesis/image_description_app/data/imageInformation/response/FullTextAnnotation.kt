package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FullTextAnnotation(
    @Json(name = "pages")
    val pages: List<Page>?,
    @Json(name = "text")
    val text: String?
)