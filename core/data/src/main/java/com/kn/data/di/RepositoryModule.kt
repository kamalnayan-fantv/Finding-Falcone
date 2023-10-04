package com.kn.data.di

import com.kn.data.repository.FalconRepositoryImpl
import com.kn.domain.repository.FalconRepository
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
}