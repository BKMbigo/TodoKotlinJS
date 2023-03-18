package components

import constants.SelectedProject
import context.ProjectsContext
import context.SelectedProjectContext
import context.getValue
import context.setValue
import csstype.ClassName
import react.*
import react.dom.aria.AriaRole
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li

val Projects = FC<Props> {
    var active by useState("")
    val projects = useContext(ProjectsContext)
    var selectedProject by useContext(SelectedProjectContext)

    if (projects.isNotEmpty()) {
        projects.map { project ->
            li {
                key = project.projectId
                className =
                    if (selectedProject is SelectedProject.Project && (selectedProject as SelectedProject.Project).projectId == project.projectId) {
                        ClassName("active sidebar__project")
                    } else {
                        ClassName("sidebar__project")
                    }
                div {
                    onKeyDown?.let {
                        active = project.projectId
                        selectedProject = SelectedProject.Project(project.projectId)
                    }
                    onClick = {
                        active = project.projectId
                        selectedProject = SelectedProject.Project(project.projectId)
                    }
                    role = AriaRole.button

                    IndividualProject {
                        this.project = project
                    }
                }
            }
        }
    }
}
