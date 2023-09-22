package tesis.image_description_app.data.imageInformation.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ColorX(
    @Json(name = "alpha")
    val alpha: Int?,
    @Json(name = "blue")
    val blue: Int?,
    @Json(name = "green")
    val green: Int?,
    @Json(name = "red")
    val red: Int?
)