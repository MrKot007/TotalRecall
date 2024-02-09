package com.example.totalrecall

import java.util.Date

data class Contact(val name: String, val phone: String)

data class ToDos(
    val id: Long,
    val whatToDo: String,
    val deadline: Date,
    val completed: Boolean
) {
    fun toTaskDbEntity(): TaskDbEntity = TaskDbEntity(
        id = this.id,
        whatToDo = this.whatToDo,
        deadline = this.deadline,
        completed = this.completed
    )

}