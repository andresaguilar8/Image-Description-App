package tesis.image_description_app.model

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import tesis.image_description_app.viewModel.MainViewModel
import tesis.image_description_app.viewModel.TextToSpeechViewModel

class RecognitionListenerImpl(
    private val mainViewModel: MainViewModel,
    private val textToSpeechViewModel: TextToSpeechViewModel
) : RecognitionListener {

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
        println("onError")
        textToSpeechViewModel.speak("Error: no se escuchó nada")
        mainViewModel.enableSpeechButton()
    }

    override fun onResults(results: Bundle?) {
        val recognizedText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!recognizedText.isNullOrEmpty()) {
            val firstResult = recognizedText[0]
            mainViewModel.executeAction(firstResult)
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        println("partial results")
        val recognizedText = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!recognizedText.isNullOrEmpty()) {
            val firstResult = recognizedText[0]
            mainViewModel.executeAction(firstResult)
        }
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        // Called when an event related to speech recognition occurs
    }
}