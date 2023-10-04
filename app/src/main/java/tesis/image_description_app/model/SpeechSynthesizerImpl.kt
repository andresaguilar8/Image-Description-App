package tesis.image_description_app.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.speech.tts.Voice.LATENCY_NORMAL
import android.speech.tts.Voice.QUALITY_VERY_HIGH
import java.util.*

class SpeechSynthesizerImpl(
    context: Context
) : SpeechSynthesizer {

    private val textToSpeech: TextToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            this.initSpeechConfiguration()
        } else {
            //TODO Manejar error de configuración del TextToSpeech
        }
    }

    override fun initSpeechConfiguration() {
        val locale = Locale("es", "ES")
        if (textToSpeech.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE)
            textToSpeech.language = locale
        val voice = Voice( "es-es-x-eef-local", locale, QUALITY_VERY_HIGH ,LATENCY_NORMAL, false, null)
        textToSpeech.voice = voice
    }

    override fun speak(textToSpeak: String) {
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_ADD, null, null)
    }

    override fun stop() {
        textToSpeech.stop()
    }

    override fun release() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}