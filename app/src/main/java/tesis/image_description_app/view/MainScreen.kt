package tesis.image_description_app.view

import android.annotation.SuppressLint
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
import tesis.image_description_app.viewModel.TextToSpeechViewModel


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


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    cameraViewModel: CameraViewModel,
    //TODO dejo el parametro de viewModel image info para testear, pero no va a ir
//    imageInformationApiViewModel: ImageInformationApiViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {

    var cameraButtonText by remember { mutableStateOf("Abrir cámara") }

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CameraButton(cameraViewModel, cameraButtonText)

        //showImageInformation(imageInformationApiViewModel)

        cameraButtonText = if (cameraViewModel.shouldShowImage()) {
            ShowImage(cameraViewModel.imageBitmap)
            "Abrir cámara"
        } else {
            if (cameraViewModel.shouldShowCamera()) {
                OpenCamera(cameraViewModel, textToSpeechViewModel)
                "Cerrar cámara"
            } else {
                "Abrir cámara"
            }
        }
    }
}

@Composable
fun CameraButton(cameraViewModel: CameraViewModel, textButton: String) {
    if (!cameraViewModel.processingImage) {
        Button(onClick = {
            //TODO: contentdescrip
            cameraViewModel.changeCameraState()
            // imageInformationApiViewModel.cleanApiResponse()
        }) {
            Text(text = textButton)
        }
    }
}

@Composable
fun showImageInformation(imageInformationApiViewModel: ImageInformationApiViewModel) {
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
}






