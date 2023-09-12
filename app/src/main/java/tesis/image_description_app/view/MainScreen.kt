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
import tesis.image_description_app.viewModel.ApiRequestViewModel

private var cameraHandler: CameraHandler = CameraHandler()
private var cameraViewModel: CameraViewModel = CameraViewModel()
private var apiRequestViewModel: ApiRequestViewModel = ApiRequestViewModel(cameraViewModel)
@Composable
fun MainScreen(cameraViewModel: CameraViewModel = viewModel(), apiRequestViewModel: ApiRequestViewModel = viewModel()) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    var textButton by remember { mutableStateOf("Abrir cámara") }
    val imageHandler = ImageHandler(cameraViewModel)

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

        if (cameraViewModel.shouldShowPhoto()) {
            Log.e("Muestra foto", "entra a mostrar foto")
            cameraViewModel.imageBitmap?.let {
                Log.e("Muestra foto", "$it")
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            cameraViewModel.closeCamera()
            apiRequestViewModel.requestImageInfo(imageHandler.getBase64Image())
            textButton = "Abrir cámara"
        }
        else {
            if (cameraViewModel.cameraOpened) {
                OpenCamera(cameraViewModel, previewView, imageHandler)
                textButton = "Cerrar cámara"
            } else {
                //TODO: CloseCamera()
                textButton = "Abrir cámara"
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
            viewModel = viewModel,
            cameraHandler = cameraHandler,
            previewView = previewView,
        ) { Log.e("ERROR", "Composable view error:", it) }
    }

    //TODO: diferentes casos
}


