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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [IncomeCategoryEntity::class, ExpensesCategoryEntity::class, IncomeExpensesEntity::class, PaymentMethodEntity::class],
    version = 3,
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
        fun getInstance(ctx: Context, scope: CoroutineScope): IncomeExpensesDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, IncomeExpensesDatabase::class.java,
                    "Income_Expenses_Manager"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(IncomeExpensesDataBaseCallBack(scope))
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }


        class IncomeExpensesDataBaseCallBack(val scope: CoroutineScope) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                instance?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateExpensesData(database.expensesCategoryDao())
                        populateIncomeData(database.incomeCategoryDao())
                        populatePaymentData(database.paymentMethodDao())

                    }
                }
            }
        }

        suspend fun populatePaymentData(paymentMethodDao: PaymentMethodDAO) {
            val paymentMethodEntity = PaymentMethodEntity(1, "Cash")
            val paymentMethodEntity1 = PaymentMethodEntity(2, "UPI")
            val paymentMethodEntity2 = PaymentMethodEntity(3, "Paytm")
            val paymentMethodEntity3 = PaymentMethodEntity(4, "Gpay")
            val paymentMethodEntity4 = PaymentMethodEntity(6, "Check")
            val paymentMethodEntity5 = PaymentMethodEntity(7, "Phone pe")
            val paymentMethodEntity6 = PaymentMethodEntity(8, "Other")

            val paymentMethodList = listOf(
                paymentMethodEntity,
                paymentMethodEntity1,
                paymentMethodEntity2,
                paymentMethodEntity3,
                paymentMethodEntity4,
                paymentMethodEntity5,
                paymentMethodEntity6
            )
            paymentMethodDao.insertAllPaymentMethod(paymentMethodList)
        }

        suspend fun populateExpensesData(expensesCategoryDao: ExpensesCategoryDAO) {
            val expensesCategoryEntity1 = ExpensesCategoryEntity(1, "Travel")
            val expensesCategoryEntity2 = ExpensesCategoryEntity(2, "Family")
            val expensesCategoryEntity3 = ExpensesCategoryEntity(3, "Party")
            val expensesCategoryEntity4 = ExpensesCategoryEntity(4, "Travel")
            val expensesCategoryList = listOf(
                expensesCategoryEntity1,
                expensesCategoryEntity2,
                expensesCategoryEntity3,
                expensesCategoryEntity4

            )

            expensesCategoryDao.addAllCategory(expensesCategoryList)
        }

        suspend fun populateIncomeData(incomeCategoryDAO: IncomeCategoryDAO) {
            val incomeCategoryEntity1 = IncomeCategoryEntity(1, "Allowance")
            val incomeCategoryEntity2 = IncomeCategoryEntity(2, "Bonus")
            val incomeCategoryEntity3 = IncomeCategoryEntity(3, "Incentives")
            val incomeCategoryEntity4 = IncomeCategoryEntity(4, "Salary")
            val incomeCategoryEntityList = listOf(
                incomeCategoryEntity1,
                incomeCategoryEntity2,
                incomeCategoryEntity3,
                incomeCategoryEntity4
            )

            incomeCategoryDAO.addAllCategory(incomeCategoryEntityList)

        }
    }
}