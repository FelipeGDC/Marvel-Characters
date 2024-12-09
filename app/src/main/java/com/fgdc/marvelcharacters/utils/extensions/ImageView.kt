package com.fgdc.marvelcharacters.utils.extensions

import android.content.Context
import android.widget.ImageView
import coil3.ImageLoader
import coil3.load
import coil3.request.placeholder
import coil3.request.transformations
import coil3.svg.SvgDecoder
import coil3.transform.RoundedCornersTransformation
import com.fgdc.marvelcharacters.R

fun ImageView.squaredListLoad(url: String, context: Context) {
    this.load(
        url,
        ImageLoader.Builder(context).components {
            add(SvgDecoder.Factory())
        }.build()
    ) {
        placeholder(R.drawable.placeholder_list_image)
        transformations(RoundedCornersTransformation(50f))
        // error(R.drawable.placeholder_list_image)
    }
}

fun ImageView.simpleLoad(url: String, context: Context) {
    this.load(
        url,
        ImageLoader.Builder(context).components {
            add(SvgDecoder.Factory())
        }.build()
    )
}
