package com.arsildo.composedatastorethemeing.datastore

import android.app.Application

class ChangeApplicationTheme : Application() {
    val dataStoreRepository by lazy {
        DataStoreRepository(this)
    }
}