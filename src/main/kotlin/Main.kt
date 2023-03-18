import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
    val container = document.getElementById("root") ?: error("")
    createRoot(container).render(App.create())
}