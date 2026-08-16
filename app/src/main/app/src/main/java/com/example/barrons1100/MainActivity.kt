package com.example.barrons1100

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

data class Collocation(
    val textEn: String,
    val textFa: String,
    val phoneticFa: String
)

data class WordModel(
    val id: Int,
    val word: String,
    val phonetic: String,
    val definitionEn: String,
    val translationFa: String,
    val exampleSentence: String,
    val collocations: List<Collocation>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BarronsApp()
                }
            }
        }
    }
}

@Composable
fun BarronsApp() {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
            }
        }
    }

    val sampleWords = listOf(
        WordModel(
            id = 1,
            word = "Avid",
            phonetic = "/ˈæv.ɪd/",
            definitionEn = "Eager, extremely desirous, enthusiastic.",
            translationFa = "مشتاق، پرشور، علاقه‌مند",
            exampleSentence = "An avid reader of science fiction.",
            collocations = listOf(
                Collocation("Avid reader", "کتاب‌خوان پرشور", "اَوید ریدِر"),
                Collocation("Avid collector", "کلکسیونر مشتاق", "اَوید کالِکتُر")
            )
        )
    )

    val currentWord = sampleWords[0]

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "1100 Words Barron's - Week 1 (Day 1)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp).fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = currentWord.word, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { tts?.speak(currentWord.word, TextToSpeech.QUEUE_FLUSH, null, null) }) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Listen")
                    }
                }

                Text(text = currentWord.phonetic, color = Color.Gray, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(text = "English Definition:", fontWeight = FontWeight.Bold)
                Text(text = currentWord.definitionEn)
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Persian Translation:", fontWeight = FontWeight.Bold)
                Text(text = currentWord.translationFa, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Common Collocations:", fontWeight = FontWeight.Bold)
                currentWord.collocations.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = item.textEn, fontWeight = FontWeight.SemiBold)
                            Text(text = item.phoneticFa, fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(text = item.textFa, fontSize = 14.sp)
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Review Again")
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("I Know This")
            }
        }
    }
}
