package com.ltk.foreign.features.statistic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ltk.foreign.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticScreenViewModel @Inject constructor(
    application: Application,
    private val dataRepository: DataRepository,
) : AndroidViewModel(application) {

}
