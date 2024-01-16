package app.rodrigojuarez.dev.taptales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.room.Room
import app.rodrigojuarez.dev.taptales.model.AppDatabase
import app.rodrigojuarez.dev.taptales.model.LocalAppDatabase
import app.rodrigojuarez.dev.taptales.ui.theme.TapTalesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tap-tales"
        ).build()

        setContent {
            TapTalesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    CompositionLocalProvider(LocalAppDatabase provides db) {
                        App()
                    }
                }
            }
        }
    }
}
