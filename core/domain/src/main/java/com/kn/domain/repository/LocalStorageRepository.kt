package com.kn.domain.repository

import androidx.activity.result.contract.ActivityResultContracts

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
interface LocalStorageRepository {
 fun setToken(token:String)

 fun getToken():String
}