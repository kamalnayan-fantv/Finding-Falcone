package com.kn.data.di

import com.kn.data.repository.FalconRepositoryImpl
import com.kn.data.repository.LocalStorageRepositoryImpl
import com.kn.domain.repository.FalconRepository
import com.kn.domain.repository.LocalStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** @Author Kamal Nayan
Created on: 04/10/23
 **/

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

 @Binds
 @Singleton
 abstract fun bindFalconRepository(falconRepositoryImpl: FalconRepositoryImpl): FalconRepository

 @Binds
 @Singleton
 abstract fun bindLocalStorage(localStorageRepositoryImpl: LocalStorageRepositoryImpl):LocalStorageRepository
}