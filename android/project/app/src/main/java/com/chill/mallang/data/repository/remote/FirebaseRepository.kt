package com.chill.mallang.data.repository.remote

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun firebaseAuthWithGoogle(idToken: String): Flow<FirebaseUser?>

    suspend fun getCurrentUser(): Flow<FirebaseUser?>

    fun getFirebaseInstance(): FirebaseAuth
}
