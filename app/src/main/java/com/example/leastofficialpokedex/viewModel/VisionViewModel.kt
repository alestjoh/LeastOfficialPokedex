package com.example.leastofficialpokedex.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leastofficialpokedex.R
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.protobuf.ByteString
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder

class VisionViewModel: ViewModel() {

    private val myMessage = MutableLiveData<String>()
    val message: LiveData<String>
        get() = myMessage

    fun visionTest(context: Context) {
        val vision = ImageAnnotatorClient.create()
        val drawable = context.getDrawable(R.drawable.pokeball)
        //val bytes = ByteString.copyFrom(Files.readAllBytes(Paths.get("./res/pokeball.png")))
        val bitmap = (drawable as BitmapDrawable).bitmap
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
        val bytes = byteStream.toByteArray()
        val byteString = ByteString.copyFrom(bytes)

        val requests = ArrayList<AnnotateImageRequest>()
        val image = Image.newBuilder().setContent(byteString).build()
        val feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build()
        val request = AnnotateImageRequest.newBuilder()
            .addFeatures(feature)
            .setImage(image)
            .build()
        requests.add(request)

        val response = vision.batchAnnotateImages(requests)
        val responses = response.responsesList

        val sb = StringBuilder()
        for (res in responses) {
            if (res.hasError()) {
                sb.append("Error: ${res.error.message}\n")
                myMessage.postValue(sb.toString())
                return
            }

            for (annotation in res.labelAnnotationsList) {
                annotation.allFields.forEach { (t, u) ->
                    sb.append("$t : $u\n")
                }
            }
        }

        myMessage.postValue(sb.toString())
    }
}