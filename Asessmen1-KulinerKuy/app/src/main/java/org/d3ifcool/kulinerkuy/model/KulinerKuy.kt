package org.d3ifcool.kulinerkuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KulinerKuy(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val alamat: String,
    val foto: String,
    val tanggal: String,
    val jam: String,
    val tag: Int,
    val isChecked: Boolean = false,
)
