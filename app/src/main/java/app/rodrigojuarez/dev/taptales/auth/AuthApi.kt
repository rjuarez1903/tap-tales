package app.rodrigojuarez.dev.taptales.auth

import app.rodrigojuarez.dev.taptales.model.Tale
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signUp")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @POST("auth/signIn")
    suspend fun signIn(
        @Body request: AuthRequest
    ): TokenResponse

    @POST("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    ): Tale
}