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
import tesis.image_description_app.viewModel.ApiViewModel
import tesis.image_description_app.viewModel.CameraViewModel

class MainActivity : ComponentActivity() {

    private lateinit var apiViewModel: ApiViewModel
    private lateinit var cameraViewModel: CameraViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO ver si anda
        apiViewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        cameraViewModel = CameraViewModel(apiViewModel)

        super.onCreate(savedInstanceState)
        setContent {
            ImageDescriptionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(cameraViewModel, apiViewModel)
                }
            }
        }


    }

}


