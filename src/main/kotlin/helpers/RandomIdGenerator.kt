package helpers

fun generateRandomId(): String {
    val characters = ('A'..'Z') + ('a'..'z')
    return (1..12).map { characters.random() }.joinToString("")
}