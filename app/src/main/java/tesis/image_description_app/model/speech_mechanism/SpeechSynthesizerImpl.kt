package tesis.image_description_app.model.speech_mechanism

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.speech.tts.Voice.LATENCY_NORMAL
import android.speech.tts.Voice.QUALITY_VERY_HIGH
import tesis.image_description_app.R
import tesis.image_description_app.model.speech_mechanism.SpeechSynthesizer
import java.util.*
import tesis.image_description_app.view.showInitError

class SpeechSynthesizerImpl(
    private val context: Context
) : SpeechSynthesizer {


    private val textToSpeech: TextToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS)
            this.initSpeechConfiguration()
        else
            showInitError(context, context.getString(R.string.speech_synt_init_error))
    }

    override fun initSpeechConfiguration() {
        val locale = Locale("es", "ES")
        if (textToSpeech.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE)
            textToSpeech.language = locale
        val voice = Voice(context.getString(R.string.tts_voice), locale, QUALITY_VERY_HIGH ,LATENCY_NORMAL, false, null)
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