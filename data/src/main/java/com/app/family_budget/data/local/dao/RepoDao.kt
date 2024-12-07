package com.app.family_budget.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.family_budget.data.local.model.RepoEntity
import com.app.family_budget.data.local.model.RepoEntity.Companion.ID
import com.app.family_budget.data.local.model.RepoEntity.Companion.STARS_COUNT
import com.app.family_budget.data.local.model.RepoEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY $STARS_COUNT DESC")
    fun getFavourites(): Flow<List<RepoEntity>>

    @Upsert
    fun insertAll(vararg entity: RepoEntity)

    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM $TABLE_NAME WHERE $ID = :id LIMIT 1")
    fun getById(id: Long): RepoEntity?

    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME WHERE id = :id LIMIT 1)")
    fun isExists(id: Long): Boolean

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    fun getCount(): Long
}