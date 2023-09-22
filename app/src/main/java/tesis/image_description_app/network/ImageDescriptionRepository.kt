package tesis.image_description_app.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tesis.image_description_app.data.imageDescription.request.ImageDescriptionBodyRequest
import tesis.image_description_app.data.imageDescription.request.Message

class ImageDescriptionRepository(private val chatGptApiService: ChatGptApiService) {

    suspend fun getImageDescription(parsedStringJson: String): Result<String> {

        val chatgptBodyRequest = generateBodyRequest(parsedStringJson)

        return try {
            val response = chatGptApiService.fetchForImageDescription(chatgptBodyRequest)
            var imageDescription = ""

            if (response.isSuccessful) {
                Log.e("CHAT GPT: response successfull", "")
                var responseBody = response.body()
                if (responseBody != null) {
                    Log.e("CHAT GPT: responseBody != null", "")
                    imageDescription = responseBody.choices[0].message.content
                }
            }
            else {
                Log.e("CHAT GPT: response  not successful", "${response.body()}")
            }
            println(imageDescription)
            Result.success(imageDescription)
        }
        catch (exception: Exception) {
            Log.e("CHAT GPT: EXCEPTION", "$exception")
            Result.failure(exception)
        }
    }

    private fun generateBodyRequest(parsedStringJson: String): ImageDescriptionBodyRequest {
        val temperature = 0.2
        val system_message = Message(
            "system",
            "Voy a compartir un conjunto de datos en formato JSON que contiene información sobre una imagen en particular. Mi objetivo es que puedas crear una descripción coherente y comprensible de la imagen, de manera que cualquier persona que lea tu respuesta pueda entender de qué se trata la imagen sin necesidad de verla.  No incluyas detalles técnicos, como términos relacionados con API, JSON o procesos de puntuación de modelos de IA en tu respuesta. Por favor, utiliza los atributos \"labelAnnotations\" para interpretar la imagen y mejorar la calidad del texto. Por favor, para interpretar la imagen, el atributo 'localizedObjectAnnotations' debe tener prioridad para interpretar de qué se trata la imagen."
        )
        val user_message = Message(
            "user",
            "\"$parsedStringJson\""
        )
        val messages = listOf(system_message, user_message)
        val model = "gpt-3.5-turbo"
        val chatgptBodyRequest = ImageDescriptionBodyRequest(model, messages, temperature)

        //Prueba para ver el json serializado
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(ImageDescriptionBodyRequest::class.java)
        val json = adapter.toJson(chatgptBodyRequest)
        println(json)
        return chatgptBodyRequest
    }

}