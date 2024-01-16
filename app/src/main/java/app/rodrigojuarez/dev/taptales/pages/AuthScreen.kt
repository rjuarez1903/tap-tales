package app.rodrigojuarez.dev.taptales.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.rodrigojuarez.dev.taptales.AuthUiEvent
import app.rodrigojuarez.dev.taptales.auth.AuthResult
import app.rodrigojuarez.dev.taptales.model.MainViewModel
import app.rodrigojuarez.dev.taptales.ui.theme.CustomOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    navHostController.navigate("home") {
                        popUpTo("authScreen") {
                            inclusive = true
                        }
                    }
                }

                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.signInUsername,
                onValueChange = { viewModel.onEvent((AuthUiEvent.SignInUsernameChanged(it))) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CustomOrange,
                    unfocusedBorderColor = CustomOrange,
                    textColor = Color.White
                ),
                placeholder = {
                    Text("Enter your email", color = Color.Gray)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.signInPassword,
                onValueChange = { viewModel.onEvent((AuthUiEvent.SignInPasswordChanged(it))) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CustomOrange,
                    unfocusedBorderColor = CustomOrange,
                    textColor = Color.White
                ),
                placeholder = {
                    Text("Enter your password", color = Color.Gray)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onEvent(AuthUiEvent.SignIn) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In")
            }
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = state.signUpUsername,
                onValueChange = { viewModel.onEvent((AuthUiEvent.SignUpUsernameChanged(it))) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CustomOrange,
                    unfocusedBorderColor = CustomOrange,
                    textColor = Color.White
                ),
                placeholder = {
                    Text("Enter your email", color = Color.Gray)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.signUpPassword,
                onValueChange = { viewModel.onEvent((AuthUiEvent.SignUpPasswordChanged(it))) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CustomOrange,
                    unfocusedBorderColor = CustomOrange,
                    textColor = Color.White
                ),
                placeholder = {
                    Text("Enter your password", color = Color.Gray)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onEvent(AuthUiEvent.SignUp) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    Modifier.size(72.dp),
                    color = CustomOrange,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}
