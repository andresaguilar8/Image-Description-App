package tesis.image_description_app.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.speech.tts.Voice.LATENCY_NORMAL
import android.speech.tts.Voice.QUALITY_VERY_HIGH
import android.view.View
import android.widget.Toast
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import java.util.*

class SpeechSynthesizerImpl(
    private val context: Context
) : SpeechSynthesizer {

    private val textToSpeech: TextToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS)
            this.initSpeechConfiguration()
        else
            this.showTextToSpeechInitError()
    }

    private fun showTextToSpeechInitError() {
        val errorMessage =
            "Ocurrió un error al inicializar la opción de convertir texto en habla. Por favor vuelve a abrir la aplicación."
        val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_LONG)
        toast.show()

        toast.view?.let {
            ViewCompat.setImportantForAccessibility(
                it,
                ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES
            )
        }
        toast.view?.let {
            ViewCompat.setAccessibilityDelegate(it, object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    if (host != null) {
                        if (info != null) {
                            super.onInitializeAccessibilityNodeInfo(host, info)
                        }
                    }
                    info.text = errorMessage
                }
            })
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