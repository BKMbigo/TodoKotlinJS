package components

import constants.SelectedProject
import context.ProjectsContext
import context.SelectedProjectContext
import context.getValue
import csstype.ClassName
import data.Task
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.ServerTimestampBehavior
import dev.gitlive.firebase.firestore.firestore
import firebase
import helpers.where
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul

val Tasks = FC<Props> { props ->
    val selectedProject by useContext(SelectedProjectContext)
    val projects = useContext(ProjectsContext)

    var tasks by useState<List<Task>>(emptyList())
    var archivedTasks by useState<List<Task>>(emptyList())

    useEffect(selectedProject) {
        CoroutineScope(Job()).launch {

            var unsubscribeList = Firebase.firestore(firebase)
                .collection("tasks")
                .where("userId", "Yhmn9NAFXkSCh")

            unsubscribeList = when (val selProject = selectedProject) {
                is SelectedProject.Project ->
                    unsubscribeList.where("projectId", selProject.projectId)

                SelectedProject.CollatedTask.TODAY ->
                    unsubscribeList.where(
                        "date",
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toEpochDays()
                    )

                SelectedProject.CollatedTask.INBOX ->
                    unsubscribeList.where("date", 0)

                else ->
                    unsubscribeList
            }

            unsubscribeList.snapshots.collect { querySnapshot ->
                val newTasks = if (selectedProject is SelectedProject.CollatedTask.NEXT_7) {
                    val currentDate =
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toEpochDays()
                    val data = querySnapshot.documents.map {
                        it.data<Task>(ServerTimestampBehavior.NONE)
                    }.filter { it.date > currentDate - 7 }

                    data
                } else
                    querySnapshot.documents.map { it.data<Task>(ServerTimestampBehavior.NONE) }

                console.log(newTasks.map { it.toString() })

                tasks = newTasks.filter { !it.archived }
                archivedTasks = newTasks.filter { it.archived }
                console.log(tasks.map { it.toString() })
            }

        }
    }
    var projectName = ""

    if (projects.isNotEmpty() && selectedProject is SelectedProject.Project) {
        projectName =
            projects.find { it.projectId == (selectedProject as SelectedProject.Project).projectId }?.name ?: ""
    }

    if (selectedProject is SelectedProject.CollatedTask) {
        projectName = (selectedProject as SelectedProject.CollatedTask).collatedTask.label
    }

    useEffect {
        document.title = "${projectName}: Todoist"
    }

    div {
        className = ClassName("tasks")
        h2 {
            +projectName
        }

        ul {
            className = ClassName("tasks__list")
            tasks.forEach { task ->
                li {
                    key = task.taskId
                    Checkbox {
                        this.id = task.taskId
                    }
                    span {
                        +task.task
                    }
                }
            }
        }

        AddTask {
            showQuickAddTask = false
            shouldShowMain = false
            setShowQuickAddTask = {
                showQuickAddTask = it
            }
            showAddTaskMain = true
        }

    }
}