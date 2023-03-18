package components.layout

import components.AddProject
import components.Projects
import constants.SelectedProject
import context.SelectedProjectContext
import context.getValue
import context.setValue
import csstype.ClassName
import js.ChevronDown
import js.Inbox
import js.RegCalendar
import js.RegCalendarAlt
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.useContext
import react.useState

val Sidebar = FC<Props> {
    var selectedProject by useContext(SelectedProjectContext)
    var activeTask by useState<SelectedProject>(SelectedProject.CollatedTask.INBOX)
    var showProjects by useState(true)

    console.log("Sidebar Loaded")

    div {
        className = ClassName("sidebar")
        ul {
            className = ClassName("sidebar__generic")
            li {
                className = if (selectedProject == SelectedProject.CollatedTask.INBOX) {
                    ClassName("active")
                } else null
                span {
                    Inbox {
                        +"Inbox"
                    }
                }
                span {
                    +"Inbox"
                }
                onClick = {
                    activeTask = SelectedProject.CollatedTask.INBOX
                    selectedProject = SelectedProject.CollatedTask.INBOX
                    console.log("Inbox Clicked")
                }
            }
            li {
                className = if (selectedProject == SelectedProject.CollatedTask.TODAY) {
                    ClassName("active")
                } else null
                span {
                    RegCalendar {
                        +"Today"
                    }
                }
                span {
                    +"Today"
                }
                onClick = {
                    activeTask = SelectedProject.CollatedTask.TODAY
                    selectedProject = SelectedProject.CollatedTask.TODAY
                    console.log("Today Clicked")
                }
            }
            li {
                className = if (selectedProject == SelectedProject.CollatedTask.NEXT_7) {
                    ClassName("active")
                } else {
                    ClassName("")
                }
                span {
                    RegCalendarAlt {
                        +"Inbox"
                    }
                }
                span {
                    +"Next 7 Days"
                }
                onClick = {
                    activeTask = SelectedProject.CollatedTask.NEXT_7
                    selectedProject = SelectedProject.CollatedTask.NEXT_7
                    console.log("Next 7 Clicked")
                }
            }
        }
        div {
            className = ClassName("sidebar__middle")
            span {
                className = if (!showProjects) {
                    ClassName("hidden-projects")
                } else null
                ChevronDown {}
            }
            h2 {
                +"Projects"
            }
            onClick = {
                showProjects = !showProjects
            }
        }
        ul {
            className = ClassName("sidebar__projects")
            if (showProjects) {
                Projects {}
            }
        }

        if (showProjects) {
            AddProject {}
        }
    }
}

