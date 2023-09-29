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
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.MainViewModel
import tesis.image_description_app.viewModel.TextToSpeechViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    cameraViewModel: CameraViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {
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
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainButton(
    mainViewModel: MainViewModel,
    cameraViewModel: CameraViewModel,
) {
    if (mainViewModel.buttonPressed()) {
        MicPermissionHandler(mainViewModel)
    }
    else {
        if (cameraViewModel.shouldShowCamera() || cameraViewModel.shouldShowImage()) {
            InvisibleButton(
                mainViewModel
            )
        }
        else
            NormalButton(
                mainViewModel
            )
    }
}

@Composable
fun InvisibleButton(mainViewModel: MainViewModel) {
    Button(
        modifier = Modifier
            .size(200.dp)
            .alpha(0.5f),
        shape = CircleShape,
        onClick = {
            mainViewModel.changeSpeechButtonState()
        }
    ) {
        Text(
            text = "textButton",
            modifier = Modifier.alpha(0f)
        )
    }
}

@Composable
fun NormalButton(mainViewModel: MainViewModel) {
    Button(
        modifier = Modifier
            .size(200.dp),
        shape = CircleShape,
        onClick = {
            mainViewModel.changeSpeechButtonState()
        }
    ) {
        Text(text = "textButton")
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MicPermissionHandler(
    mainViewModel: MainViewModel,
) {
    var micPermissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    LaunchedEffect(true) {
        micPermissionState.launchPermissionRequest()
    }

    //TODO testear casos
    when {
        micPermissionState.status.isGranted -> {
            mainViewModel.startListeningForCommandAction()
            Text("Listening...")
        }
        micPermissionState.status.shouldShowRationale -> {
            //textToSpeechViewModel.speak("Aca iria el rationale")
        }
        micPermissionState.isPermanentlyDenied() -> {
            //textToSpeechViewModel.speak("Has denegado el permiso para utilizar la cámara. Por favor, para conceder el permiso, debes ir configuraciones..")
        }
    }
}








