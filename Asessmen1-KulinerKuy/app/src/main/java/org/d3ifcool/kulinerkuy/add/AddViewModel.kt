package org.d3ifcool.kulinerkuy.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3ifcool.kulinerkuy.data.DatabaseDao
import org.d3ifcool.kulinerkuy.model.KulinerKuy

class AddViewModel(private val db : DatabaseDao) : ViewModel() {
    fun insertData(datakuliner: KulinerKuy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertData(datakuliner)
            }
        }
    }

    val data = db.getAll()

}