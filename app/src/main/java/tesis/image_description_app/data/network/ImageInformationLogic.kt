package tesis.image_description_app.data.network

interface ImageInformationLogic {

    suspend fun getImageInformation(base64Image: String): Result<String>

}