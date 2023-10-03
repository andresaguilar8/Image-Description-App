package tesis.image_description_app.network

import tesis.image_description_app.data.imageDescription.request.ImageDescriptionBodyRequest
import tesis.image_description_app.data.imageDescription.request.Message

class ImageDescriptionLogicImpl : ImageDescriptionLogic {

    override suspend fun getImageDescription(parsedStringJson: String): Result<String> {
        val chatGptApiService = ChatGptAPI.instance
        val bodyRequest = generateBodyRequest(parsedStringJson)
        val result: Result<String>

        return try {
            val response = chatGptApiService.fetchForImageDescription(bodyRequest)
            var imageDescription = ""
            var responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                imageDescription = responseBody.choices[0].message.content
                result = Result.success(imageDescription)
            }
            else {
                val exception = CustomException("Ocurrió un error, vuelva a intentar.")
                result = Result.failure(exception)
            }
            println(imageDescription)
            return result
        }
        catch (exception: Exception) {
            //TODO
            val customException = CustomException("Ocurrió un error, vuelva a intentar.")
            Result.failure(exception)
        }
    }

    private fun generateBodyRequest(parsedStringJson: String): ImageDescriptionBodyRequest {
        val temperature = 0.2
        //TODO ver donde poner los strings
        val systemMessage = Message(
            "system",
            "Voy a compartir un conjunto de datos en formato JSON que contiene información sobre una imagen en particular. Mi objetivo es que puedas crear una descripción coherente y comprensible de la imagen, de manera que cualquier persona que lea tu respuesta pueda entender de qué se trata la imagen sin necesidad de verla.  No incluyas detalles técnicos, como términos relacionados con API, JSON o procesos de puntuación de modelos de IA en tu respuesta. Por favor, utiliza los atributos \"labelAnnotations\" para interpretar la imagen y mejorar la calidad del texto. Por favor, para interpretar la imagen, el atributo 'localizedObjectAnnotations' debe tener prioridad para interpretar de qué se trata la imagen."
        )
        val userMessage = Message(
            "user",
            "\"$parsedStringJson\""
        )
        val messages = listOf(systemMessage, userMessage)
        val model = "gpt-3.5-turbo"

        return ImageDescriptionBodyRequest(model, messages, temperature)
    }

}