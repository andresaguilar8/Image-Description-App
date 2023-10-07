package tesis.image_description_app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.SpeechRecognizer
import tesis.image_description_app.model.SpeechSynthesizer

class MainViewModel(
    private val cameraViewModel: CameraViewModel,
    private val speechSynthesizer: SpeechSynthesizer
) : ViewModel() {

    private var speechButtonPressed by  mutableStateOf(false)
    private lateinit var speechRecognizer: SpeechRecognizer

    fun setSpeechRecognizer(speechRecognizer: SpeechRecognizer) {
        this.speechRecognizer = speechRecognizer
    }

    fun buttonPressed(): Boolean {
        return this.speechButtonPressed
    }

    fun changeSpeechButtonState() {
        this.speechButtonPressed = !this.speechButtonPressed
        this.speechSynthesizer.stop()
    }

    fun enableSpeechButton() {
        this.speechButtonPressed = false
    }

    fun executeAction(speechToString: String?) {
        speechToString?.let {
            when {
                it.contains("abrir cámara", ignoreCase = true) ->
                    if (!cameraViewModel.cameraIsOpen())
                        cameraViewModel.openCamera()
                    else
                        speechSynthesizer.speak("La cámara ya está abierta.")
                it.contains("cerrar cámara", ignoreCase = true) ->
                    if (cameraViewModel.cameraIsOpen())
                        cameraViewModel.openCamera()
                    else
                        speechSynthesizer.speak("La cámara no está abierta.")
                it.contains("tomar foto", ignoreCase = true) ->
                    if (cameraViewModel.cameraIsOpen())
                        cameraViewModel.activateTakePhotoCommand()
                    else
                        speechSynthesizer.speak("La cámara no está abierta.")
                else -> speechSynthesizer.speak("No se reconoció una acción posible.")
            }
        }
    }

    fun notifyEventToUser(messageToNotify: String) {
        this.speechSynthesizer.speak(messageToNotify)
    }

    //        intent.putExtra(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE, true)

    fun startListeningForCommandAction() {
        this.speechRecognizer.startListening()
    }

}