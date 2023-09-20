package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeatureResponse(
    //@Json(name = "cropHintsAnnotation")
    //val cropHintsAnnotation: CropHintsAnnotation?,
    //@Json(name = "fullTextAnnotation")
    //val fullTextAnnotation: FullTextAnnotation?,
    @Json(name = "imagePropertiesAnnotation")
    val imagePropertiesAnnotation: ImagePropertiesAnnotation?,
    @Json(name = "labelAnnotations")
    val labelAnnotations: List<LabelAnnotation>?,
    @Json(name = "localizedObjectAnnotations")
    val localizedObjectAnnotations: List<LocalizedObjectAnnotation>?,
    @Json(name = "logoAnnotations")
    var logoAnnotations: List<LogoAnnotation>?,
    @Json(name = "safeSearchAnnotation")
    val safeSearchAnnotation: SafeSearchAnnotation?,
    //@Json(name = "textAnnotations")
    //val textAnnotations: List<TextAnnotation>?,
    @Json(name = "webDetection")
    val webDetection: WebDetection?
)