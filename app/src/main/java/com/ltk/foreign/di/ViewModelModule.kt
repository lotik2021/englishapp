package com.ltk.foreign.di

import com.ltk.foreign.features.main.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
    ): MainViewModel {
        return MainViewModel()
    }
}
