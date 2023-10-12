package tesis.image_description_app.model

import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import tesis.image_description_app.R
import tesis.image_description_app.viewModel.MainViewModel

class RecognitionListenerImpl(
    private val mainViewModel: MainViewModel,
    private val context: Context
) : RecognitionListener {

    //TODO
    override fun onReadyForSpeech(params: Bundle?) {
        // Called when the speech recognition service is ready to listen
       //mainViewModel.disableSpeechButton()
    }

    override fun onBeginningOfSpeech() {
        // Called when the user starts speaking
    }

    override fun onRmsChanged(rmsdB: Float) {
        // Called when the RMS (Root Mean Square) changes
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        // Called when audio data is received
    }

    override fun onEndOfSpeech() {
        println("onEndOfSpeech")
        mainViewModel.enableSpeechButton()
    }

    override fun onError(error: Int) {
        mainViewModel.notifyEventToUser(context.getString(R.string.recognition_listener_on_error))
        mainViewModel.enableSpeechButton()
    }

    override fun onResults(results: Bundle?) {
        val recognizedText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!recognizedText.isNullOrEmpty()) {
            val firstResult = recognizedText[0]
            mainViewModel.executeAction(firstResult, this.context)
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val recognizedText = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!recognizedText.isNullOrEmpty()) {
            val firstResult = recognizedText[0]
            mainViewModel.executeAction(firstResult, this.context)
        }
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        // Called when an event related to speech recognition occurs
    }
}