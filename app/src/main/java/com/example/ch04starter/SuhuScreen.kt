package com.example.ch04starter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch04starter.ui.theme.Ch04StarterTheme
import java.util.Locale

// ============================================================================
// TODO Pertemuan 4 — SuhuScreen (soal #2 di Tugas Pertemuan 4)
//
// Kalkulator konversi suhu Celsius <-> Fahrenheit <-> Kelvin, memakai
// `derivedStateOf` — sama seperti pola `bmi` di BmiScreen, tapi di sini
// hasil derivasinya dipakai sebagai TAMPILAN saja (read-only), bukan input.
//
// Rumus yang perlu diimplementasikan:
//   F = C * 9/5 + 32
//   K = C + 273.15
//
// [ ] 2a. Field "Celsius" di bawah masih kosong logikanya — parse teks
//         input jadi Float (hati-hati input tidak valid / kosong!)
// [ ] 2b. Hitung `fahrenheit` dan `kelvin` dari `celsius` memakai
//         `derivedStateOf`, lalu tampilkan di kedua Text di bawah field
// [ ] (Tantangan, opsional) Jadikan ketiga field bisa DIKETIK bebas —
//         Fahrenheit atau Kelvin pun boleh jadi sumber input. Hati-hati:
//         kalau ketiganya saling mengisi satu sama lain secara langsung,
//         bisa terjadi UPDATE MELINGKAR (circular update). Coba pikirkan
//         cara melacak "field mana yang sedang aktif diketik" sebagai
//         satu-satunya sumber kebenaran (single source of truth, ingat
//         slide UDF), baru dua field lain murni derived dari situ.
// ============================================================================

@Composable
fun SuhuScreen() {
    var celsiusText by rememberSaveable { mutableStateOf("") }
    var fahrenheitText by rememberSaveable { mutableStateOf("") }
    var kelvinText by rememberSaveable { mutableStateOf("") }
    var lastActive by rememberSaveable { mutableStateOf("C") }

    // TODO 2a: ganti 0f di bawah dengan hasil parse `celsiusText` yang aman
    //          (mis. `celsiusText.toFloatOrNull() ?: 0f`)
    val baseCelsius by remember {
        derivedStateOf {
            when (lastActive) {
                "C" -> celsiusText.toFloatOrNull() ?: 0f
                "F" -> {
                    val f = fahrenheitText.toFloatOrNull() ?: 32f
                    (f - 32f) * 5f / 9f
                }
                "K" -> {
                    val k = kelvinText.toFloatOrNull() ?: 273.15f
                    k - 273.15f
                }
                else -> 0f
            }
        }
    }

    // TODO 2b: bungkus kalkulasi F dan K dengan `remember { derivedStateOf { ... } }`
    //          seperti pola `bmi` di BmiScreen.
    val calcFahrenheit by remember { derivedStateOf { baseCelsius * 9f / 5f + 32f } }
    val calcKelvin by remember { derivedStateOf { baseCelsius + 273.15f } }

    Column(
        modifier            = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text  = "Konversi Suhu",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = if (lastActive == "C") celsiusText else String.format(Locale.US, "%.1f", baseCelsius),
            onValueChange = {
                celsiusText = it
                lastActive = "C"
            },
            label = { Text("Celsius (°C)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = if (lastActive == "F") fahrenheitText else String.format(Locale.US, "%.1f", calcFahrenheit),
            onValueChange = {
                fahrenheitText = it
                lastActive = "F"
            },
            label = { Text("Fahrenheit (°F)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = if (lastActive == "K") kelvinText else String.format(Locale.US, "%.1f", calcKelvin),
            onValueChange = {
                kelvinText = it
                lastActive = "K"
            },
            label = { Text("Kelvin (K)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuhuScreenPreview() {
    Ch04StarterTheme { SuhuScreen() }
}
