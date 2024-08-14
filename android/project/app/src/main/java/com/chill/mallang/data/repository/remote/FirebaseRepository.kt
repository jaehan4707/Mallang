package com.chill.mallang.data.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun firebaseAuthWithGoogle(idToken: String): Flow<FirebaseUser?>

    suspend fun getCurrentUser(): Flow<FirebaseUser?>

    fun getFirebaseInstance(): FirebaseAuth
}
