package com.fgdc.marvelcharacters.utils.extensions

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.fgdc.marvelcharacters.R

fun ImageView.squaredListLoad(url: String, context: Context) {
    this.load(
        url,
        ImageLoader.Builder(context).componentRegistry {
            add(SvgDecoder(context))
        }.build()
    ) {
        placeholder(R.drawable.placeholder_list_image)
        error(R.drawable.placeholder_list_image)
        transformations(RoundedCornersTransformation(50f))
    }
}

fun ImageView.simpleLoad(url: String, context: Context) {
    this.load(
        url,
        ImageLoader.Builder(context).componentRegistry {
            add(SvgDecoder(context))
        }.build()
    )
}
