package tesis.image_description_app.view

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import tesis.image_description_app.viewModel.CameraViewModel
import tesis.image_description_app.viewModel.TextToSpeechViewModel
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OpenCamera(
    cameraViewModel: CameraViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(true) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (cameraPermissionState.status.isGranted) {
        CameraView(
            executor = Executors.newSingleThreadExecutor(),
            onImageCaptured = cameraViewModel::onImageCapture,
            cameraViewModel = cameraViewModel,
            textToSpeechViewModel = textToSpeechViewModel
        ) { Log.e("ERROR", "Composable view error:", it) }
    }

    //TODO: diferentes casos
}

@Composable
fun CameraView(
    executor: Executor,
    onImageCaptured: (ByteBuffer) -> Unit,
    cameraViewModel: CameraViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    onError: (ImageCaptureException) -> Unit

) {

    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val preview = Preview.Builder()
        .build()
    preview.setSurfaceProvider(previewView.surfaceProvider)

    //TODO ver donde hacer el unbind dps de cerrar camara

    LaunchedEffect(true) {

        val cameraProvider = context.getCameraProvider()
        cameraProvider!!.unbindAll()
        cameraProvider!!.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        textToSpeechViewModel.speak("La cámara está abierta.")
    }

    Box(contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            IconButton(modifier = Modifier.padding(bottom = 80.dp),
                onClick = {
                    cameraViewModel.takePhoto(
                        imageCapture = imageCapture,
                        executor = executor,
                        onImageCaptured = onImageCaptured,
                        onError = onError
                    )
                },
                content = {
                    Icon(
                        imageVector = Icons.Sharp.Lens,
                        contentDescription = "Tomar imagen",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(150.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            )
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}



