package com.challenge.nimmsta.budget.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.nimmsta.budget.storage.dao.BudgetDao
import com.challenge.nimmsta.budget.storage.model.BudgetEntity

@Database(entities = [BudgetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}