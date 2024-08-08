package com.chill.mallang.core.di

import com.chill.mallang.data.api.AreaApi
import com.chill.mallang.data.api.FactionApi
import com.chill.mallang.data.api.QuizApi
import com.chill.mallang.data.api.StudyApi
import com.chill.mallang.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideAreaApi(retrofit: Retrofit): AreaApi = retrofit.create()

    @Provides
    @Singleton
    fun provideQuizApi(retrofit: Retrofit): QuizApi = retrofit.create()

    @Provides
    @Singleton
    fun provideFactionApi(retrofit: Retrofit): FactionApi = retrofit.create()

    @Provides
    @Singleton
    fun provideStudyApi(retrofit: Retrofit): StudyApi = retrofit.create()
}
