package app.rodrigojuarez.dev.taptales.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import app.rodrigojuarez.dev.taptales.ui.theme.*

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                color = StolenYellow,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = dialogText,
                color = Color.White
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmation,
                colors = ButtonDefaults.textButtonColors(contentColor = StolenYellow)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(contentColor = StolenYellow)
            ) {
                Text("Dismiss")
            }
        },
        containerColor = StolenNight,
        textContentColor = Color.White,
    )
}
