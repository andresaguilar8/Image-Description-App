package tesis.image_description_app.view_model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import tesis.image_description_app.model.speech_mechanism.SpeechRecognizer
import tesis.image_description_app.model.speech_mechanism.SpeechSynthesizer
import tesis.image_description_app.R

class MainViewModel(
    private val cameraViewModel: CameraViewModel,
    private val imageDescriptionViewModel: ImageDescriptionViewModel,
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
        this.speechSynthesizer.stop()
        this.speechButtonPressed = !this.speechButtonPressed
    }

    fun enableSpeechButton() {
        this.speechButtonPressed = false
    }

    fun executeAction(speechToString: String?, context: Context) {
        speechToString?.let {
            when {
                it.contains(context.getString(R.string.generate_new_description), ignoreCase = true) ->
                    if (cameraViewModel.hasCapturedImage()) {
                        speechSynthesizer.speak(context.getString(R.string.img_being_processed))
                        imageDescriptionViewModel.fetchForImageDescription(cameraViewModel.getEncodedImage())
                    }
                    else
                        speechSynthesizer.speak(context.getString(R.string.no_img_for_new_description))
                it.contains(context.getString(R.string.repeat_img_description), ignoreCase = true) ->
                    if (imageDescriptionViewModel.hasImageDescription())
                        imageDescriptionViewModel.provideImgDescription()
                    else
                        speechSynthesizer.speak(context.getString(R.string.no_img_for_repeating_description))
                it.contains(context.getString(R.string.open_camera), ignoreCase = true) ->
                    if (!cameraViewModel.cameraIsOpen())
                        cameraViewModel.openCamera()
                    else
                        speechSynthesizer.speak(context.getString(R.string.camera_already_open))
                it.contains(context.getString(R.string.close_camera), ignoreCase = true) ->
                    if (cameraViewModel.cameraIsOpen()) {
                        cameraViewModel.closeCamera()
                        speechSynthesizer.speak(context.getString(R.string.camera_closed))
                    }
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