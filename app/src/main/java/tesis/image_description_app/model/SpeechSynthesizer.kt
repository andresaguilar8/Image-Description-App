package tesis.image_description_app.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.speech.tts.Voice.LATENCY_NORMAL
import android.speech.tts.Voice.QUALITY_VERY_HIGH
import android.util.Log
import java.util.*

class SpeechSynthesizer(context: Context) {

    var textToSpeechIsInitialized = false

    private val textToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            textToSpeechIsInitialized = true;

            Log.i("conf exitosa de textospeech", "text to speech success")
        } else {
            //TODO
            // Manejar error de configuración del TextToSpeech
        }
    }

    fun speak(text: String) {
        val locale = Locale("es", "ES") // Configura el idioma a español
        if (textToSpeech.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE) {
            textToSpeech.language = locale

        }
     //   val availableVoices = textToSpeech.voices

        // Loop through availableVoices to see the list of voices
       // for (voice in availableVoices) {
         //   Log.d("TTS", "Voice: ${voice.name}, Locale: ${voice.locale}")
       // }

//        es-us-x-esf-network
        //es-us-x-esc-network
        //es-es-x-eee-local
        //es-us-x-esd-local
        //es-es-x-eef-local
        val voice = Voice( "es-es-x-eef-local", locale, QUALITY_VERY_HIGH ,LATENCY_NORMAL, false, null)
        textToSpeech.voice = voice
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun release() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}