package tesis.image_description_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    var cameraOpened by mutableStateOf(false)
        private set

    fun changeCameraState() {
        cameraOpened = !cameraOpened
        Log.i("cameraOpened", "ahora cameraOpened es $cameraOpened")

    }
}