package com.example.totalrecall

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

@Entity(
    tableName = "to_do_list",
    indices = [Index("id")]
)
data class TaskDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "what_to_do") val whatToDo: String,
    @ColumnInfo(name = "deadline") val deadline: Date,
    @ColumnInfo(name = "completed") val completed: Boolean
)

@Dao
interface ToDoListDao {

    @Insert(entity = TaskDbEntity::class)
    fun insertNewTask(newTask: TaskDbEntity)

    @Query("SELECT to_do_list.id, what_to_do, deadline, completed FROM to_do_list")
    fun getAllTasks() : List<ToDoInfoTuple>

    @Query("")
    fun deleteTask(taskId: Long)
}

@Database(
    version = 1,
    entities = [
        TaskDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getToDoList() : ToDoListDao
}

data class ToDoInfoTuple(
    val id: Long,
    @ColumnInfo(name = "what_to_do") val whatToDo: String,
    @ColumnInfo(name = "deadline") val deadline: Date,
    @ColumnInfo(name = "completed") val completed: Boolean
)

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("room_article.db")
            .build()
    }
}

class ToDoListRepository(private val toDoListDao: ToDoListDao) {

    suspend fun insertNewTask(taskDbEntity: TaskDbEntity) {
        withContext(Dispatchers.IO) {
            toDoListDao.insertNewTask(taskDbEntity)
        }
    }

    suspend fun getAllTasks(): List<ToDoInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext toDoListDao.getAllTasks()
        }
    }

    suspend fun deleteTaskById(id: Long) {
        withContext(Dispatchers.IO) {
            toDoListDao.deleteTask(id)
        }
    }
}