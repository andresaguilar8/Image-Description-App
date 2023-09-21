package tesis.image_description_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.ui.theme.ImageDescriptionAppTheme
import tesis.image_description_app.viewModel.*

class MainActivity : ComponentActivity() {

    private lateinit var imageInformationApiViewModel: ImageInformationApiViewModel
    private lateinit var imageDescriptionApiViewModel: ImageDescriptionApiViewModel
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var textToSpeechViewModel: TextToSpeechViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        textToSpeechViewModel = ViewModelProvider(this, TextToSpeechViewModelFactory(this))[TextToSpeechViewModel::class.java]
//        textToSpeechViewModel = TextToSpeechViewModel(this)
        imageDescriptionApiViewModel = ViewModelProvider(this, ImageDescriptionApiViewModelFactory(textToSpeechViewModel))[ImageDescriptionApiViewModel::class.java]
        imageInformationApiViewModel = ViewModelProvider(this, ImageInformationApiViewModelFactory(imageDescriptionApiViewModel))[ImageInformationApiViewModel::class.java]
        cameraViewModel = ViewModelProvider(this, CameraViewModelFactory(imageInformationApiViewModel, textToSpeechViewModel))[CameraViewModel::class.java]

        super.onCreate(savedInstanceState)
        setContent {
            ImageDescriptionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        cameraViewModel,
                        imageInformationApiViewModel,
                        textToSpeechViewModel
                        )
                }
            }
        }
    }

}


