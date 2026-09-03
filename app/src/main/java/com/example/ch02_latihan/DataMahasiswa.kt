package com.example.ch02_latihan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 1. Data Class Mahasiswa
data class Mahasiswa(val nim: String, val nama: String, val ipk: Double)

// 2. Sealed Class
sealed class ResultState {
    data class Success(val data: List<Mahasiswa>) : ResultState()
    data class Error(val message: String) : ResultState()
}

// 3. Fungsi Logika
fun getMahasiswaBerprestasi(): ResultState {
    val listMahasiswa = listOf(
        Mahasiswa("22001", "Agung", 3.60),
        Mahasiswa("22002", "Ari", 3.20),
        Mahasiswa("22003", "Candra", 3.91),
        Mahasiswa("22004", "Efan", 3.85),
        Mahasiswa("22005", "Ferdy", 3.10)
    )

    return try {
        val filteredList = listMahasiswa
            .filter { it.ipk >= 3.5 }
            .sortedBy { it.nama }
        if (filteredList.isNotEmpty()) ResultState.Success(filteredList)
        else ResultState.Error("Tidak ada mahasiswa sesuai kriteria")
    } catch (e: Exception) {
        ResultState.Error(e.message ?: "Terjadi kesalahan")
    }
}

// 4. Tampilan UI
@Composable
fun TampilDataMahasiswa(modifier: Modifier = Modifier) {
    val hasil = getMahasiswaBerprestasi() // Panggil data

    Column(modifier = modifier.padding(24.dp)) {
        Text(
            text = "Hasil Tugas 1",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (hasil) {
            is ResultState.Success -> {
                hasil.data.forEach { mhs ->
                    Text(
                        text = "• ${mhs.nama} (NIM: ${mhs.nim}) - IPK: ${mhs.ipk}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            is ResultState.Error -> {
                Text(text = "Error: ${hasil.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}