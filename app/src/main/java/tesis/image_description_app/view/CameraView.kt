package tesis.image_description_app.view

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import tesis.image_description_app.R
import tesis.image_description_app.view_model.CameraViewModel
import tesis.image_description_app.view_model.MainViewModel
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OpenCamera(
    context: Context,
    cameraViewModel: CameraViewModel,
    mainViewModel: MainViewModel
) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(true) {
        cameraPermissionState.launchPermissionRequest()
    }
    //TODO testear casos
    when {
        cameraPermissionState.status.isGranted -> {
            CameraView(
                executor = Executors.newSingleThreadExecutor(),
                onImageCaptured = cameraViewModel::onImageCapture,
                cameraViewModel = cameraViewModel,
                mainViewModel = mainViewModel
            ) {
                mainViewModel.notifyEventToUser(context.getString(R.string.image_captured_error))
            }
        }
        cameraPermissionState.status.shouldShowRationale -> {
            mainViewModel.notifyEventToUser(context.getString(R.string.camera_rationale_msg))
        }
        cameraPermissionState.isPermanentlyDenied() -> {
            mainViewModel.notifyEventToUser(context.getString(R.string.camera_permission_denied))
        }
    }

}

@Composable
fun CameraView(
    executor: Executor,
    onImageCaptured: (ImageProxy) -> Unit,
    cameraViewModel: CameraViewModel,
    mainViewModel: MainViewModel,
    onError: (ImageCaptureException) -> Unit
) {

    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val preview = Preview.Builder()
        .setTargetAspectRatio(AspectRatio.RATIO_16_9).build()

    lateinit var cameraProvider: ProcessCameraProvider
    Log.d("CameraView", "Surface created")

    //This line below generates the error
    preview.setSurfaceProvider(previewView.surfaceProvider)
    //TODO ver donde hacer el unbind dps de cerrar camara

    LaunchedEffect(true) {

        cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d("CameraView", "Surface disposed")
            preview.setSurfaceProvider(null)
            cameraProvider?.unbindAll()
        }
    }

    mainViewModel.notifyEventToUser(stringResource(id = R.string.camera_opened))

    Box(contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            TakeImage(
                cameraViewModel,
                imageCapture = imageCapture,
                executor = executor,
                onImageCaptured = onImageCaptured,
                onError = onError
            )
        }
}

@Composable
fun TakeImage(
    cameraViewModel: CameraViewModel,
    imageCapture: ImageCapture,
    executor: Executor,
    onImageCaptured: (ImageProxy) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    if (cameraViewModel.captureImageCommandActivated()) {
        cameraViewModel.takePhoto(
            imageCapture = imageCapture,
            executor = executor,
            onImageCaptured = onImageCaptured,
            onError = onError
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



