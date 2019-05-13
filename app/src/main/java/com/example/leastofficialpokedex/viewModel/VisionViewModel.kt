package com.example.leastofficialpokedex.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.leastofficialpokedex.R
import com.google.cloud.vision.v1.ImageAnnotatorClient

class VisionViewModel(val context: Context): ViewModel() {

    fun visionTest() {
        val vision = ImageAnnotatorClient.create()
        val image = context.getDrawable(R.drawable.pokeball)
    }
}