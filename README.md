# TODOIST CLONE

This is a Kotlin/JS project showcasing the use of kotlin to build a functional website. The website uses javascript
libraries such as React, React Icons and Firebase. The project is a clone
of [Karl Hadwen Todoist](https://github.com/karlhadwen/todoist).

## Resources

Resources Used in the project include:

#### Javascript

* [React](https://reactjs.org) - ([Kotlin React Wrappers](https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-react))
* [React Icons](https://react-icons.github.io/react-icons/)

#### Kotlin

* [GitLive Firebase Kotlin SDK](https://github.com/GitLiveApp/firebase-kotlin-sdk)

## Pre-requisites

The project requires a firebase project to run. Create a firebase client and add
a [javascript target](https://youtu.be/HgfA4W_VjmI?t=1707). Copy the credentials to any file in constants directory

```kotlin
const val authDomain = ""
const val databaseURL = ""
const val projectId = ""
const val storageBucket = ""
const val messagingSenderId = ""
const val appId = ""

const val userId = "" // Can be a random String
```

The project can be run using IntelliJ IDEA. Run the command

```
./gradlew :browserDevelopmentRun
```

## Contribution

All contributions are welcome. To contribute to the project, clone the repository, and create a pull request.

## More Resources

* [Kotlin/JS](https://kotl.in/js)
* [Kotlin/JS Official Samples](https://kotlinlang.org/docs/js-samples.html)