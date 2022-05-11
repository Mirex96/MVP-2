package com.example.a11052022

interface StoreContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun showContent(person: List<Person>)
        fun hideContent()
        fun showAddPerson()
        fun showOnSelect(person: Person)
        fun showBottomSheet(person: Person)
        fun showEdit(person: Person)
    }

    interface Presenter {
        var state: State
        fun onAttach(view: View)
        fun onDetach()
        fun load()
        fun onDelete(person: Person)
        fun showAdd()
        fun showAddedPerson(person: Person)
        fun onSelect(person: Person)
        fun onClone(person: Person)
        fun onBottomSheetDialog(person: Person)
        fun showEdit(person: Person)
        fun showEditedPerson(person: Person)
        fun showSelect(person: Person)

        enum class State {
            LOADING,
            CONTENT,
            ERROR
        }
    }

    interface Repository {
        fun getPersons(): List<Person>
        fun onDelete(person: Person)
        fun showAddedPerson(person: Person)
        fun onClone(person: Person)
        fun showEditedPerson(person: Person)
        fun onSelect(person: Person)
    }
}