package tesis.image_description_app.app_module

import android.app.Application
import tesis.image_description_app.model.image_processing.ImageCaptureHandler
import tesis.image_description_app.model.image_processing.ImageRotator
import tesis.image_description_app.model.speech_mechanism.SpeechSynthesizerImpl
import tesis.image_description_app.data.network.image_description.ImageDescriptionLogic
import tesis.image_description_app.data.network.image_description.ImageDescriptionLogicImpl
import tesis.image_description_app.data.network.image_information.ImageInformationLogic
import tesis.image_description_app.data.network.image_information.ImageInformationLogicImpl

class Application: Application() {

    companion object {
        lateinit var imageInformationLogicImpl: ImageInformationLogic
        lateinit var imageDescriptionLogicImpl: ImageDescriptionLogic
        lateinit var speechSynthesizerImpl: SpeechSynthesizerImpl
        lateinit var imageRotator: ImageRotator
        lateinit var imageCaptureHandler: ImageCaptureHandler

    }

    override fun onCreate() {
        super.onCreate()
        speechSynthesizerImpl = SpeechSynthesizerImpl(this)
        imageInformationLogicImpl = ImageInformationLogicImpl()
        imageDescriptionLogicImpl = ImageDescriptionLogicImpl(this)
        imageRotator = ImageRotator()
        imageCaptureHandler = ImageCaptureHandler(imageRotator, this)
    }

    //TODO
    override fun onTerminate() {
        super.onTerminate()
    }
}