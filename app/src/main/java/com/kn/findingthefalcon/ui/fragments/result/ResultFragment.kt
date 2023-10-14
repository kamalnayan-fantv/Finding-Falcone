package com.kn.findingthefalcon.ui.fragments.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kn.commons.utils.annotation.Status
import com.kn.findingthefalcon.R
import com.kn.findingthefalcon.databinding.FragmentResultBinding
import com.kn.ui.base.BaseFragment


class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {
    private var result: String? = null
    private val args by navArgs<ResultFragmentArgs>()
    override fun setViewModelToBinding() {

    }

    override fun initViews() {
        with(binding){
            when(args.status){
                Status.FAILURE->{
                    tvStatus.text= com.kn.ui.R.string.not_found.toStringFromResourceId()
                    lottie.setAnimation(R.raw.not_found)
                }
                Status.SUCCESS->{
                    tvStatus.text= com.kn.ui.R.string.format_found_on.toStringFromResourceId(args.planetName.orEmpty())
                    lottie.setAnimation(R.raw.found)
                }
            }
        }
    }

    override fun setData() {
    }

    override fun setListeners() {
    }

    override fun setObservers() {
    }

}