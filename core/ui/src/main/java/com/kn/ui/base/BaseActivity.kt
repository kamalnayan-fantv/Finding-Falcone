package com.kn.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.kn.ui.R

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
abstract class BaseActivity<VDB : ViewDataBinding>(private val bindingInflater: (LayoutInflater) -> VDB) :
    AppCompatActivity() {
    protected val TAG =this.javaClass.simpleName

    private var _binding: VDB?=null
    protected val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Base_Theme_FindingFalcon_Dark)
        _binding = bindingInflater.invoke(layoutInflater)
        _binding?.lifecycleOwner = this
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}