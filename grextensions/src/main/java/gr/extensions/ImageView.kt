package gr.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes

fun ImageView.tint(@ColorRes colorId: Int) = setColorFilter(color(colorId))