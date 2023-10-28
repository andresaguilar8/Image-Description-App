package tesis.image_description_app.model.speech_mechanism

import android.content.Context
import android.content.Intent
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import tesis.image_description_app.R

class SpeechRecognizer(
    context: Context,
    recognitionListener: RecognitionListener
) {

    private var speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private var intent: Intent

    init {
        try {
            speechRecognizer.setRecognitionListener(recognitionListener)
            intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es")
        } catch (exception: Exception) {
            throw Exception(context.getString(R.string.speech_recognition_init_error))
        }

    }

    fun startListening() {
        speechRecognizer.startListening(intent)
    }


}