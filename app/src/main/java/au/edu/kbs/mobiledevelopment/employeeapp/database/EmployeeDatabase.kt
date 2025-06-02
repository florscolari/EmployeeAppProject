package au.edu.kbs.mobiledevelopment.employeeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee


@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase: RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao

    companion object{
        // Anything inside this is static and can be accessed from anywhere in the code by its name
        @Volatile
        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        // invoke function follows the singleton pattern ensuring only one instance is created
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EmployeeDatabase::class.java,
                "employee_db"
            ).build()

    }

}