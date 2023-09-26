package tesis.image_description_app.network

interface ImageDescriptionLogic {

    suspend fun getImageDescription(parsedStringJson: String): Result<String>

}