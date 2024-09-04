package org.d3ifcool.kulinerkuy.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.kulinerkuy.dashboard.DashboardViewModel
import org.d3ifcool.kulinerkuy.data.DatabaseDao

class DashboardViewModelFactory(private val dataSource: DatabaseDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }



}