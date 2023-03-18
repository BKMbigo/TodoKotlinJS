package components

import csstype.ClassName
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.aria.AriaRole
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

val Checkbox = FC<CheckboxProps> { props ->
    suspend fun archiveTask(id: String) =
        Firebase.firestore(firebase)
            .collection("tasks")
            .document(id)
            .update(Pair("archived", true))

    div {
        className = ClassName("checkbox-holder")
        role = AriaRole.button
        onClick = {
            CoroutineScope(SupervisorJob()).launch {
                archiveTask(props.id)
            }
        }

        span {
            className = ClassName("checkbox")
        }
    }


}

external interface CheckboxProps : Props {
    var id: String
}