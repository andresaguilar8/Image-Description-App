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
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.model.ImageHandler
import java.util.concurrent.Executors
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import tesis.image_description_app.model.CameraHandler
import tesis.image_description_app.viewModel.ApiViewModel

private var cameraHandler: CameraHandler = CameraHandler()

@Composable
fun MainScreen(apiRequestViewModel: ApiViewModel, cameraViewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    var textButton by remember { mutableStateOf("Abrir cámara") }
    val imageHandler = ImageHandler(cameraViewModel, apiRequestViewModel)

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            cameraViewModel.changeCameraState()
        }) {
            Text(text = textButton)
        }
        if (cameraViewModel.shouldShowImage()) {
            Log.e("Muestra foto", "entra a mostrar foto")
            cameraViewModel.imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                //apiRequestViewModel.requestImageInfo()
                cameraViewModel.closeCamera()
                textButton = "Abrir cámara"
            }
        }
        else {
            textButton = if (cameraViewModel.shouldShowCamera()) {
                OpenCamera(cameraViewModel, previewView, imageHandler)
                "Cerrar cámara"
            } else {
                //TODO: CloseCamera()
                "Abrir cámara"
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OpenCamera(viewModel: CameraViewModel, previewView: PreviewView, imageHandler: ImageHandler) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(true) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (cameraPermissionState.status.isGranted) {
        CameraView(
            executor = Executors.newSingleThreadExecutor(),
            onImageCaptured = imageHandler::handleImageCapture,
            cameraViewModel = viewModel,
            cameraHandler = cameraHandler,
        ) { Log.e("ERROR", "Composable view error:", it) }
    }

    //TODO: diferentes casos
}


