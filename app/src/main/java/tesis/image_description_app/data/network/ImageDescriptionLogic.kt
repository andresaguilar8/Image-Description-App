package tesis.image_description_app.data.network

interface ImageDescriptionLogic {

    suspend fun getImageDescription(parsedStringJson: String): Result<String>

}