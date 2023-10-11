package com.kn.findingthefalcon.extensions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
fun Fragment.navigateToDestination(
    destinationId: Int,
    actionId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    if (isAdded) {
        if (this.findNavController().currentDestination?.id != destinationId &&
            this.findNavController().currentDestination?.getAction(actionId) != null
        ) {
            this.findNavController().navigate(
                actionId,
                bundle,
                navOptions,
                navigatorExtras
            )
        } else {
            Log.e(
                "navigateToDestination",
                "***************************  can't navigate because destination is wrong *************************** "
            )
        }
    } else {
        Log.e(
            "navigateToDestination",
            " *************************** can't navigate because fragment is not added *************************** "
        )
    }
}