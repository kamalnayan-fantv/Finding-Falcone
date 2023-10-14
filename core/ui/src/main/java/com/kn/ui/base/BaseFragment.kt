package com.kn.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis
import com.kn.commons.utils.view.autoCleared

/** @Author: Kamal Nayan
 * @since: 11/07/23 at 4:07 pm */

abstract class BaseFragment<VDB : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater) -> VDB
) : Fragment() {

    protected val TAG = this.javaClass.simpleName
    protected var binding by autoCleared<VDB>()


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingInflater.invoke(inflater).also {
        binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            /* forward= */ true
        ).apply {
            duration = 200
        }
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            /* forward= */ false
        ).apply {
            duration = 200
        }
        binding.lifecycleOwner = viewLifecycleOwner

        initViews()
        setViewModelToBinding()
        setData()
        setListeners()
        setObservers()
    }

    /**
     * Used to set view model to view binding
     */
    abstract fun setViewModelToBinding()

    abstract fun initViews()

    abstract fun setData()

    abstract fun setListeners()

    abstract fun setObservers()

    protected fun runOnUiThread(func: () -> Unit) {
        if (!this.isDetached && this.isAdded) {
            activity?.runOnUiThread {
                func.invoke()
            }
        }
    }

    protected fun Int.toStringFromResourceId(vararg  formatArgs:Any): String =
        if (formatArgs.isNullOrEmpty())
            context?.resources?.getString(this) ?: ""
        else
            context?.resources?.getString(this, *formatArgs) ?: ""

    protected fun Int.toColor() = ContextCompat.getColor(requireContext(), this)
}