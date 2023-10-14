package com.kn.commons.utils.extensions

import android.content.Context
import android.widget.Toast

/** @Author Kamal Nayan
Created on: 14/10/23
 **/

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}