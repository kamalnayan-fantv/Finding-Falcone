package com.kn.commons.utils.extensions

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.kn.commons.utils.listeners.SafeClickListener

/** @Author Kamal Nayan
Created on: 06/10/23
 **/
const val DEFAULT_ANIMATION_DURATION = 100L

@SuppressLint("ClickableViewAccessibility")
fun View.setSafeClickListener(
 interval: Int = 1000,
 scaleX: Float = 0.97f,
 scaleY: Float = 0.97f,
 onClicked: (View) -> Unit
) {
 this.setOnClickListener(SafeClickListener(interval) {
  onClicked.invoke(it)
 })

 this.setOnTouchListener { v, event ->
  if (event.action == MotionEvent.ACTION_DOWN) {
   v.showTouchAnimation(scaleX, scaleY)
  } else if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
   v.showTouchReleaseAnimation()
  }
  return@setOnTouchListener false
 }
}


fun View.showTouchAnimation(scaleX: Float = 0.97f, scaleY: Float = 0.97f) {
 this.animate().scaleX(scaleX).setDuration(DEFAULT_ANIMATION_DURATION)
  .start()
 this.animate().scaleY(scaleY).setDuration(DEFAULT_ANIMATION_DURATION)
  .start()
}

fun View.showTouchReleaseAnimation() {
 this.animate().scaleX(1f).setDuration(DEFAULT_ANIMATION_DURATION).start()
 this.animate().scaleY(1f).setDuration(DEFAULT_ANIMATION_DURATION).start()
}

