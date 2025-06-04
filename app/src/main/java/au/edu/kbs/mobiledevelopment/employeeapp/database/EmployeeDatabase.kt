package au.edu.kbs.mobiledevelopment.employeeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Employee::class], version = 5)
abstract class EmployeeDatabase: RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao

    companion object {
        // Anything inside this is static and can be accessed from anywhere in the code by its name
        @Volatile
        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): EmployeeDatabase =
            instance ?: synchronized(LOCK) {
                instance ?: createDatabase(context).also { builtDb ->
                    instance = builtDb

                    // GenAI Assistant (heady challenge here): Launch a coroutine here to pre-populate the database if empty
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = builtDb.getEmployeeDao()
                        // Check if database is empty (no employees)
                        val employees = dao.getAllEmployeesSuspend()
                        if (employees.isEmpty()) {
                            dao.insertAll(prepopulateEmployees())
                        }
                    }
                }
            }
        private fun createDatabase(context: Context): EmployeeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                EmployeeDatabase::class.java,
                "employee_db"
            )
                //Destroy previous database version
                .fallbackToDestructiveMigration()
                // Build new instance
                .build()
        }

        // Populate Employee list (I couldn't make it work with json file insertion)
        private fun prepopulateEmployees(): List<Employee> {
            return listOf(
                Employee(
                    id = 0,
                    firstName = "Ella",
                    lastName = "Taylor",
                    jobRole = "Retail Assistant",
                    initials = "ET",
                    phoneNumber = "0411 123 456",
                    email = "ella.taylor@company.com",
                    department = "Sales",
                    hireDate = "2022-01-15",
                    contractType = "Full-Time",
                    salary = 52000.0,
                    address = "123 Queen Street",
                    city = "Sydney",
                    state = "NSW",
                    zipCode = "2000",
                    country = "Australia"
                ),
                Employee(
                    id = 0,
                    firstName = "Mason",
                    lastName = "Green",
                    jobRole = "Warehouse Coordinator",
                    initials = "MG",
                    phoneNumber = "0402 654 321",
                    email = "mason.green@company.com",
                    department = "Logistics",
                    hireDate = "2021-06-10",
                    contractType = "Part-Time",
                    salary = 45000.0,
                    address = "88 Collins St",
                    city = "Melbourne",
                    state = "VIC",
                    zipCode = "3000",
                    country = "Australia"
                )
            )
        }
    }
}

