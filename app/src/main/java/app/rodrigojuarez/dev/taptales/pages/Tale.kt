package app.rodrigojuarez.dev.taptales.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.rodrigojuarez.dev.taptales.model.LocalAppDatabase
import app.rodrigojuarez.dev.taptales.model.Tale
import app.rodrigojuarez.dev.taptales.ui.theme.CustomOrange
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Tale(slug: String?) {
    val db = LocalAppDatabase.current
    var tale by remember { mutableStateOf<Tale?>(null) }

    GlobalScope.launch(Dispatchers.IO) {
        if (slug != null) {
            val taleBySlug = db.taleDao().getTaleBySlug(slug)
            GlobalScope.launch(Dispatchers.Main) {
                tale = taleBySlug
            }
        }
    }

    if (tale == null) {
        CircularProgressIndicator(color = CustomOrange)
    } else {
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                contentAlignment = Alignment.BottomStart
            ) {
                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Text(
                        text = tale!!.title,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    )
                }
            }
            Column {

            }
            tale!!.paragraphs.forEach {
                Text(
                    text = it,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}

