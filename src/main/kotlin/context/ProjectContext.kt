package context

import data.Project
import react.createContext

val ProjectsContext = createContext<List<Project>>(emptyList())