package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "cropHintsAnnotation")
    var cropHintsAnnotation: CropHintsAnnotation?,
    @Json(name = "error")
    var error: Error?,
    @Json(name = "faceAnnotations")
    var faceAnnotations: List<FaceAnnotation>?,
    //@Json(name = "fullTextAnnotation")
    //val fullTextAnnotation: FullTextAnnotation?,
    @Json(name = "imagePropertiesAnnotation")
    var imagePropertiesAnnotation: ImagePropertiesAnnotation?,
    @Json(name = "labelAnnotations")
    var labelAnnotations: List<LabelAnnotation>?,
    @Json(name = "landmarkAnnotations")
    var landmarkAnnotations: List<LandmarkAnnotation>?,
    @Json(name = "localizedObjectAnnotations")
    var localizedObjectAnnotations: List<LocalizedObjectAnnotation>?,
    @Json(name = "logoAnnotations")
    var logoAnnotations: List<LogoAnnotation>?,
    @Json(name = "productSearchResults")
    var productSearchResults: ProductSearchResults?,
    @Json(name = "textAnnotations")
    var textAnnotations: List<TextAnnotation>?,
    @Json(name = "webDetection")
    var webDetection: WebDetection?
)