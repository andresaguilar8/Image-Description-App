package tesis.image_description_app.view

import android.Manifest
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import tesis.image_description_app.viewModel.MainViewModel
import tesis.image_description_app.model.ImageHandler
import java.util.concurrent.Executors
import androidx.compose.ui.platform.LocalContext
import tesis.image_description_app.model.CameraHandler

private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(true)
private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
private val imageHandler: ImageHandler = ImageHandler()
private var cameraHandler: CameraHandler = CameraHandler()

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    var textButton by remember { mutableStateOf("Abrir cámara") }
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            viewModel.changeCameraState()
        }) {
            Text(text = textButton)
        }

        if (viewModel.cameraOpened) {
            OpenCamera(viewModel, previewView)
            textButton = "Cerrar cámara"
        } else {
            //TODO: CloseCamera()
            textButton = "Abrir cámara"
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OpenCamera(viewModel: MainViewModel, previewView: PreviewView) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(true) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (cameraPermissionState.status.isGranted) {
        CameraView(
            executor = Executors.newSingleThreadExecutor(),
            onImageCaptured = imageHandler::handleImageCapture,
            viewModel = viewModel,
            cameraHandler = cameraHandler,
            previewView = previewView,
        ) { Log.e("ERROR", "Composable view error:", it) }
    }

    //TODO: diferentes casos
}

