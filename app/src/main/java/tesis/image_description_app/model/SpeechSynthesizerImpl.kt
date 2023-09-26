package tesis.image_description_app.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.speech.tts.Voice.LATENCY_NORMAL
import android.speech.tts.Voice.QUALITY_VERY_HIGH
import android.util.Log
import java.util.*

class SpeechSynthesizerImpl(
    context: Context
) : SpeechSynthesizer {

    private var textToSpeechIsInitialized = false
    private val textToSpeech: TextToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            this.initSpeechConfiguration()
            textToSpeechIsInitialized = true;
            Log.i("Conf exitosa de textospeech", "text to speech success")
        } else {
            //TODO Manejar error de configuración del TextToSpeech
        }
    }

    override fun initSpeechConfiguration() {
        //es-us-x-esf-network
        //es-us-x-esc-network
        //es-es-x-eee-local
        //es-us-x-esd-local
        //es-es-x-eef-local
        val locale = Locale("es", "ES") // Configura el idioma a español
        if (textToSpeech.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE)
            textToSpeech.language = locale
        val voice = Voice( "es-es-x-eef-local", locale, QUALITY_VERY_HIGH ,LATENCY_NORMAL, false, null)
        textToSpeech.voice = voice
    }

    override fun speak(textToSpeak: String) {
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun setVoice(voice: Voice) {
        textToSpeech.voice = voice
    }

    override fun release() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}