package app.rodrigojuarez.dev.taptales.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.rodrigojuarez.dev.taptales.R
import app.rodrigojuarez.dev.taptales.ui.composables.AnimatedCreateTaleButton
import app.rodrigojuarez.dev.taptales.ui.theme.Pink80
import app.rodrigojuarez.dev.taptales.ui.theme.CustomYellow

@Composable
fun Home(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_cartoon),
            contentDescription = "Sample",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Tap Tales",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = CustomYellow
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 64.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Create magical and unique stories for your child. Every night is a new story.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Pink80
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AnimatedCreateTaleButton(navHostController)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navHostController.navigate("talesList") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(
                        alpha = 0F,
                    ),
                )
            ) {
                Row {
                    Text(
                        text = "View previous stories",
                        textAlign = TextAlign.Center,
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "View previous stories"
                    )
                }
            }
        }
    }
}

