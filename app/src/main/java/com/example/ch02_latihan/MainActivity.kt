package com.example.ch02_latihan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ch02_latihan.ui.theme.Ch02latihanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Ch02latihanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Memanggil fungsi Menu Utama
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // State untuk mencatat tugas yang sedang dipilih (Default: 1)
    var tugasAktif by remember { mutableIntStateOf(1) }

    Column(modifier = modifier.fillMaxSize()) {
        // --- TOMBOL NAVIGASI ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { tugasAktif = 1 },
                // Ubah warna tombol jika sedang aktif
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (tugasAktif == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Tugas 1")
            }

            Button(
                onClick = { tugasAktif = 2 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (tugasAktif == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Tugas 2")
            }
        }

        // Pembatas
        HorizontalDivider(thickness = 2.dp)

        // --- KONTEN TUGAS ---
        Box(modifier = Modifier.fillMaxSize()) {
            if (tugasAktif == 1) {
                TampilDataMahasiswa()
            } else {
                DownloaderScreen()
            }
        }
    }
}