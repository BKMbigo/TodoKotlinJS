package components

import constants.SelectedProject
import context.SelectedProjectContext
import context.getValue
import context.setValue
import csstype.ClassName
import data.Project
import dev.gitlive.firebase.firebase
import js.TrashIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.aria.AriaRole
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.useContext
import react.useState

val IndividualProject = FC<IndividualProjectProps> { projectProp ->
    var selectedProjectValue by useContext(SelectedProjectContext)

    var showConfirmation by useState(false)

    suspend fun deleteProject(docId: String) {
        firebase.firestore()
            .collection("projects")
            .doc(docId)
            .delete()
            .await()
            .apply {
                selectedProjectValue = SelectedProject.CollatedTask.INBOX
            }

    }


    div {
        span {
            className = ClassName("sidebar__dot")
            +"•"
        }

        span {
            className = ClassName("sidebar__project-name")
            +projectProp.project.name
        }
        span {
            className = ClassName("sidebar__project-delete")
            onClick = {
                showConfirmation = !showConfirmation
            }
            role = AriaRole.button

            TrashIcon {}
            if (showConfirmation) {
                div {
                    className = ClassName("project-delete-modal")
                    div {
                        className = ClassName("project-delete-modal__inner")
                        p {
                            +"Are you sure you want to delete this project?"
                        }
                        button {
                            type = ButtonType.button
                            onClick = {
                                CoroutineScope(SupervisorJob()).launch {
                                    deleteProject(projectProp.project.projectId)
                                }
                            }
                            +"Delete"
                        }
                        span {
                            onClick = {
                                showConfirmation = !showConfirmation
                            }
                            +"Cancel"
                        }
                    }
                }
            }
        }
    }
}

external interface IndividualProjectProps : Props {
    var project: Project
}