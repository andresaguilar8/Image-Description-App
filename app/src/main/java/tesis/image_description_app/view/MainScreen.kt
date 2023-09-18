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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import tesis.image_description_app.viewModel.ApiViewModel
import androidx.compose.ui.res.painterResource


@Preview
@Composable
fun MainScreenPreview() {
    val apiViewModel = ApiViewModel()
    val cameraViewModel = CameraViewModel(apiViewModel)
    MainScreen(cameraViewModel = cameraViewModel, apiViewModel = apiViewModel)
    //TODO importar una imagen de prueba
    /*Image(
        painter  = painterResource(id = R.drawable.dow),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )*/
}

@Composable
fun MainScreen(cameraViewModel: CameraViewModel, apiViewModel: ApiViewModel) {
    var textButton by remember { mutableStateOf("Abrir cámara") }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (!apiViewModel.isFetchingApi() && !cameraViewModel.isProcessingImage()) {
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

        if (cameraViewModel.isProcessingImage())
            Text("Procesando imagen...")


        if (cameraViewModel.shouldShowImage()) {
            ShowImage(cameraViewModel.imageBitmap)
            cameraViewModel.closeCamera()
            textButton = "Abrir cámara"

        }
        else {
            textButton = if (cameraViewModel.shouldShowCamera()) {
                OpenCamera(cameraViewModel)
                "Cerrar cámara"
            } else {
                "Abrir cámara"
            }
        }
    }
}

@Composable
fun ShowImage(imageBitmap: ImageBitmap?) {
    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}




