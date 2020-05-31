package com.funrisestudio.avengers.data.source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

class FirestoreQuery {

    inline fun <reified T> get(documentReference: DocumentReference): Task<T?> {
        return documentReference.get().continueWith {
            it.result?.toObject(T::class.java)
        }
    }

    inline fun <reified T> get(collectionReference: CollectionReference): Task<List<T?>?> {
        return collectionReference.get().continueWith { queryRes ->
            queryRes.result?.documents?.map {
                it.toObject(T::class.java)
            }
        }
    }

    inline fun <reified T> getAndMapIds(
        collectionReference: CollectionReference,
        crossinline transform: (id: String, entity: T?) -> T?
    ): Task<List<T?>?> {
        return collectionReference.get().continueWith { queryRes ->
            queryRes.result?.documents?.map { doc ->
                val entity = doc.toObject(T::class.java)
                transform(doc.id, entity)
            }
        }
    }

    fun set(
        documentReference: DocumentReference,
        data: Any,
        merge: Boolean = false
    ): Task<Void> {
        return if (!merge) {
            documentReference.set(data)
        } else {
            documentReference.set(data, SetOptions.merge())
        }
    }

    fun add(
        collectionReference: CollectionReference,
        data: Any
    ): Task<DocumentReference> {
        return collectionReference.add(data)
    }

}