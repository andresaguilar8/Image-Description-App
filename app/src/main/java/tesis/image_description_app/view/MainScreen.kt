package tesis.image_description_app.view

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.MainViewModel
import tesis.image_description_app.viewModel.TextToSpeechViewModel


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    cameraViewModel: CameraViewModel,
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
                    .alpha(0.5f),
                shape = CircleShape,
                onClick = {
                    mainViewModel.onSpeechButtonPress(micPermissionState)
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








