package tesis.image_description_app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tesis.image_description_app.data.network.ImageDescriptionLogic
import tesis.image_description_app.data.network.ImageInformationLogic

class ImageDescriptionViewModel(
    private val imageInformationLogicImpl: ImageInformationLogic,
    private val imageDescriptionLogicImpl: ImageDescriptionLogic
) : ViewModel() {

    private var imageDescription by mutableStateOf("")
    private var hasImageDescription = mutableStateOf(false)
    private var errorMessage by mutableStateOf("")
    private var errorGeneratingImgDescription = mutableStateOf(false)
    private var provideImageDescription by mutableStateOf(false)
    private var processingImage = mutableStateOf(false)

    fun fetchForImageDescription(encodedImage: String) {
        viewModelScope.launch {
            runCatching {
                val imageInformationResponse = imageInformationLogicImpl.getImageInformation(encodedImage)
                val imageDescriptionResponse = imageDescriptionLogicImpl.getImageDescription(imageInformationResponse.getOrThrow())
                imageDescription = imageDescriptionResponse.getOrThrow()
                hasImageDescription.value = true
                provideImageDescription = true
                if (hasImageDescriptionError())
                    errorGeneratingImgDescription.value = false
                processingImage.value = false
            }.onFailure { throwable ->
                errorMessage = throwable.toString()
                errorGeneratingImgDescription.value = true
                processingImage.value = false
            }
        }
    }

    fun isProcessingImage(): Boolean {
        return this.processingImage.value
    }

    fun setProcessingImage() {
        this.processingImage.value = true
    }

    fun getImgDescription(): String {
        return this.imageDescription
    }

    fun hasImageDescription(): Boolean {
        return this.hasImageDescription.value
    }

    fun hasImageDescriptionError(): Boolean {
        return this.errorGeneratingImgDescription.value
    }

    fun disableImgDescriptionProvide() {
        this.provideImageDescription = false
    }

    fun provideImgDescription() {
        this.provideImageDescription = true
    }

    fun provideImgDescriptionIsEnabled(): Boolean {
        return this.provideImageDescription
    }

    fun getError(): String {
        return this.errorMessage
    }

}