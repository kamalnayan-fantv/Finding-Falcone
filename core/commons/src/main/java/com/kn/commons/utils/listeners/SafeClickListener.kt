package com.kn.commons.utils.listeners

import android.os.SystemClock
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation

/**
Created by Kamal Nayan on 05/09/22
 **/



/**
 * A Safe View Click Listener.
 *
 * This class used to handle view clicks safely with a time interval.
 *
 * @property interval the time interval.
 * @property onSafeClick
 */
class SafeClickListener(
    private val interval: Int = 1000,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClickTime: Long = 0L

    override fun onClick(v: View) {

        if (SystemClock.elapsedRealtime() - lastClickTime < interval) {
            return
        }

        lastClickTime = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }
}
