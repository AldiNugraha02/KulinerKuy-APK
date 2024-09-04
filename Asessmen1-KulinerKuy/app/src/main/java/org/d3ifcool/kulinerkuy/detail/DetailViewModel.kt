package org.d3ifcool.kulinerkuy.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3ifcool.kulinerkuy.data.DatabaseDao

class DetailViewModel(private val db : DatabaseDao) : ViewModel() {
    fun getData(id: Int) = db.getById(id)
    fun updateData(id: Int, nama: String, alamat: String,) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.updateData(id, nama, alamat)
            }
        }
    }
}