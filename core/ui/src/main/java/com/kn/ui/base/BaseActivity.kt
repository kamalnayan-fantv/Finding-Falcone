package com.kn.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.WindowCompat
import androidx.databinding.ViewDataBinding
import com.kn.ui.R

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
abstract class BaseActivity<VDB : ViewDataBinding>(private val bindingInflater: (LayoutInflater) -> VDB) :
    AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    companion object {
        private const val PREF_NAME = "pref_app_theme"
        private const val KEY_THEME = "KEY_THEME"
        private const val DARK_TEXT = "Dark"
        private const val LIGHT_TEXT = "Light"
    }

    protected val isDarkThemeApplied:Boolean
        get(){
           return currentTheme==DARK_THEME
        }

    private var _binding: VDB? = null
    protected val binding
        get() = _binding

    private val DARK_THEME = Pair(R.style.Base_Theme_FindingFalcon_Dark, DARK_TEXT)
    private val LIGHT_THEME = Pair(R.style.Base_Theme_FindingFalcon_Light, LIGHT_TEXT)
    private var currentTheme = LIGHT_THEME

    override fun onCreate(savedInstanceState: Bundle?) {
        val isDarkTheme =
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_THEME, false)
        currentTheme = if (isDarkTheme) DARK_THEME else LIGHT_THEME
        super.onCreate(savedInstanceState)
        setTheme()
        _binding = bindingInflater.invoke(layoutInflater)
        _binding?.lifecycleOwner = this
        setContentView(_binding?.root)
    }

    private fun setTheme() {
        setTheme(currentTheme.first)

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            currentTheme == LIGHT_THEME
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected fun switchTheme() {
        currentTheme = when (currentTheme) {
            DARK_THEME -> LIGHT_THEME
            LIGHT_THEME -> DARK_THEME
            else -> DARK_THEME
        }
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
            putBoolean(KEY_THEME, currentTheme == DARK_THEME)
        }
    }

    protected fun getCurrentThemeText()= currentTheme.second

}