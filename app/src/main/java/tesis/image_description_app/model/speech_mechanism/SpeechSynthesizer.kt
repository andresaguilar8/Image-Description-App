package tesis.image_description_app.model.speech_mechanism

interface SpeechSynthesizer {

    fun speak(textToSpeak: String)

    fun initSpeechConfiguration()

    fun release()

    fun stop()

}