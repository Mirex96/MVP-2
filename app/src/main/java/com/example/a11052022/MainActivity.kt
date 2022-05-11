package com.example.a11052022


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.a11052022.BottomSheetDialogAction.*
import kotlinx.parcelize.Parcelize

const val KEY_ADD = "KEY_ADD"
const val KEY_REQUEST_ADD = 100
const val KEY_SELECT = "KEY_SELECT"
const val KEY_EDIT = "KEY_EDIT"
const val KEY_REQUEST_EDIT = 101

@Parcelize
data class Person(
    val id: Int,
    val name: String,
    val age: String
) : Parcelable

class MainActivity : AppCompatActivity(), StoreContract.View {
    private lateinit var progressBar: ProgressBar
    private val personRecyclerView by lazy {
        findViewById<RecyclerView>(R.id.personRecyclerView)
    }
    private lateinit var personAdd: ImageView
    private val presenter: StoreContract.Presenter by lazy {
        StorePresenter.create(StoreRepository.create())
    }
    private val adapter = PersonAdapter(
        presenter::onSelect,
        presenter::onBottomSheetDialog,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        personAdd = findViewById(R.id.onAdd)
        personRecyclerView.adapter = adapter
        progressBar = findViewById(R.id.progress)

        presenter.onAttach(this)
        presenter.load()

        personAdd.setOnClickListener {
            presenter.showAdd()
        }

        supportFragmentManager
            .setFragmentResultListener(KEY_REQUEST_BS, this) { _, bundle ->
                val person = bundle.getParcelable<Person>(BUNDLE_KEY_PERSON)
                    ?: return@setFragmentResultListener
                when (bundle.getSerializable(KEY_BUNDLE_TYPE) as BottomSheetDialogAction) {
                    CLONE -> {
                        presenter.onClone(person)
                    }
                    DELETE -> {
                        presenter.onDelete(person)
                    }
                    EDIT -> {
                        presenter.showEdit(person)
                    }
                }

            }
    }

    override fun showProgress() {
        progressBar.isVisible = true
    }

    override fun hideProgress() {
        progressBar.isVisible = false
    }

    override fun showContent(person: List<Person>) {
        personRecyclerView.isVisible = true
        personAdd.isVisible = true
        adapter.setData(person)
    }

    override fun hideContent() {
        personRecyclerView.isVisible = false
        personAdd.isVisible = false
    }

    override fun showAddPerson() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, KEY_REQUEST_ADD)
    }

    override fun showOnSelect(person: Person) {
        val intent = Intent(this, SelectActivity::class.java)
        intent.putExtra(KEY_SELECT, person)
        startActivity(intent)
    }

    override fun showBottomSheet(person: Person) {
        BottomSheetDialog.show(person, supportFragmentManager)
    }

    override fun showEdit(person: Person) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(KEY_EDIT, person)
        startActivityForResult(intent, KEY_REQUEST_EDIT)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KEY_REQUEST_ADD && resultCode == RESULT_OK && data != null) {
            val addPerson = data.getParcelableExtra<Person>(KEY_ADD) ?: return
            presenter.showAddedPerson(addPerson)
        } else if (requestCode == KEY_REQUEST_EDIT && resultCode == RESULT_OK && data != null) {
            val editPerson = data.getParcelableExtra<Person>(KEY_EDIT) ?: return
            presenter.showEditedPerson(editPerson)

        }
    }

}