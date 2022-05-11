package com.example.a11052022

import android.os.Handler
import android.os.Looper
import com.example.a11052022.StoreContract.*
import com.example.a11052022.StoreContract.Presenter.*
import com.example.a11052022.StoreContract.Presenter.State.*

class StorePresenter(
    private val repository: Repository
) : Presenter {
    private var view: View? = null

    override var state: State = LOADING


    override fun onAttach(view: View) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun load() {
        view?.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                replaceData()
            }, 3_000L

        )
    }

    override fun onDelete(person: Person) {
        view?.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                repository.onDelete(person)
                replaceData()
            }, 1_000L

        )
    }

    override fun showAdd() {
        view?.showAddPerson()
    }


    override fun showAddedPerson(person: Person) {
        repository.showAddedPerson(person)
        replaceData()

    }

    override fun onSelect(person: Person) {
        if (state == LOADING) {
            return
        }
        state = LOADING
        view?.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                view?.showOnSelect(person)
                replaceData()
            }, 1_000L

        )

    }

    override fun onClone(person: Person) {
        if (state == LOADING) {
            return
        }
        state = LOADING
        view?.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                repository.onClone(person)
                replaceData()
            }, 1_000L

        )

    }

    override fun onBottomSheetDialog(person: Person) {
        view?.showBottomSheet(person)
        replaceData()
    }

    override fun showEdit(person: Person) {
        view?.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                view?.showEdit(person)
                replaceData()
            }, 1_000L

        )

    }

    override fun showEditedPerson(person: Person) {
        view?.hideContent()
        repository.showEditedPerson(person)
        replaceData()
    }

    override fun showSelect(person: Person) {
        repository.onSelect(person)
        replaceData()
    }


    private fun replaceData() {
        val persons = repository.getPersons()
        view?.hideProgress()
        view?.showContent(persons)
        state = CONTENT
    }

    companion object {
        fun create(
            repository: Repository
        ) = StorePresenter(repository)
    }

}