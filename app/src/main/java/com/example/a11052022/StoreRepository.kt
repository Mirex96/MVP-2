package com.example.a11052022

class StoreRepository : StoreContract.Repository {
    private val persons = mutableListOf(
        Person(IdGenerator.generatorId(), "Mary", "28"),
        Person(IdGenerator.generatorId(), "Olga", "28"),
        Person(IdGenerator.generatorId(), "Helga", "28"),
    )

    override fun getPersons(): List<Person> = persons

    override fun onDelete(person: Person) {
        persons.remove(person)
    }

    override fun showAddedPerson(person: Person) {
        persons.add(person)
    }

    override fun onClone(person: Person) {
        persons.add(person)
    }

    override fun showEditedPerson(person: Person) {
        val oldPerson = persons.find { it.id == person.id } ?: return
        val index = persons.indexOf(oldPerson)
        persons[index] = person
    }

    override fun onSelect(person: Person) {
        persons.add(person)
    }


    companion object {
        fun create() = StoreRepository()
    }
}