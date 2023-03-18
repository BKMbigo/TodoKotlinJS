package components

import csstype.ClassName
import data.Project
import dev.gitlive.firebase.firebase
import helpers.generateRandomId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState

val AddProject = FC<AddProjectProps> { props ->
    var show by useState<Boolean>(props.shouldShow)
    var projectName by useState("")

    val projectId = generateRandomId()

    suspend fun addProject() {
        if (projectName.isNotBlank()) {
            firebase
                .firestore()
                .collection("projects")
                .doc(projectId)
                .set(
                    Project(
                        projectId = projectId,
                        userId = constants.userId,
                        name = projectName
                    )
                )
                .await()
            projectName = ""
            show = false
        }
    }

    div {
        className = ClassName("add-project")
        if (show) {
            div {
                className = ClassName("add-project__input")
                input {
                    className = ClassName("add-project__name")
                    value = projectName
                    onChange = {
                        projectName = it.target.value
                    }
                    type = InputType.text
                    placeholder = "Name your project"
                }
                button {
                    className = ClassName("add-project__submit")
                    type = ButtonType.button
                    onClick = {
                        CoroutineScope(SupervisorJob()).launch {
                            addProject()
                        }
                    }
                    +"Add Project"
                }
                span {
                    className = ClassName("add-project__cancel")
                    onClick = {
                        show = false
                    }
                }
            }
        }
        span {
            className = ClassName("add-project__plus")
            +"+"
        }
        span {
            className = ClassName("add-project__text")
            onClick = {
                show = !show
            }
            +"Add Project"
        }
    }

}

external interface AddProjectProps : Props {
    var shouldShow: Boolean
}