package com.example.ch02_latihan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DownloaderScreen(modifier: Modifier = Modifier) {
    var progress by remember { mutableFloatStateOf(0f) }
    var status by remember { mutableStateOf("Siap") }

    val scope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Status: $status",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (job?.isActive == true) return@Button

                    status = "Mengunduh..."
                    progress = 0f

                    job = scope.launch {
                        try {
                            for (i in 1..100) {
                                delay(50)
                                progress = i / 100f
                            }
                            status = "Selesai"
                        } catch (e: CancellationException) {
                            status = "Dibatalkan"
                            progress = 0f // <-- Reset progress bar ke 0
                            throw e
                        }
                    }
                },
                enabled = job?.isActive != true
            ) {
                Text("Unduh")
            }

            Button(
                onClick = {
                    job?.cancel()
                },
                enabled = job?.isActive == true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Batal")
            }
        }
    }
}