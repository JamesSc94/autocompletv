package com.jamessc94.autocompletetvj.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jamessc94.autocompletetvj.Util.converters_date

@Database(entities = [D_search::class], version = 4, exportSchema = false)
@TypeConverters(converters_date::class)
abstract class DB_ : RoomDatabase() {

    abstract val searchDao : Dao_search

    companion object {
        private var INSTANCE: DB_? = null

        fun getInstance(context: Context) : DB_ {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DB_::class.java,
                        "search_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance

                }

                return instance

            }

        }

    }

}