package tesis.image_description_app.view

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import tesis.image_description_app.viewModel.CameraViewModel
import java.util.concurrent.Executors
import androidx.compose.foundation.Image
import tesis.image_description_app.viewModel.ApiViewModel

@Composable
fun MainScreen(cameraViewModel: CameraViewModel, apiViewModel: ApiViewModel) {
    var textButton by remember { mutableStateOf("Abrir cámara") }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        if (!apiViewModel.isFetchingApi() && !cameraViewModel.processingImage) {
            Button(onClick = {
                cameraViewModel.changeCameraState()
                apiViewModel.cleanApiResponse()
            }) {
                Text(text = textButton)
            }
        }

        if (apiViewModel.apiResponse != "") {
            Text(apiViewModel.apiResponse)
        }

        if (apiViewModel.isFetchingApi())
            Text("Fetching api...")

        if (cameraViewModel.processingImage())
            Text("Procesando imagen...")

        if (cameraViewModel.shouldShowImage()) {
            cameraViewModel.imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                cameraViewModel.closeCamera()
                textButton = "Abrir cámara"
            }
        }
        else {
            textButton = if (cameraViewModel.shouldShowCamera()) {
                //TODO cameraViewModel.openCamera
                OpenCamera(cameraViewModel)
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
fun OpenCamera(cameraViewModel: CameraViewModel) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(true) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (cameraPermissionState.status.isGranted) {
        CameraView(
            executor = Executors.newSingleThreadExecutor(),
            onImageCaptured = cameraViewModel::onImageCapture,
            cameraViewModel = cameraViewModel,
        ) { Log.e("ERROR", "Composable view error:", it) }
    }

    //TODO: diferentes casos
}


