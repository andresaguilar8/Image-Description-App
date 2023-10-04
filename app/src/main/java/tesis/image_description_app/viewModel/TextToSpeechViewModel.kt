package tesis.image_description_app.viewModel

import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.SpeechSynthesizer

class TextToSpeechViewModel(
    private val speechSynthesizer: SpeechSynthesizer
) : ViewModel() {

    fun speak(textToSpeak: String) {
        this.speechSynthesizer.speak(textToSpeak)
    }

    fun releaseSpeech() {
        this.speechSynthesizer.release()
    }

    fun stop() {
        this.speechSynthesizer.stop()
    }


}