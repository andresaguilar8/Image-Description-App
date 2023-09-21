package tesis.image_description_app.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.SpeechSynthesizer

class TextToSpeechViewModel(context: Context) : ViewModel() {

    private val speechSynthesizer = SpeechSynthesizer(context)

    fun speak(textToSpeak: String) {
        this.speechSynthesizer.speak(textToSpeak)
    }

}