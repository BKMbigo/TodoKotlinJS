package data

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val archived: Boolean = false,
    val date: Int,
    val projectId: String,
    val task: String,
    val taskId: String,
    val userId: String
)
