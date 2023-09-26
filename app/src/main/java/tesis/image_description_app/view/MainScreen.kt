package tesis.image_description_app.view

import android.annotation.SuppressLint
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tesis.image_description_app.viewModel.ImageDescriptionApiViewModel
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
    imageInformationApiViewModel: ImageInformationApiViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {

    var textButton by remember { mutableStateOf("Abrir cámara") }

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        println(cameraViewModel.isProcessingImage())

        if (!cameraViewModel.isProcessingImage()) {
            Button(onClick = {
                //TODO: contentdescrip
                cameraViewModel.changeCameraState()
                imageInformationApiViewModel.cleanApiResponse()
            }) {
                Text(text = textButton)
            }
        }
        else {
            Text("rotando imagen")
        }

        //showImageInformation(imageInformationApiViewModel)

        textButton = if (cameraViewModel.shouldShowImage()) {
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




