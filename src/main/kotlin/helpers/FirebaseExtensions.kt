package helpers

import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.Query

import dev.gitlive.firebase.firestore.where as fWhere

fun <T : Query> T.where(field: String, equalTo: Any?) = fWhere(field, equalTo) as T
fun <T : Query> T.where(path: FieldPath, equalTo: Any?) = fWhere(path, equalTo) as T
fun <T : Query> T.where(field: String, equalTo: DocumentReference) = fWhere(field, equalTo) as T
fun <T : Query> T.where(path: FieldPath, equalTo: DocumentReference) = fWhere(path, equalTo) as T
fun <T : Query> T.where(field: String, lessThan: Any? = null, greaterThan: Any? = null, arrayContains: Any? = null) =
    fWhere(field, lessThan, greaterThan, arrayContains) as T

fun <T : Query> T.where(path: FieldPath, lessThan: Any? = null, greaterThan: Any? = null, arrayContains: Any? = null) =
    fWhere(path, lessThan, greaterThan, arrayContains) as T

fun <T : Query> T.where(field: String, inArray: List<Any>? = null, arrayContainsAny: List<Any>? = null) =
    fWhere(field, inArray, arrayContainsAny) as T

fun <T : Query> T.where(path: FieldPath, inArray: List<Any>? = null, arrayContainsAny: List<Any>? = null) =
    fWhere(path, inArray, arrayContainsAny) as T