package tesis.image_description_app.data.network.image_description

interface ImageDescriptionLogic {

    suspend fun getImageDescription(parsedStringJson: String): Result<String>

}