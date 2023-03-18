package components.layout

import components.AddTask
import csstype.ClassName
import js.PizzaSlice
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.ul
import react.useState

val Header = FC<HeaderProps> { props ->
    var shouldShowMain by useState(false)
    var showQuickAddTask by useState(false)


    header {
        className = ClassName("header")
        nav {
            div {
                className = ClassName("logo")
                img {
                    src = "/images/logo.png"
                    alt = "Todoist"
                }
            }
            div {
                className = ClassName("settings")
                ul {
                    li {
                        className = ClassName("settings__add")
                        onClick = {
                            showQuickAddTask = true
                            shouldShowMain = true
                        }
                        +"+"
                    }
                    li {
                        className = ClassName("settings__darkmode")
                        onClick = {
                            props.setDarkMode(!props.darkMode)
                        }
                        PizzaSlice {
                            +"Pizza Slice"
                        }
                    }
                }
            }
        }

        AddTask {
            this.shouldShowMain = shouldShowMain
            this.showQuickAddTask = showQuickAddTask
            showAddTaskMain = false
            setShowQuickAddTask = {
                showQuickAddTask = it
            }
        }
    }
}

external interface HeaderProps : Props {
    var darkMode: Boolean
    var setDarkMode: (Boolean) -> Unit
}