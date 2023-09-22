package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "cropHintsAnnotation")
    val cropHintsAnnotation: CropHintsAnnotation?,
    @Json(name = "error")
    val error: Error?,
    @Json(name = "faceAnnotations")
    val faceAnnotations: List<FaceAnnotation>?,
    //@Json(name = "fullTextAnnotation")
    //val fullTextAnnotation: FullTextAnnotation?,
    @Json(name = "imagePropertiesAnnotation")
    var imagePropertiesAnnotation: ImagePropertiesAnnotation?,
    @Json(name = "labelAnnotations")
    val labelAnnotations: List<LabelAnnotation>?,
    @Json(name = "landmarkAnnotations")
    val landmarkAnnotations: List<LandmarkAnnotation>?,
    @Json(name = "localizedObjectAnnotations")
    val localizedObjectAnnotations: List<LocalizedObjectAnnotation>?,
    @Json(name = "logoAnnotations")
    val logoAnnotations: List<LogoAnnotation>?,
    @Json(name = "productSearchResults")
    val productSearchResults: ProductSearchResults?,
    @Json(name = "textAnnotations")
    val textAnnotations: List<TextAnnotation>?,
    @Json(name = "webDetection")
    val webDetection: WebDetection?
)