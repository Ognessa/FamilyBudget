package com.app.family_budget.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.family_budget.data.local.dao.RepoDao
import com.app.family_budget.data.local.model.RepoEntity


@Database(entities = [RepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): RepoDao
}