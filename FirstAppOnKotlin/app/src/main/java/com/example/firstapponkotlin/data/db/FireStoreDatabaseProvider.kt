package com.example.firstapponkotlin.data.db

import android.util.Log
import com.example.firstapponkotlin.error.NoAuthException
import com.example.firstapponkotlin.model.Note
import com.example.firstapponkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "notes"
private const val USERS_COLLECTION = "users"
const val TAG = "FireStoreDatabase"

class FireStoreDatabaseProvider : DatabaseProvider {

    private val db = FirebaseFirestore.getInstance()
    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    private val result = MutableStateFlow<List<Note>?>(null)
    private var subscribedOnDB = false

    override fun observeNotes(): Flow<List<Note>> {
        if (!subscribedOnDB) subscribeForDBChanging()
        return result.filterNotNull()
    }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }


    override suspend fun addOrReplace(newNote: Note) {

        suspendCoroutine { continuation ->
            handleNotesReference(
                {
                    getUserNotesCollection().document(newNote.id.toString()).set(newNote)
                        .addOnSuccessListener {
                            Log.d(TAG, "Note $newNote is saved")
                            continuation.resumeWith(Result.success(it))
                        }.addOnFailureListener {
                            Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
                            continuation.resumeWithException(it)
                        }
                }, {
                    Log.e(TAG, "Error getting reference note $newNote, message: ${it.message}")
                    continuation.resumeWithException(it)
                }
            )

        }
//        val result = MutableLiveData<Result<Note>>()
//
//        handleNotesReference(
//            {
//                getUserNotesCollection().document(newNote.id.toString()).set(newNote)
//                    .addOnSuccessListener {
//                        Log.d(TAG, "Note $newNote is saved")
//                        result.value = Result.success(newNote)
//                    }.addOnFailureListener {
//                        Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
//                        result.value = Result.failure(it)
//                    }
//
//            }, {
//                Log.e(TAG, "Error getting reference note $newNote, message: ${it.message}")
//                result.value = Result.failure(it)
//            }
//        )

    }

    override suspend fun deleteNote(note: Note) {

        suspendCoroutine { continuation ->
            getUserNotesCollection().document(note.id.toString()).delete()
                .addOnSuccessListener {
                    Log.d(TAG, "Note $note is deleted")
                    continuation.resumeWith(Result.success(it))
                }.addOnFailureListener {
                    Log.e(TAG, "Error deleting note $note, message: ${it.message}")
                    continuation.resumeWithException(it)
                }
        }

//        val result = MutableLiveData<Result<Note>>()
//
//        handleNotesReference({
//            getUserNotesCollection().document(note.id.toString()).delete()
//                .addOnSuccessListener {
//                    Log.d(TAG, "Note $note is deleted")
//                    result.value = Result.success(note)
//                }.addOnFailureListener {
//                    Log.e(TAG, "Error deleting note $note, message: ${it.message}")
//                    result.value = Result.failure(it)
//                }
//        })
    }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    private fun subscribeForDBChanging() {

        handleNotesReference(
            {
                getUserNotesCollection().addSnapshotListener { value, error ->
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

            }, {
                Log.e(TAG, "Error getting reference while subscribed for notes")
            }
        )
    }

    private inline fun handleNotesReference(
        referenceHandler: (CollectionReference) -> Unit,
        exceptionHandler: (Throwable) -> Unit = {}
    ) {
        kotlin.runCatching { getUserNotesCollection() }
            .fold(referenceHandler, exceptionHandler)
    }
}