package tesis.image_description_app.model

import android.speech.tts.Voice

interface SpeechSynthesizer {

    fun speak(textToSpeak: String)

    fun initSpeechConfiguration()

    fun release()

}