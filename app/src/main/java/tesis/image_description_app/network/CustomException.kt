package tesis.image_description_app.network

class CustomException(message: String) : Exception(message) {

    override fun toString(): String {
        return message ?: super.toString()
    }

}