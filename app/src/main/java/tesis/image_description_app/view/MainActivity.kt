package tesis.image_description_app.view

import android.os.Bundle
import android.speech.RecognitionListener
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.model.RecognitionListenerImpl
import tesis.image_description_app.model.SpeechRecognizer
import tesis.image_description_app.model.SpeechSynthesizerImpl
import tesis.image_description_app.network.ImageDescriptionLogic
import tesis.image_description_app.network.ImageDescriptionLogicImpl
import tesis.image_description_app.network.ImageInformationLogic
import tesis.image_description_app.network.ImageInformationLogicImpl
import tesis.image_description_app.ui.theme.ImageDescriptionAppTheme
import tesis.image_description_app.viewModel.*

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var imageInformationApiViewModel: ImageInformationApiViewModel
    private lateinit var imageDescriptionApiViewModel: ImageDescriptionApiViewModel
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var textToSpeechViewModel: TextToSpeechViewModel

    //TODO capaz van a ir en una clase Application
    private lateinit var imageInformationLogicImpl: ImageInformationLogic
    private lateinit var imageDescriptionLogicImpl: ImageDescriptionLogic
    private lateinit var speechSynthesizerImpl: SpeechSynthesizerImpl
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener


    override fun onCreate(savedInstanceState: Bundle?) {
        speechSynthesizerImpl = SpeechSynthesizerImpl(this)
        imageInformationLogicImpl = ImageInformationLogicImpl()
        imageDescriptionLogicImpl = ImageDescriptionLogicImpl()

        textToSpeechViewModel = ViewModelProvider(this, TextToSpeechViewModelFactory(speechSynthesizerImpl))[TextToSpeechViewModel::class.java]
        imageDescriptionApiViewModel = ViewModelProvider(this, ImageDescriptionApiViewModelFactory(textToSpeechViewModel, imageDescriptionLogicImpl))[ImageDescriptionApiViewModel::class.java]
        imageInformationApiViewModel = ViewModelProvider(this, ImageInformationApiViewModelFactory(imageDescriptionApiViewModel, imageInformationLogicImpl))[ImageInformationApiViewModel::class.java]
        cameraViewModel = ViewModelProvider(this, CameraViewModelFactory(imageInformationApiViewModel, textToSpeechViewModel))[CameraViewModel::class.java]
        mainViewModel = MainViewModel(cameraViewModel, textToSpeechViewModel)

        recognitionListener = RecognitionListenerImpl(mainViewModel, textToSpeechViewModel)
        speechRecognizer = SpeechRecognizer(this, recognitionListener)

        mainViewModel.setSpeechRecognizer(speechRecognizer)

        super.onCreate(savedInstanceState)
        setContent {
            ImageDescriptionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        mainViewModel,
                        cameraViewModel,
                        textToSpeechViewModel
                    )
                }
            }
        }
    }

    //TODO
    override fun onDestroy() {
        super.onDestroy()
        textToSpeechViewModel.releaseSpeech()
    }

    override fun onStop() {
        super.onStop()
        Log.e("onStop", "se ejecuta onStop")
    }
}


