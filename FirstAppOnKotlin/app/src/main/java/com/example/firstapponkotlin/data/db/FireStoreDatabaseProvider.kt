package com.example.firstapponkotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstapponkotlin.data.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

private const val NOTES_COLLECTION = "notes"
const val TAG = "FireStoreDatabase"

class FireStoreDatabaseProvider : DatabaseProvider {

    private val db = FirebaseFirestore.getInstance()
    private val notesRepository = db.collection(NOTES_COLLECTION)

    private val result = MutableLiveData<List<Note>>()
    private var subscribedOnDB = false

    override fun observeNotes(): LiveData<List<Note>> {
        if (!subscribedOnDB) subscribeFoDBChanging()

        return result
    }

    override fun addOrReplace(newNote: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()

        notesRepository.document(newNote.id.toString()).set(newNote)
            .addOnSuccessListener {
                Log.d(TAG, "Note $newNote is saved")
                result.value = Result.success(newNote)
            }.addOnFailureListener {
                Log.d(TAG, "Error saving note $newNote, message: ${it.message}")
                result.value = Result.failure(it)
            }
        return result
    }


    private fun subscribeFoDBChanging() {
        notesRepository.addSnapshotListener { value, error ->
            if (error != null) {
                Log.e(TAG, "Observe note exception: $error")
            } else if (value != null) {
                val notes = mutableListOf<Note>()
                for (doc: QueryDocumentSnapshot in value) {
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = notes
            }
        }
        subscribedOnDB = true
    }
}