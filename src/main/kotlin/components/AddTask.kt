package components

import constants.SelectedProject
import constants.userId
import context.SelectedProjectContext
import context.getValue
import csstype.ClassName
import data.Project
import data.Task
import dev.gitlive.firebase.firebase
import helpers.generateRandomId
import js.RegCalendarAlt
import js.RegListAlt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.datetime.internal.JSJoda.Clock
import kotlinx.datetime.internal.JSJoda.LocalDate
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useContext
import react.useState

val AddTask = FC<AddTaskProps> { props ->
    var task by useState("")
    var taskDate by useState(0)
    var project by useState<Project?>(null)
    var showMain by useState(props.shouldShowMain)
    var showProjectOverlay by useState(false)
    var showTaskDate by useState(false)

    val selectedProject by useContext(SelectedProjectContext)

    suspend fun addTask(): Boolean {
        val projectId = project?.projectId ?: when (selectedProject) {
            is SelectedProject.Project -> (selectedProject as SelectedProject.Project).projectId
            is SelectedProject.CollatedTask -> (selectedProject as SelectedProject.CollatedTask).collatedTask.name
            is SelectedProject.EMPTY -> ""
        }

        val collatedDate = when (selectedProject) {
            is SelectedProject.Project -> 0
            is SelectedProject.CollatedTask -> when (selectedProject as SelectedProject.CollatedTask) {
                SelectedProject.CollatedTask.INBOX -> 0
                SelectedProject.CollatedTask.NEXT_7 -> LocalDate.now(Clock.systemDefaultZone()).toEpochDay().toInt() + 7
                SelectedProject.CollatedTask.TODAY -> LocalDate.now(Clock.systemDefaultZone()).toEpochDay().toInt()
            }

            is SelectedProject.EMPTY -> 0
        }

        val randomId = generateRandomId()

        return if (task.isNotBlank() && projectId.isNotBlank()) {
            firebase
                .firestore()
                .collection("tasks")
                .doc(randomId)
                .set(
                    Task(
                        id = randomId,
                        archived = false,
                        date = if (collatedDate == 0) taskDate else collatedDate,
                        projectId = projectId,
                        task = task,
                        taskId = randomId,
                        userId = userId,

                        )
                ).await()

            task = ""
            project = null
            showMain = props.shouldShowMain
            showProjectOverlay = false

            true
        } else false


    }

    div {
        className = if (props.showQuickAddTask)
            ClassName("add-task add-task__overlay") else ClassName("add-task")

        if (props.showAddTaskMain) {
            div {
                className = ClassName("add-task__shallow")
                onClick = {
                    showMain = !showMain
                }
                span {
                    className = ClassName("add-task__plus")
                    +"+"
                }
                span {
                    className = ClassName("add-task__text")
                    +"Add Task"
                }
            }
        }

        if (showMain || props.showQuickAddTask) {
            div {
                className = ClassName("add-task__main")
                if (props.showQuickAddTask) {
                    div {
                        h2 {
                            className = ClassName("header")
                            +"Quick Add Task"
                        }
                        span {
                            className = ClassName("add-task__cancel-x")
                            onClick = {
                                showMain = false
                                showProjectOverlay = false
                                props.setShowQuickAddTask(false)
                            }
                            +"X"
                        }

                    }
                }



                ProjectOverlay {
                    setProject = { projectId ->
                        project = projectId
                    }
                    this.showProjectOverlay = showProjectOverlay
                    setShowProjectOverlay = {
                        showProjectOverlay = it
                    }
                }

                TaskDate {
                    setTaskDate = {
                        taskDate = it
                    }
                    this.showTaskDate = showTaskDate
                    setShowTaskDate = {
                        showTaskDate = it
                    }
                }

                input {
                    className = ClassName("add-task__content")
                    type = InputType.text
                    value = task
                    onChange = {
                        task = it.target.value
                    }
                }

                button {
                    type = ButtonType.button
                    className = ClassName("add-task__submit")
                    onClick = {
                        CoroutineScope(SupervisorJob()).launch {
                            addTask()
                            if (props.showQuickAddTask) {
                                props.showQuickAddTask = false
                            }
                        }
                    }
                    +"Add Task"
                }


                if (!props.showQuickAddTask) {
                    span {
                        className = ClassName("add-task__cancel")
                        onClick = {
                            showMain = false
                            showProjectOverlay = false
                        }
                        +"Cancel"
                    }
                }

                span {
                    className = ClassName("add-task__project")
                    onClick = {
                        showProjectOverlay = !showProjectOverlay
                    }
                    RegListAlt {}
                }
                span {
                    className = ClassName("add-task__date")
                    onClick = {
                        showTaskDate = !showTaskDate
                    }
                    RegCalendarAlt {}
                }
            }
        }
    }

}

external interface AddTaskProps : Props {
    var showAddTaskMain: Boolean
    var shouldShowMain: Boolean
    var showQuickAddTask: Boolean
    var setShowQuickAddTask: (Boolean) -> Unit
}