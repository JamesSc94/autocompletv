package com.jamessc94.autocompletetvj.Vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jamessc94.autocompletetvj.DB.Dao_search

class vm_dynamicautocompletetvj_factory (
    private val dataSource: Dao_search,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(vm_dynamicautocompletetvj::class.java)) {
            return vm_dynamicautocompletetvj(dataSource, application) as T

        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}