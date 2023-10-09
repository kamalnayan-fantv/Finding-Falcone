package com.kn.findingthefalcon.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

 @Provides
 fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
  return context.getSharedPreferences("shared_prefs", MODE_PRIVATE)
 }

}