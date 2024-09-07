import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

suspend fun main() {
    val persons = makePersonsList()
    val phones: MutableList<String> = mutableListOf()
    val newPersons: MutableList<Person> = mutableListOf()
    var personinfo: List<Pair<Person, String>> = listOf()
    println("Задан список сотрудников.${persons.joinToString(",\n", "\n", ".")}")
    withContext(newSingleThreadContext("One_thread")) {
        launch {
            getPersonsFlow(persons).collect { it -> newPersons.add(it) }
        }
        launch {
            getPhoneFlow(persons.size).collect { it -> phones.add(it) }
        }
    }
    personinfo = newPersons zip phones
    println("\nРезультат обработки списка:")
    println(personinfo.joinToString("\b;\n", "", "\b."))
}

fun getPersonsFlow(list: List<Person>) = flow {
    for (item in list) {
        emit(item)
    }
}

fun getPhoneFlow(length: Int) = flow {
    repeat(length) {
        emit("+7917" + (10000000..19999999).random().toString().drop(1))
    }
}