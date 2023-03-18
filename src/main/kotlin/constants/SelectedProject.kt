package constants

sealed class SelectedProject {
    object EMPTY : SelectedProject()
    class Project(val projectId: String) :
        SelectedProject()

    sealed class CollatedTask(val collatedTask: constants.CollatedTask) : SelectedProject() {
        object INBOX : CollatedTask(constants.CollatedTask.INBOX)
        object TODAY : CollatedTask(constants.CollatedTask.TODAY)
        object NEXT_7 : CollatedTask(constants.CollatedTask.NEXT_7)
    }
}
