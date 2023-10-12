package tesis.image_description_app.view

import android.os.Bundle
import android.speech.RecognitionListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.model.RecognitionListenerImpl
import tesis.image_description_app.model.SpeechRecognizer
import tesis.image_description_app.ui.theme.ImageDescriptionAppTheme
import tesis.image_description_app.viewModel.*

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cameraViewModel: CameraViewModel
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
                        cameraViewModel
                    )
                }
            }
        }
    }

    private fun initializeViewModels() {
        cameraViewModel = ViewModelProvider(this, CameraViewModelFactory(MyApp.imageInformationLogicImpl, MyApp.imageDescriptionLogicImpl))[CameraViewModel::class.java]
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(cameraViewModel, MyApp.speechSynthesizerImpl))[MainViewModel::class.java]
        this.setViewModelsToCaptureHandler()
    }

    private fun setViewModelsToCaptureHandler() {
        cameraViewModel.setImageCaptureHandler(MyApp.imageCaptureHandler)
        MyApp.imageCaptureHandler.setViewModels(mainViewModel, cameraViewModel)
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

    //TODO
    override fun onDestroy() {
        super.onDestroy()
//        mainViewModel.releaseSpeech()
    }

    override fun onStop() {
        //textToSpeechViewModel.releaseSpeech()
        super.onStop()
    }

}


