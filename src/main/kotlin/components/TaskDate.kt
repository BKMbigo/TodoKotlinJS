package components

import csstype.ClassName
import js.RegPaperPlane
import js.SpaceShuttle
import js.Sun
import kotlinx.datetime.internal.JSJoda.Clock
import kotlinx.datetime.internal.JSJoda.LocalDate
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul

val TaskDate = FC<TaskDateProps> { props ->
    if (props.showTaskDate) {
        div {
            className = ClassName("task-date")
            ul {
                className = ClassName("task-date__list")
                li {
                    onClick = {
                        props.setShowTaskDate(false)
                        props.setTaskDate(
                            LocalDate.now(Clock.systemDefaultZone()).toEpochDay().toInt()
                        )
                    }

                    span {
                        SpaceShuttle {}
                    }
                    span {
                        +"Today"
                    }
                }
                li {
                    onClick = {
                        props.setShowTaskDate(false)
                        props.setTaskDate(
                            LocalDate.now(Clock.systemDefaultZone()).toEpochDay().toInt() + 1
                        )
                    }

                    span {
                        Sun {}
                    }
                    span {
                        +"Tomorrow"
                    }
                }
                li {
                    onClick = {
                        props.setShowTaskDate(false)
                        props.setTaskDate(
                            LocalDate.now(Clock.systemDefaultZone()).toEpochDay().toInt() + 7
                        )
                    }

                    span {
                        RegPaperPlane {}
                    }
                    span {
                        +"Next Week"
                    }
                }
            }
        }

    }
}

external interface TaskDateProps : Props {
    var setTaskDate: (Int) -> Unit
    var showTaskDate: Boolean
    var setShowTaskDate: (Boolean) -> Unit
}