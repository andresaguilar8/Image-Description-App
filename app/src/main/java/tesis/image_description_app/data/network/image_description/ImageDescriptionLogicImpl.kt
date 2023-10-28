package tesis.image_description_app.data.network.image_description

import android.content.Context
import tesis.image_description_app.R
import tesis.image_description_app.data.network.ChatGptAPI
import tesis.image_description_app.data.network.CustomException
import tesis.image_description_app.data.network.image_description.http_body_request.ImageDescriptionBodyRequest
import tesis.image_description_app.data.network.image_description.http_body_request.Message

class ImageDescriptionLogicImpl(
    private val context: Context
) : ImageDescriptionLogic {

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
            return result
        }
        catch (exception: Exception) {
            val customException = CustomException("Ocurrió un error, vuelva a intentar.")
            Result.failure(customException)
        }
    }

    private fun generateBodyRequest(parsedStringJson: String): ImageDescriptionBodyRequest {
        val temperature = 0.2
        val systemMessage = Message("system", context.getString(R.string.system_role_content))
        val userMessage = Message("user", "\"$parsedStringJson\"")
        val messages = listOf(systemMessage, userMessage)
        val model = context.getString(R.string.model_name)
        return ImageDescriptionBodyRequest(model, messages, temperature)
    }

}