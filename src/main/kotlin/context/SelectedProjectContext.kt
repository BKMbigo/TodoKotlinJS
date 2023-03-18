package context

import constants.SelectedProject
import react.createContext
import kotlin.reflect.KProperty

val SelectedProjectContext = createContext<Pair<SelectedProject, (SelectedProject) -> Unit>>()

operator fun <T> Pair<T, (T) -> Unit>.getValue(b: T?, property: KProperty<*>): T = first
operator fun <T> Pair<T, (T) -> Unit>.setValue(b: T?, property: KProperty<*>, t: T) = second(t)