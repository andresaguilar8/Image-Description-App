package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageInformationResponse(
    @Json(name = "responses")
    val responses: List<Response>
) {
    override fun toString(): String {
        //TODO
        return "ImageInformationResponse(responses=$responses)"
    }
}
