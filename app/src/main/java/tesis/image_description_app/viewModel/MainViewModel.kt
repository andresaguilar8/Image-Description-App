package tesis.image_description_app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.SpeechRecognizer

class MainViewModel(
    private val cameraViewModel: CameraViewModel,
    private val textToSpeechViewModel: TextToSpeechViewModel,
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
    }

    fun enableSpeechButton() {
        this.speechButtonPressed = false
    }

    fun executeAction(speechToString: String?) {
        speechToString?.let {
            when {
                it.contains("abrir cámara", ignoreCase = true) -> cameraViewModel.openCamera()
                it.contains("cerrar cámara", ignoreCase = true) -> cameraViewModel.closeCamera()
                it.contains("tomar foto", ignoreCase = true) -> cameraViewModel.activateTakePhotoCommand()
                else -> textToSpeechViewModel.speak("No reconoció una acción posible.")
            }
        }
    }

    //        intent.putExtra(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE, true)

    fun startListeningForCommandAction() {
        this.speechRecognizer.startListening()
    }

//    fun startListeningForVoiceCommand() {
//        speechRecognizer.setRecognitionListener(recognitionListener)
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
//        intent.putExtra(
//            RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
//            9999999 // Set the desired silence duration in milliseconds (e.g., 6 seconds)
//        )
//        intent.putExtra(
//            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
//            9999999 // Set the desired silence duration in milliseconds (e.g., 6 seconds)
//        )
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es")
//        speechRecognizer.startListening(intent)
//    }


//    fun stopListening() {
//        println("deja de escuchar")
//        this.speechRecognizer.stopListening()
//    }

}