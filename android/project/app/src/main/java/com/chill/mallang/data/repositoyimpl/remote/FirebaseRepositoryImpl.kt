package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.repository.remote.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(

) : FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    private var user: FirebaseUser? = null
    override suspend fun firebaseAuthWithGoogle(idToken: String): Flow<FirebaseUser?> = flow {

        runCatching {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            user = authResult.user
        }.onSuccess {
            emit(user)
        }.onFailure {
            emit(null)
        }
    }

    override suspend fun getCurrentUser(): Flow<FirebaseUser?> = flow {
        emit(auth.currentUser)
    }

    override fun getFirebaseInstance() = auth
}
