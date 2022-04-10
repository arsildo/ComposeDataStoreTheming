package com.arsildo.composedatastorethemeing.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ApplicationViewModel(private val dataStoreRepository: DataStoreRepository) : ViewModel() {

    val themeSetting = dataStoreRepository.currentTheme.map {
        it?.let {
            ApplicationThemes.values()[it]
        }
    }

    fun changeTheme(theme: ApplicationThemes) {
        viewModelScope.launch {
            dataStoreRepository.setApplicationTheme(theme.ordinal)
        }
    }
}


class ApplicationViewModelFactory(private val dataStoreRepository: DataStoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationViewModel::class.java)) {
            return ApplicationViewModel(dataStoreRepository) as T
        } else throw IllegalArgumentException("ViewModel Class Not Found")
    }
}
