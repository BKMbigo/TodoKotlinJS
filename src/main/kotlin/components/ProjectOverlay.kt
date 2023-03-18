package components

import context.ProjectsContext
import csstype.ClassName
import data.Project
import react.FC
import react.Props
import react.dom.aria.AriaRole
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.key
import react.useContext

val ProjectOverlay = FC<ProjectOverlayProps> { props ->
    val projects = useContext(ProjectsContext)

    if (projects.isNotEmpty() && props.showProjectOverlay) {
        div {
            className = ClassName("project-overlay")
            ul {
                className = ClassName("project-overlay__list")
                projects.map { project ->
                    li {
                        key = project.projectId
                        div {
                            role = AriaRole.button
                            tabIndex = 0
                            onClick = {
                                props.setProject(project)
                                props.setShowProjectOverlay(false)
                            }
                            +project.name
                        }
                    }
                }
            }
        }
    }
}

external interface ProjectOverlayProps : Props {
    var setProject: (Project) -> Unit
    var showProjectOverlay: Boolean
    var setShowProjectOverlay: (Boolean) -> Unit
}