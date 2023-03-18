package data

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val projectId: String,
    val userId: String,
    val name: String
)
