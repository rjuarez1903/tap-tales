package app.rodrigojuarez.dev.taptales.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.rodrigojuarez.dev.taptales.ui.theme.Night
import app.rodrigojuarez.dev.taptales.ui.theme.CustomOrange
import app.rodrigojuarez.dev.taptales.ui.theme.CustomYellow

@Composable
fun CustomSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, CustomYellow, RoundedCornerShape(36.dp))
                    .clip(RoundedCornerShape(36.dp))
                    .background(Night)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomYellow,
                        textAlign = TextAlign.Center
                    )
                    data.visuals.actionLabel?.let { actionLabel ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { data.performAction() },
                            colors = ButtonDefaults.buttonColors(containerColor = CustomOrange),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(actionLabel, color = Color.White)
                        }
                    }
                }
            }
        }
    )
}


