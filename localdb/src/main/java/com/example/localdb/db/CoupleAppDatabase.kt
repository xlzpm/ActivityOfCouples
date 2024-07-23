package com.example.localdb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.localdb.converter.Converters
import com.example.randomActivities.domain.dao.RandomActivitiesDao
import com.example.randomActivities.domain.model.RandomActivitiesEntity

@Database(
    entities = [RandomActivitiesEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CoupleAppDatabase: RoomDatabase() {
    abstract fun RandomActivitiesDao(): RandomActivitiesDao
}