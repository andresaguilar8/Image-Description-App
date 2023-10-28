package tesis.image_description_app.data.network.image_information

interface ImageInformationLogic {

    suspend fun getImageInformation(base64Image: String): Result<String>

}