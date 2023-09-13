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

class MainActivity : ComponentActivity() {

    //val viewModel by viewModels<ViewModel>()
    private lateinit var apiRequestViewModel: ApiViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO ver si anda
        apiRequestViewModel = ViewModelProvider(this)[ApiViewModel::class.java]

        super.onCreate(savedInstanceState)
        setContent {
            ImageDescriptionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(apiRequestViewModel)
                }
            }
        }
    }
}
