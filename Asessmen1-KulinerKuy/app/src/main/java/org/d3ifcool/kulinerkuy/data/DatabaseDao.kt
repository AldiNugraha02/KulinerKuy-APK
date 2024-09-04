package org.d3ifcool.kulinerkuy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.d3ifcool.kulinerkuy.model.KulinerKuy

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM kulinerkuy")
    fun getAll(): LiveData<List<KulinerKuy>>

    @Query("SELECT * FROM kulinerkuy WHERE id = :id")
    fun getById(id: Int): LiveData<KulinerKuy>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg modelDatabases: KulinerKuy)

    @Query("DELETE FROM kulinerkuy WHERE id IN (:ids)")
    fun deleteById(ids: List<Int>)

    @Query("DELETE FROM kulinerkuy")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM kulinerkuy")
    fun countData(): LiveData<Int>


    @Query("SELECT * FROM kulinerkuy WHERE tanggal = :tanggal")
    fun getAllToday(tanggal: String): LiveData<List<KulinerKuy>>

    @Query("UPDATE kulinerkuy SET nama = :nama, alamat = :alamat WHERE id = :id")
    fun updateData(id: Int, nama: String, alamat: String)

    @Query("UPDATE kulinerkuy SET tag = 1 WHERE id = :id")
    fun checkTag(id: Int)

    @Query("UPDATE kulinerkuy SET tag = 0 WHERE id = :id")
    fun uncheckTag(id: Int)

    // filter data with name
    @Query("SELECT * FROM kulinerkuy WHERE nama LIKE '%' || :name || '%'")
    fun filterData(name: String): LiveData<List<KulinerKuy>>

    @Query("UPDATE kulinerkuy SET isChecked = 1 WHERE id = :id")
    fun updateIsCheckedToTrue(id: Int)

    @Query("UPDATE kulinerkuy SET isChecked = 0 WHERE id = :id")
    fun updateIsCheckedToFalse(id: Int)

    // filter data with tag
    @Query("SELECT * FROM kulinerkuy WHERE tag = :tag")
    fun filter(tag: Int): LiveData<List<KulinerKuy>>
}