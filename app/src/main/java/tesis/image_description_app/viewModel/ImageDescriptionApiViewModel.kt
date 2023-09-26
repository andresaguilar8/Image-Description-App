package tesis.image_description_app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tesis.image_description_app.network.ChatGptAPI
import tesis.image_description_app.network.ImageDescriptionRepository

class ImageDescriptionApiViewModel(
    private val textToSpeechViewModel: TextToSpeechViewModel
) : ViewModel() {

    private val imageDescriptionRepository = ImageDescriptionRepository(ChatGptAPI.instance)
    private var imageDescriptionIsAvailable by mutableStateOf(false)
    var imageDescription by mutableStateOf("")

    //TODO manejar errores
    fun requestImageDescription(parsedStringJson: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            imageDescriptionRepository.getImageDescription(parsedStringJson).onSuccess { response ->
//                imageDescription = response
//                textToSpeechViewModel.speak(imageDescription)
//            }.onFailure { response ->
//                imageDescription = response.toString()
//                Log.e("error", "CHAT GPT API ERROR")
//                textToSpeechViewModel.speak("Error API de CHat gpt")
//            }
            imageDescription = "La imagen muestra un diseño gráfico con colores dominantes en tonos azules, verdes y magenta. Se puede observar un patrón simétrico en forma de círculo con elementos gráficos y fuentes de texto. En la parte superior derecha de la imagen, hay un código de barras bidimensional. Además, se puede identificar el logotipo de MBC 3 en la imagen. La imagen es segura y no contiene contenido adulto, médico, provocativo, falso o violento.\n"
            imageDescriptionIsAvailable = true
            textToSpeechViewModel.speak(imageDescription)
        }
    }

//    fun getImageDescription(): String {
//        return this.imageDescription
//    }

    override fun onCleared(): Unit {
        this.textToSpeechViewModel.releaseSpeech()
    }

    fun imageDescriptionIsAvailable(): Boolean {
        return this.imageDescriptionIsAvailable
    }
}