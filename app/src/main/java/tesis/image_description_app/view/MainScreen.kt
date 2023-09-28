package tesis.image_description_app.view

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.ImageInformationApiViewModel
import tesis.image_description_app.viewModel.MainViewModel
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


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    cameraViewModel: CameraViewModel,
    //TODO dejo el parametro de viewModel image info para testear, pero no va a ir
//    imageInformationApiViewModel: ImageInformationApiViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {

    var micPermissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    SideEffect {
        //TODO
        micPermissionState.launchPermissionRequest()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        if (cameraViewModel.shouldShowCamera()) {
            OpenCamera(
                mainViewModel,
                cameraViewModel,
                textToSpeechViewModel
            )
        }
        else
            if (cameraViewModel.shouldShowImage())
                ShowImage(cameraViewModel.getBitmapImage())
        if (cameraViewModel.isProcessingImage()) {
            Text(text = "processing image")
        }
        else {
            MainButton(
                mainViewModel,
                cameraViewModel,
                micPermissionState
            )
        }
        //showImageInformation(imageInformationApiViewModel)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainButton(
    mainViewModel: MainViewModel,
    cameraViewModel: CameraViewModel,
    micPermissionState: PermissionState
) {
    if (!mainViewModel.speechButtonIsPressed()) {
        if (cameraViewModel.shouldShowCamera() || cameraViewModel.shouldShowImage()) {
            Button(
                modifier =  Modifier
                    .size(200.dp)
                    .alpha(0.5f), // Set alpha to 0 to make it invisible
                shape = CircleShape,
                onClick = {
                    mainViewModel.onSpeechButtonPress(micPermissionState)
                    // imageInformationApiViewModel.cleanApiResponse()
                }
            ) {
                Text(
                    text = "textButton",
                    modifier = Modifier.alpha(0f)
                )

            }
        }
        else {
            Button(
                modifier =  Modifier
                    .size(200.dp),
                shape = CircleShape,
                onClick = {
                     mainViewModel.onSpeechButtonPress(micPermissionState)

                }
            ) {
                Text(text = "textButton")
            }
        }
    }
    else {
        Text("Listening...")
    }
}

/*
if (!cameraViewModel.shouldShowCamera())
                        cameraViewModel.openCamera()
                    else
                        cameraViewModel.closeCamera()
 */
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






