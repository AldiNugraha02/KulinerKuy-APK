package org.d3ifcool.kulinerkuy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3ifcool.kulinerkuy.model.KulinerKuy

@Database(entities = [KulinerKuy::class], version = 1, exportSchema = false)
abstract class KulinerKuyDB : RoomDatabase() {
    abstract val dao : DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: KulinerKuyDB? = null
        fun getInstance(context: Context): KulinerKuyDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KulinerKuyDB::class.java,
                        "kulinerkuy.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}