package tesis.image_description_app.app_module

import android.os.Bundle
import android.speech.RecognitionListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.model.speech_mechanism.RecognitionListenerImpl
import tesis.image_description_app.model.speech_mechanism.SpeechRecognizer
import tesis.image_description_app.view.MainScreen
import tesis.image_description_app.view.showInitError
import tesis.image_description_app.view.ui.theme.ImageDescriptionAppTheme
import tesis.image_description_app.view_model.*

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var imageDescriptionViewModel: ImageDescriptionViewModel
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModels()
        setupSpeechRecognition()
        setContent {
            ImageDescriptionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        this,
                        mainViewModel,
                        cameraViewModel,
                        imageDescriptionViewModel
                    )
                }
            }
        }
    }

    private fun initializeViewModels() {
//        imageDescriptionViewModel = ImageDescriptionViewModel(
//            Application.imageInformationLogicImpl,
//            Application.imageDescriptionLogicImpl
//        )
        imageDescriptionViewModel = ViewModelProvider(this, ImageDescriptionViewModelFactory(Application.imageInformationLogicImpl, Application.imageDescriptionLogicImpl))[ImageDescriptionViewModel::class.java]
        cameraViewModel = ViewModelProvider(this, CameraViewModelFactory(imageDescriptionViewModel))[CameraViewModel::class.java]
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(cameraViewModel, imageDescriptionViewModel, Application.speechSynthesizerImpl))[MainViewModel::class.java]

        this.setViewModelsToCaptureHandler()
    }

    private fun setViewModelsToCaptureHandler() {
        cameraViewModel.setImageCaptureHandler(Application.imageCaptureHandler)
        Application.imageCaptureHandler.setViewModels(mainViewModel, cameraViewModel, imageDescriptionViewModel)
    }

    private fun setupSpeechRecognition() {
        recognitionListener = RecognitionListenerImpl(mainViewModel, this)
        try {
            speechRecognizer = SpeechRecognizer(this, recognitionListener)
            mainViewModel.setSpeechRecognizer(speechRecognizer)
        }
        catch (exception: Exception) {
            exception.message?.let { showInitError(this, it) }
        }
    }

}


