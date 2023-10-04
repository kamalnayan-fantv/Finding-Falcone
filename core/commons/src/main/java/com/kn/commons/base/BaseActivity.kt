package com.kn.commons.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
abstract class BaseActivity<VDB : ViewDataBinding>( private val bindingInflater: (LayoutInflater) -> VDB) :
    AppCompatActivity() {
    protected val TAG =this.javaClass.simpleName

    protected var binding: VDB?=null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = bindingInflater.invoke(layoutInflater)
        binding?.lifecycleOwner = this
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}