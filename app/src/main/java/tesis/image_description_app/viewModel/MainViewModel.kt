package tesis.image_description_app.viewModel

import android.content.Context
import android.content.Intent
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import tesis.image_description_app.model.RecognitionListenerImpl
import tesis.image_description_app.model.SpeechSynthesizer

class MainViewModel(
    private val context: Context,
    private val cameraViewModel: CameraViewModel,
    private val speechSynthesizer: SpeechSynthesizer
) : ViewModel() {

    private var speechButtonPressed by  mutableStateOf(false)
    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognitionListener: RecognitionListener = RecognitionListenerImpl(this, speechSynthesizer)

    @OptIn(ExperimentalPermissionsApi::class)
    fun onSpeechButtonPress(micPermissionState: PermissionState) {
        this.speechButtonPressed = !this.speechButtonPressed
        if (this.speechButtonPressed && micPermissionState.status.isGranted) {
            this.startListening()
        }
    }

    fun disableSpeechButton() {
        this.speechButtonPressed = true
    }

    fun speechButtonIsPressed(): Boolean {
        return this.speechButtonPressed
    }

    fun enableSpeechButton() {
        this.speechButtonPressed = false
    }

    fun executeAction(speechToString: String?) {
        if (speechToString != null) {
            println(speechToString)
            if (speechToString.contains("abrir cámara") || speechToString.contains("Abrir cámara"))
                cameraViewModel.openCamera()
            else
                if (speechToString.contains("cerrar cámara") || speechToString.contains("Cerrar cámara"))
                    cameraViewModel.closeCamera()
                else
                    if (speechToString.contains("Tomar foto") || speechToString.contains("tomar foto"))
                        this.cameraViewModel.activateTakePhotoCommand()
                    else
                        speechSynthesizer.speak("No reconoció una acción posible.")
        }
    }

    //        intent.putExtra(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE, true)

    private fun startListening() {
        speechRecognizer.setRecognitionListener(recognitionListener)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es")
        speechRecognizer.startListening(intent)
    }

    fun startListeningForVoiceCommand() {
        speechRecognizer.setRecognitionListener(recognitionListener)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
            9999999 // Set the desired silence duration in milliseconds (e.g., 6 seconds)
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
            9999999 // Set the desired silence duration in milliseconds (e.g., 6 seconds)
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es")
        speechRecognizer.startListening(intent)
    }


    fun stopListening() {
        println("deja de escuchar")
        this.speechRecognizer.stopListening()
    }

}