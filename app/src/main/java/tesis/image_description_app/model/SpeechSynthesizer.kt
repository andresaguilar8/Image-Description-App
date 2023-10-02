package tesis.image_description_app.model

interface SpeechSynthesizer {

    fun speak(textToSpeak: String)

    fun initSpeechConfiguration()

    fun release()

}