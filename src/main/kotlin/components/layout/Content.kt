package components.layout

import components.Tasks
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.section

val Content = FC<Props> {
    section {
        className = ClassName("content")
        Sidebar {}
        Tasks {}
    }
}