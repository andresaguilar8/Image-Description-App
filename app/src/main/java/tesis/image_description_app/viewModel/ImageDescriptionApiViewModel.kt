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

    private var imageDescription by mutableStateOf("")

    fun requestImageDescription(parsedStringJson: String) {
        println(parsedStringJson.length)
        viewModelScope.launch(Dispatchers.IO) {
            imageDescriptionLogicImpl.getImageDescription(parsedStringJson).onSuccess { response ->
                imageDescription = response
                textToSpeechViewModel.speak(imageDescription)
            }.onFailure { response ->
                Log.e("error", "CHAT GPT API ERROR")
                textToSpeechViewModel.speak(response.toString())
            }
        }
    }

    override fun onCleared(): Unit {
        this.textToSpeechViewModel.releaseSpeech()
    }

}