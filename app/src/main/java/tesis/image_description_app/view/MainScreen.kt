package tesis.image_description_app.view

import InvisibleButton
import NormalButton
import android.Manifest
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.MainViewModel
import tesis.image_description_app.R

@Composable
fun MainScreen(
    context: Context,
    mainViewModel: MainViewModel,
    cameraViewModel: CameraViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Log.e("MAIN SCREEN", "MAIN SCREEN RENDERIZA")

        if (cameraViewModel.hasImageDescriptionResult())
            mainViewModel.notifyEventToUser(cameraViewModel.getImgDescriptionResult())

        if (cameraViewModel.shouldShowCamera()) {
            OpenCamera(
                context,
                cameraViewModel,
                mainViewModel
            )
        }
        else
            if (cameraViewModel.shouldShowImage())
                ShowImage(imageBitmap = cameraViewModel.getBitmapImage()) {
                    mainViewModel.notifyEventToUser(context.getString(R.string.show_captured_image_error))
                }

        if (cameraViewModel.isProcessingImage()) {
            Text(text = "Procesando imagen...")
        }
        else {
            MainButton(
                mainViewModel,
                cameraViewModel.shouldShowCamera(),
                cameraViewModel.shouldShowImage(),
//                { cameraViewModel.openCamera() },
//                { cameraViewModel.activateTakePhotoCommand() })
            { mainViewModel.startListeningForCommandAction() },
            { mainViewModel.changeSpeechButtonState() })

        }
    }
}

@Composable
fun MainButton(
    mainViewModel: MainViewModel,
    shouldShowCamera: Boolean,
    shouldShowImage: Boolean,
    onClick1: () -> Unit,
    changeSpeechButtonState: () -> Unit,
) {
    if (mainViewModel.buttonPressed())
        MicPermissionHandler (
            mainViewModel = mainViewModel
        )
    else
        if (shouldShowCamera || shouldShowImage)
            InvisibleButton { changeSpeechButtonState() }
        else
            NormalButton { changeSpeechButtonState() }
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
            Text(text = stringResource(id = R.string.listening))
        }
        micPermissionState.status.shouldShowRationale -> {
            mainViewModel.notifyEventToUser(stringResource(id = R.string.mic_rationale_msg))
        }
        micPermissionState.isPermanentlyDenied() -> {
            mainViewModel.notifyEventToUser(stringResource(id = R.string.mic_permission_denied))
        }
    }
}








