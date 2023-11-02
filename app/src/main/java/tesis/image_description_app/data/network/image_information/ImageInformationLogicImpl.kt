package tesis.image_description_app.data.network.image_information

import tesis.image_description_app.data.network.CustomException
import tesis.image_description_app.data.network.GoogleVisionAPI

class ImageInformationLogicImpl(
    private val bodyRequestParser: ImageInfoParser
) : ImageInformationLogic {

    override suspend fun getImageInformation(base64Image: String): Result<String> {
        val googleVisionAPI = GoogleVisionAPI.instance
        val jsonBodyRequest = this.bodyRequestParser.generateJSONBodyRequest(base64Image)
        val result: Result<String>
        return try {
            val response = googleVisionAPI.fetchForImageInformation(jsonBodyRequest as String)
            var jsonFromAPI = response.body()
            var jsonStringToReturn = ""

            if (response.isSuccessful && jsonFromAPI != null) {
                jsonStringToReturn = this.bodyRequestParser.parseJSON(jsonFromAPI)
                println("long final: ${jsonStringToReturn.length}")
                //TODO con longitud = 9257 anduvo
                result = Result.success(jsonStringToReturn)
            } else {
                val exception = CustomException("Ocurrió un error, vuelva a intentar.")
                result = Result.failure(exception)
            }
            return result
        } catch (exception: Exception) {
            val customException = CustomException("Ocurrió un error de red, verifica tu conexión y vuelve a intentar.")
            Result.failure(customException)
        }
    }



}