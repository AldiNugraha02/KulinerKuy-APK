package org.d3ifcool.kulinerkuy.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3ifcool.kulinerkuy.data.DatabaseDao
import org.d3ifcool.kulinerkuy.model.KulinerKuy
import java.util.Calendar
import java.util.Date

class DashboardViewModel(private val db : DatabaseDao) : ViewModel() {
    val data = db.getAll()
    fun deleteData(ids: List<Int>) {
        val newIds = ids.toList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.deleteById(newIds)
            }
        }
    }


    var countData = db.countData()
    val tanggal = getCurrentDate()
    val dataToday = db.getAllToday(tanggal)

    fun getCurrentDate(): String {
        val c: Calendar = Calendar.getInstance()
        val year: Int
        val month: Int
        val day: Int
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DATE)
        return day.toString() + "-" + (month + 1) + "-" + year
    }

    fun dataById(id: Int) = db.getById(id)

    fun updateTag(it: Int, i: Int) {
        if(i == 0){
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    db.checkTag(it)
                }
            }
        }
    }

    fun filterData(name: String) = db.filterData(name)
    fun updateIsCheckedToTrue(copy: KulinerKuy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.updateIsCheckedToTrue(copy.id)
            }
        }

    }

    fun updateIsCheckedToFalse(copy: KulinerKuy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.updateIsCheckedToFalse(copy.id)
            }
        }
    }

    fun filter(tag : Int) = db.filter(tag)


}

