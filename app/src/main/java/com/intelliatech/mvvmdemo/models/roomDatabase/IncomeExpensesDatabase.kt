package com.intelliatech.mvvmdemo.models.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.ExpensesCategoryDAO
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeCategoryDAO
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeExpensesDAO
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.PaymentMethodDAO
import kotlinx.coroutines.*

@Database(
    entities = [IncomeCategoryEntity::class, ExpensesCategoryEntity::class, IncomeExpensesEntity::class, PaymentMethodEntity::class],
    version = 2,
    exportSchema = false
)
abstract class IncomeExpensesDatabase : RoomDatabase() {

    abstract fun incomeExpensesDao(): IncomeExpensesDAO
    abstract fun incomeCategoryDao(): IncomeCategoryDAO
    abstract fun expensesCategoryDao(): ExpensesCategoryDAO
    abstract fun paymentMethodDao(): PaymentMethodDAO

    companion object {
        private var instance: IncomeExpensesDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): IncomeExpensesDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, IncomeExpensesDatabase::class.java,
                    "Income_Expenses_Manager"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
//                populateDatabase(instance!!)

//                CoroutineScope().launch(Dispatchers.IO) { populateData(instance) }
            }
        }

//        private fun populateDatabase(db: IncomeExpensesDatabase) {
//            val  expensesDAO= db.expensesCategory()
//            subscribeOnBackground {
//                expensesDAO.addCategory(ExpensesCategoryEntity( 1,"fgf"))
//                expensesDAO.addCategory(ExpensesCategoryEntity( 2," cddc"))
//                expensesDAO.addCategory(ExpensesCategoryEntity(3, "desc 3"))
//
//            }
//        }

        suspend fun populateData(db: IncomeExpensesDatabase?) {
            db?.let { database ->
                withContext(Dispatchers.IO) {
//val category1 = expe
                }

            }
        }
    }
}