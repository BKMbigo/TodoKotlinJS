import components.layout.Content
import components.layout.Header
import constants.SelectedProject
import context.ProjectsContext
import context.SelectedProjectContext
import csstype.ClassName
import data.Project
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.orderBy
import helpers.where
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.main
import react.useEffect
import react.useState

@JsName("require")
external fun requireModule(module: String): dynamic

val App = FC<AppProps> { props ->
    requireModule("./App.scss")
    var selectedProject by useState<SelectedProject>(SelectedProject.CollatedTask.INBOX)
    var projects by useState<List<Project>>(emptyList())

    var darkMode by useState(props.darkModeDefault)


    useEffect(selectedProject) {
        CoroutineScope(SupervisorJob()).launch {
            val unsubscribeList = Firebase.firestore(firebase)
                .collection("projects")
                .where("userId", "Yhmn9NAFXkSCh")
                .orderBy("projectId")
                .get()
            projects = unsubscribeList.documents.map { docSnapshot ->
                docSnapshot.data()
            }
        }
    }

    SelectedProjectContext(selectedProject to { selectedProject = it }) {
        ProjectsContext(projects) {
            main {
                className = if (darkMode) ClassName("darkmode") else ClassName("")
                Header {
                    this.darkMode = darkMode
                    setDarkMode = {
                        darkMode = it
                    }
                }
                Content()
            }
        }
    }
}

external interface AppProps : Props {
    var darkModeDefault: Boolean
}
