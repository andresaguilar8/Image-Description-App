package tesis.image_description_app.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.model.SpeechSynthesizer

class TextToSpeechViewModelFactory(
    private val speechSynthesizer: SpeechSynthesizer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == TextToSpeechViewModel::class.java) {
            return TextToSpeechViewModel(speechSynthesizer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}