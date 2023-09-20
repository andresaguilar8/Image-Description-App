package tesis.image_description_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tesis.image_description_app.viewModel.CameraViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import tesis.image_description_app.viewModel.ImageInformationApiViewModel


@Preview
@Composable
fun MainScreenPreview() {
//    val imageInformationApiViewModel = ImageInformationApiViewModel()
 //   val cameraViewModel = CameraViewModel(imageInformationApiViewModel)
   // MainScreen(cameraViewModel = cameraViewModel, imageInformationApiViewModel = imageInformationApiViewModel)
    //TODO importar una imagen de prueba
    /*Image(
        painter  = painterResource(id = R.drawable.dow),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )*/
}

@Composable
fun MainScreen(cameraViewModel: CameraViewModel, imageInformationApiViewModel: ImageInformationApiViewModel) {
    var textButton by remember { mutableStateOf("Abrir cámara") }

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (!imageInformationApiViewModel.isFetchingApi() && !cameraViewModel.isProcessingImage()) {
            Button(onClick = {
                cameraViewModel.changeCameraState()
                imageInformationApiViewModel.cleanApiResponse()
            }) {
                Text(text = textButton)
            }
        }

        if (imageInformationApiViewModel.apiResponse != "") {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                ) {
                        Text(
                            imageInformationApiViewModel.apiResponse,
                        )
                }
            }
        }

        if (imageInformationApiViewModel.isFetchingApi())
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




