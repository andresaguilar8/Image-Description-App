package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tesis.image_description_app.network.ImageDescriptionLogic

class ImageDescriptionApiViewModel(
    private val textToSpeechViewModel: TextToSpeechViewModel,
    private val imageDescriptionLogicImpl: ImageDescriptionLogic
) : ViewModel() {

    private var imageDescriptionIsAvailable by mutableStateOf(false)
    private var imageDescription by mutableStateOf("")

    //TODO manejar errores
    fun requestImageDescription(parsedStringJson: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageDescriptionLogicImpl.getImageDescription(parsedStringJson).onSuccess { response ->
                imageDescription = response
                textToSpeechViewModel.speak(imageDescription)
            }.onFailure { response ->
//                imageDescription = response.toString()
                println(response)
                Log.e("error", "CHAT GPT API ERROR")
                textToSpeechViewModel.speak("Error API de CHat gpt")
            }
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