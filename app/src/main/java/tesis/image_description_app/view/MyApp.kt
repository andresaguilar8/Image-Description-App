package tesis.image_description_app.view

import android.app.Application
import android.speech.RecognitionListener
import tesis.image_description_app.model.SpeechRecognizer
import tesis.image_description_app.model.SpeechSynthesizerImpl
import tesis.image_description_app.network.ImageDescriptionLogic
import tesis.image_description_app.network.ImageDescriptionLogicImpl
import tesis.image_description_app.network.ImageInformationLogic
import tesis.image_description_app.network.ImageInformationLogicImpl

class MyApp: Application() {

    companion object {
        lateinit var imageInformationLogicImpl: ImageInformationLogic
        lateinit var imageDescriptionLogicImpl: ImageDescriptionLogic
        lateinit var speechSynthesizerImpl: SpeechSynthesizerImpl
    }

    override fun onCreate() {
        super.onCreate()
        speechSynthesizerImpl = SpeechSynthesizerImpl(this)
        imageInformationLogicImpl = ImageInformationLogicImpl()
        imageDescriptionLogicImpl = ImageDescriptionLogicImpl()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}