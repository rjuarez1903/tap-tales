package app.rodrigojuarez.dev.taptales.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.rodrigojuarez.dev.taptales.composables.CustomSnackbarHost
import app.rodrigojuarez.dev.taptales.model.AI
import app.rodrigojuarez.dev.taptales.model.LocalAppDatabase
import app.rodrigojuarez.dev.taptales.ui.theme.DarkPink
import app.rodrigojuarez.dev.taptales.ui.theme.DuskPurple
import app.rodrigojuarez.dev.taptales.ui.theme.MidnightPurple
import app.rodrigojuarez.dev.taptales.ui.theme.ShadowPurple
import app.rodrigojuarez.dev.taptales.ui.theme.Night
import app.rodrigojuarez.dev.taptales.ui.theme.CustomOrange
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun NewTale(navHostController: NavHostController) {
    val db = LocalAppDatabase.current
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val childName = remember { mutableStateOf("") }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val languages = listOf("English", "Spanish", "Portuguese")
    var language by remember {
        mutableStateOf(languages[0])
    }
    val age = remember { mutableIntStateOf(7) }


    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        containerColor = Night,
        topBar = {
            Text(
                text = "Craft Your Tale",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

        },
        bottomBar = {
            Button(
                onClick = {
                    if (childName.value == "") {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "Please enter your child's name",
                            )
                        }
                    } else {
                        isLoading.value = true
                        GlobalScope.launch(Dispatchers.Default) {
                            val newTale =
                                AI.requestTale(childName.value, age.intValue.toString(), language)
                            if (newTale == null) {
                                //TODO: show snackbar or error message
                            } else {
                                db.taleDao().insertTale(newTale)
                                GlobalScope.launch(Dispatchers.Main) {
                                    navHostController.navigate("talesList")
                                }
                                isLoading.value = false
                            }
                        }
                    }
                },
                enabled = !isLoading.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isLoading.value) CustomOrange else Color.Gray,
                    contentColor = Color.White,
                    disabledContainerColor = CustomOrange,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                if (isLoading.value) ButtonText(text = "Generating Tale...") else ButtonText(text = "Generate Tale")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Night,
                                MidnightPurple,
                                ShadowPurple,
                                DarkPink
                            )
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Child's name",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = childName.value,
                        onValueChange = { childName.value = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = CustomOrange,
                            unfocusedBorderColor = CustomOrange,
                            textColor = Color.White
                        ),
                        placeholder = {
                            Text("Enter your child's name", color = Color.Gray)
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Language",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it }) {

                        OutlinedTextField(
                            value = language,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = CustomOrange,
                                unfocusedBorderColor = CustomOrange,
                                textColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },
                            modifier = Modifier
                                .background(DuskPurple)
                                .exposedDropdownSize(),
                        ) {
                            languages.forEach {
                                DropdownMenuItem(
                                    text = { Text(it, color = Color.White) },
                                    onClick = {
                                        language = it
                                        isExpanded = false
                                    },
                                    modifier = Modifier.background(DuskPurple)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    AgeSlider(age)
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        if (isLoading.value) {
                            CircularProgressIndicator(
                                Modifier.size(72.dp),
                                color = CustomOrange,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun AgeSlider(ageState: MutableState<Int>) {
    var sliderPosition by remember { mutableFloatStateOf(ageState.value.toFloat()) }

    Column {
        Text(text = "Child's age ${sliderPosition.roundToInt()} years old")
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                ageState.value = sliderPosition.roundToInt()
            },
            valueRange = 3f..11f,
            steps = 7,
            colors = colors(
                activeTickColor = CustomOrange,
                activeTrackColor = CustomOrange,
                inactiveTrackColor = Color.DarkGray,
                thumbColor = CustomOrange
            ),
        )
    }
}

@Composable
fun ButtonText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

