package tesis.image_description_app.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tesis.image_description_app.data.network.image_description.ImageDescriptionLogic
import tesis.image_description_app.data.network.image_information.ImageInformationLogic

class ImageDescriptionViewModelFactory(
    private val imageInformationLogicImpl: ImageInformationLogic,
    private val imageDescriptionLogicImpl: ImageDescriptionLogic
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ImageDescriptionViewModel::class.java) {
            return ImageDescriptionViewModel(imageInformationLogicImpl, imageDescriptionLogicImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}