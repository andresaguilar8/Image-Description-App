package tesis.image_description_app.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.SpeechRecognizer
import tesis.image_description_app.model.SpeechSynthesizer
import tesis.image_description_app.R

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

    fun executeAction(speechToString: String?, context: Context) {
        speechToString?.let {
            when {
                it.contains(context.getString(R.string.open_camera), ignoreCase = true) ->
                    if (!cameraViewModel.cameraIsOpen())
                        cameraViewModel.openCamera()
                    else
                        speechSynthesizer.speak(context.getString(R.string.camera_already_open))
                it.contains(context.getString(R.string.close_camera), ignoreCase = true) ->
                    if (cameraViewModel.cameraIsOpen())
                        cameraViewModel.openCamera()
                    else
                        speechSynthesizer.speak(context.getString(R.string.camera_not_open))
                it.contains(context.getString(R.string.take_photo), ignoreCase = true) ->
                    if (cameraViewModel.cameraIsOpen())
                        cameraViewModel.activateTakePhotoCommand()
                    else
                        speechSynthesizer.speak(context.getString(R.string.camera_not_open))
                else -> speechSynthesizer.speak(context.getString(R.string.not_recognized_action))
            }
        }
    }

    fun notifyEventToUser(messageToNotify: String) {
        this.speechSynthesizer.speak(messageToNotify)
    }

    fun startListeningForCommandAction() {
        this.speechRecognizer.startListening()
    }

}