package com.kn.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.kn.commons.utils.constants.Constants
import com.kn.commons.utils.extensions.Empty
import com.kn.domain.repository.LocalStorageRepository
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
class LocalStorageRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LocalStorageRepository {


    override fun setToken(token: String) {
        sharedPreferences.edit {
            putString(Constants.SHARED_PREF_KEY_TOKEN, token)
        }
    }

    override fun getToken(): String {
        return sharedPreferences.getString(Constants.SHARED_PREF_KEY_TOKEN, String.Empty)
            ?: String.Empty
    }
}