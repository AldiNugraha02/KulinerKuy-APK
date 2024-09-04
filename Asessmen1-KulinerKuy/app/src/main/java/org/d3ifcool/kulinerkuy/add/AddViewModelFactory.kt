package org.d3ifcool.kulinerkuy.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.kulinerkuy.data.DatabaseDao

class AddViewModelFactory(private val dataSource: DatabaseDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }



}