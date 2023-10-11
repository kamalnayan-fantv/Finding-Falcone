package com.kn.findingthefalcon.ui.activity

import android.os.Bundle
import com.kn.ui.base.BaseActivity
import com.kn.findingthefalcon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSwitchState()
        setListeners()
    }

    private fun setSwitchState() {
        binding?.themeSwitch?.apply{
            isChecked = isDarkThemeApplied
            text= getCurrentThemeText()
        }
    }

    private fun setListeners() {
      binding?.themeSwitch?.setOnClickListener {
          switchTheme()
          recreate()
      }
    }
}