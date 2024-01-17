package app.rodrigojuarez.dev.taptales.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import app.rodrigojuarez.dev.taptales.R
import app.rodrigojuarez.dev.taptales.ui.composables.AnimatedCreateTaleButton
import app.rodrigojuarez.dev.taptales.ui.composables.CustomAlertDialog
import app.rodrigojuarez.dev.taptales.model.LocalAppDatabase
import app.rodrigojuarez.dev.taptales.model.Tale
import app.rodrigojuarez.dev.taptales.model.TalesViewModel
import app.rodrigojuarez.dev.taptales.ui.theme.Pink80
import app.rodrigojuarez.dev.taptales.ui.theme.YetAnotherPurple
import app.rodrigojuarez.dev.taptales.ui.theme.CustomYellow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun TalesList(navHostController: NavHostController) {
    val db = LocalAppDatabase.current
    val vm: TalesViewModel = viewModel()
    vm.loadTales(db.taleDao())
    val tales by vm.tales.observeAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (tales?.isNotEmpty() == true) {
            items(tales!!) {
                TaleCard(navHostController, it)
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            item {
                NoTales(navHostController)
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun TaleCard(navHostController: NavHostController, tale: Tale) {
    val db = LocalAppDatabase.current
    val openAlertDialog = remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
    val formattedDate = tale.date.format(dateFormatter)

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(YetAnotherPurple)
    ) {
        Row(modifier = Modifier.padding(end = 8.dp, top = 8.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {  openAlertDialog.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Delete Tale",
                    tint = Color.White
                )
            }
        }
        Text(
            text = tale.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Pink80,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        Text(
            text = " $formattedDate - ${tale.words} words",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "${tale.paragraphs[0]}..",
            modifier = Modifier.padding(16.dp)
        )
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Button(
                onClick = { navHostController.navigate("tale/${tale.slug}") },
            ) {
                Row {
                    Text(text = "Read")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Arrow Forward",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        if (openAlertDialog.value) {
            CustomAlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.taleDao().deleteTale(tale)
                    }
                    openAlertDialog.value = false
                },
                dialogTitle = "Confirm Deletion",
                dialogText = "Are you sure you want to delete this tale? You won't be able to read it anymore"
            )
        }
    }
}

@Composable
fun NoTales(navHostController: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.storyteller),
        contentDescription = "Sample",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 64.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Looks like your magical library of tales is just beginning! ✨",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = Pink80
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Right now, there are no stories to display here. But don't worry, creating your first enchanting tale is just a moment away. Tap on \"Create New Tale\" and embark on a journey of imagination and wonder. ",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = CustomYellow
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AnimatedCreateTaleButton(navHostController)
        }

    }

}