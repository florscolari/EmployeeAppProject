package au.edu.kbs.mobiledevelopment.employeeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Employee::class], version = 3)
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
                ),
                Employee(
                    id = 0,
                    firstName = "Amber",
                    lastName = "Martin",
                    jobRole = "Delivery Driver",
                    initials = "AM",
                    phoneNumber = "0427 893 172",
                    email = "amber.martin@example.com",
                    department = "Logistics",
                    hireDate = "2022-08-06",
                    contractType = "Full-Time",
                    salary = 60170.96,
                    address = "Unit 3 460 O'Connor Street",
                    city = "Toowoomba",
                    state = "Victoria",
                    zipCode = "3092",
                    country = "Australia"
                ),
                Employee(
                    id = 1,
                    firstName = "Claudia",
                    lastName = "Campbell",
                    jobRole = "Inventory Analyst",
                    initials = "CC",
                    phoneNumber = "0420 214 632",
                    email = "claudia.campbell@example.com",
                    department = "Logistics",
                    hireDate = "2024-04-06",
                    contractType = "Full-Time",
                    salary = 68097.34,
                    address = "Suite 3 969 Gregory Road",
                    city = "Geelong",
                    state = "Western Australia",
                    zipCode = "6227",
                    country = "Australia"
                ),
                Employee(
                    id = 2,
                    firstName = "Ryan",
                    lastName = "Simpson",
                    jobRole = "Warehouse Worker",
                    initials = "RS",
                    phoneNumber = "0422 838 016",
                    email = "ryan.simpson@example.com",
                    department = "Logistics",
                    hireDate = "2023-01-02",
                    contractType = "Part-Time",
                    salary = 64868.67,
                    address = "Level 2 663 Campbell Place",
                    city = "Wagga Wagga",
                    state = "New South Wales",
                    zipCode = "2064",
                    country = "Australia"
                ),
                Employee(
                    id = 3,
                    firstName = "Kylie",
                    lastName = "Jackson",
                    jobRole = "Store Manager",
                    initials = "KJ",
                    phoneNumber = "0456 312 730",
                    email = "kylie.jackson@example.com",
                    department = "Retail",
                    hireDate = "2021-12-06",
                    contractType = "Contract",
                    salary = 62245.11,
                    address = "Unit 6 152 Robertson Court",
                    city = "Sunshine Coast",
                    state = "Tasmania",
                    zipCode = "7099",
                    country = "Australia"
                ),
                Employee(
                    id = 4,
                    firstName = "Lisa",
                    lastName = "Ross",
                    jobRole = "Finance Assistant",
                    initials = "LR",
                    phoneNumber = "0412 181 078",
                    email = "lisa.ross@example.com",
                    department = "Finance",
                    hireDate = "2022-10-28",
                    contractType = "Casual",
                    salary = 78785.45,
                    address = "Unit 9 651 Carter Plaza",
                    city = "Wollongong",
                    state = "South Australia",
                    zipCode = "5560",
                    country = "Australia"
                ),
                Employee(
                    id= 5,
                    firstName= "Nathan",
                    lastName = "Edwards",
                    jobRole = "Sales Associate",
                    initials = "NE",
                    phoneNumber = "0499 187 462",
                    email = "nathan.edwards@example.com",
                    department = "Retail",
                    hireDate = "2021-06-19",
                    contractType = "Casual",
                    salary = 80711.34,
                    address = "Apt. 21 1063 Collins Highway",
                    city = "Geelong",
                    state = "Queensland",
                    zipCode = "4819",
                    country = "Australia"
                ),
                Employee(
                    id = 6,
                    firstName = "Aaron",
                    lastName = "Barnes",
                    jobRole = "Logistics Coordinator",
                    initials = "AB",
                    phoneNumber = "0426 701 183",
                    email =  "aaron.barnes@example.com",
                    department = "Logistics",
                    hireDate = "2023-11-09",
                    contractType = "Contract",
                    salary = 60748.63,
                    address = "Suite 59 810 Parsons Keys",
                    city = "Cairns",
                    state = "Queensland",
                    zipCode = "4362",
                    country = "Australia"
                ),
                Employee(
                    id = 7,
                    firstName =  "Ian",
                    lastName =  "King",
                    jobRole =  "Sales Associate",
                    initials =  "IK",
                    phoneNumber =  "0430 140 348",
                    email =  "ian.king@example.com",
                    department =  "Retail",
                    hireDate =  "2022-02-06",
                    contractType =  "Full-Time",
                    salary =  91961.23,
                    address =  "Suite 6 552 Fraser Points",
                    city = "Launceston",
                    state = "New South Wales",
                    zipCode = "2792",
                    country = "Australia"
                ),
                Employee(
                    id = 8,
                    firstName =  "Jason",
                    lastName =  "Cameron",
                    jobRole =  "Customer Service Rep",
                    initials =  "JC",
                    phoneNumber =  "0489 299 284",
                    email =  "jason.cameron@example.com",
                    department =  "Support",
                    hireDate =  "2021-11-16",
                    contractType =  "Full-Time",
                    salary =  66140.24,
                    address = "Level 6 303 Melissa Viaduct",
                    city =  "Toowoomba",
                    state =  "Victoria",
                    zipCode =  "3021",
                    country =  "Australia"
                ),
                Employee(
                    id= 9,
                    firstName = "Liam",
                    lastName = "Cox",
                    jobRole = "Inventory Analyst",
                    initials = "LC",
                    phoneNumber = "0490 183 928",
                    email = "liam.cox@example.com",
                    department = "Logistics",
                    hireDate = "2020-01-20",
                    contractType = "Casual",
                    salary = 48814.73,
                    address = "Suite 57 215 Lauren Crescent",
                    city = "Darwin",
                    state = "New South Wales",
                    zipCode = "2716",
                    country = "Australia"
                ),
                Employee(
                    id = 10,
                    firstName = "David",
                    lastName = "Grant",
                    jobRole = "Customer Service Rep",
                    initials = "DG",
                    phoneNumber = "0432 943 728",
                    email = "david.grant@example.com",
                    department = "Support",
                    hireDate = "2020-09-12",
                    contractType = "Part-Time",
                    salary = 46299.79,
                    address = "Suite 0 1343 Hayden Lane",
                    city = "Wollongong",
                    state = "Queensland",
                    zipCode = "4680",
                    country = "Australia"
                ),
                Employee(
                    id = 11,
                    firstName = "Mark",
                    lastName = "Harris",
                    jobRole = "IT Support Technician",
                    initials = "MH",
                    phoneNumber = "0433 745 579",
                    email = "mark.harris@example.com",
                    department = "IT",
                    hireDate = "2022-01-30",
                    contractType = "Contract",
                    salary = 84568.76,
                    address = "Suite 50 702 Lewis Freeway",
                    city = "Darwin",
                    state = "South Australia",
                    zipCode = "5127",
                    country = "Australia"
                ),
                Employee(
                    id = 12,
                    firstName = "David",
                    lastName = "Miller",
                    jobRole = "Store Manager",
                    initials = "DM",
                    phoneNumber = "0411 499 931",
                    email = "david.miller@example.com",
                    department = "Retail",
                    hireDate = "2021-03-05",
                    contractType = "Part-Time",
                    salary = 88440.97,
                    address = "Suite 46 873 Ross Divide",
                    city = "Newcastle",
                    state = "South Australia",
                    zipCode = "5602",
                    country = "Australia"
                ),
                Employee(
                    id = 13,
                    firstName = "Brandon",
                    lastName = "Smith",
                    jobRole = "Warehouse Worker",
                    initials = "BS",
                    phoneNumber = "0424 425 822",
                    email = "brandon.smith@example.com",
                    department = "Logistics",
                    hireDate = "2021-11-03",
                    contractType = "Casual",
                    salary = 50927.01,
                    address = "Suite 77 281 Riley Island",
                    city = "Canberra",
                    state = "Western Australia",
                    zipCode = "6401",
                    country = "Australia"
                ),
                Employee(
                    id = 14,
                    firstName = "Craig",
                    lastName = "Scott",
                    jobRole = "Delivery Driver",
                    initials = "CS",
                    phoneNumber = "0410 368 494",
                    email = "craig.scott@example.com",
                    department = "Logistics",
                    hireDate = "2022-09-24",
                    contractType = "Full-Time",
                    salary = 86965.92,
                    address = "Suite 53 688 Michael Junctions",
                    city = "Sunshine Coast",
                    state = "Victoria",
                    zipCode = "3691",
                    country = "Australia"
                ),
                Employee(
                    id = 15,
                    firstName = "Luke",
                    lastName = "Ellis",
                    jobRole = "Customer Service Rep",
                    initials = "LE",
                    phoneNumber = "0454 944 345",
                    email = "luke.ellis@example.com",
                    department = "Support",
                    hireDate = "2024-01-14",
                    contractType = "Full-Time",
                    salary = 91745.0,
                    address = "Suite 65 640 Nicole Mews",
                    city = "Townsville",
                    state = "Western Australia",
                    zipCode = "6243",
                    country = "Australia"
                ),
                Employee(
                    id = 16,
                    firstName = "Trevor",
                    lastName = "Evans",
                    jobRole = "Logistics Coordinator",
                    initials = "TE",
                    phoneNumber = "0429 119 657",
                    email = "trevor.evans@example.com",
                    department = "Logistics",
                    hireDate = "2020-10-14",
                    contractType = "Full-Time",
                    salary = 49109.75,
                    address = "Apt. 50 223 Lucas Plaza",
                    city = "Newcastle",
                    state = "New South Wales",
                    zipCode = "2056",
                    country = "Australia"
                ),
                Employee(
                    id = 17,
                    firstName = "Brandon",
                    lastName = "Brooks",
                    jobRole = "Inventory Analyst",
                    initials = "BB",
                    phoneNumber = "0441 156 438",
                    email = "brandon.brooks@example.com",
                    department = "Logistics",
                    hireDate = "2023-09-03",
                    contractType = "Contract",
                    salary = 78533.27,
                    address = "Level 4 903 Brian Street",
                    city = "Launceston",
                    state = "Tasmania",
                    zipCode = "7133",
                    country = "Australia"
                ),
                Employee(
                    id = 18,
                    firstName = "Joel",
                    lastName = "Powell",
                    jobRole = "Sales Associate",
                    initials = "JP",
                    phoneNumber = "0452 623 178",
                    email = "joel.powell@example.com",
                    department = "Retail",
                    hireDate = "2024-04-10",
                    contractType = "Contract",
                    salary = 70352.47,
                    address = "Suite 68 959 Jeremy Springs",
                    city = "Canberra",
                    state = "Victoria",
                    zipCode = "3090",
                    country = "Australia"
                )

            )
        }
    }
}

