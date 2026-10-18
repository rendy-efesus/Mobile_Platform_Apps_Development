package com.example.ch03

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch03.ui.theme.Ch03Theme

data class Mahasiswa(val nama: String, val nim: String, val ipk: Double)

val dummyMahasiswa = listOf(
    Mahasiswa("Ali Rahman", "22001", 3.85),
    Mahasiswa("Budi Santoso", "22002", 3.40),
    Mahasiswa("Cici Wulandari", "22003", 3.92),
    Mahasiswa("Dian Pratama", "22004", 2.95),
    Mahasiswa("Eka Fitriani", "22005", 3.75),
    Mahasiswa("Fandi Ahmad", "22006", 3.50),
    Mahasiswa("Gita Permata", "22007", 3.88),
    Mahasiswa("Hendra Kusuma", "22008", 2.80),
    Mahasiswa("Indah Lestari", "22009", 3.65),
    Mahasiswa("Joko Pratama", "22010", 3.20),
    Mahasiswa("Kevin Wijaya", "22011", 3.55),
    Mahasiswa("Lina Marlina", "22012", 3.10),
    Mahasiswa("Mira Anggraini", "22013", 3.95),
    Mahasiswa("Nanda Saputra", "22014", 2.75),
    Mahasiswa("Oki Setiawan", "22015", 3.45),
    Mahasiswa("Putri Ramadhani", "22016", 3.70),
    Mahasiswa("Rizky Hidayat", "22017", 3.30),
    Mahasiswa("Sari Nurhaliza", "22018", 3.82),
    Mahasiswa("Taufik Hakim", "22019", 3.05),
    Mahasiswa("Wulan Sari", "22020", 3.60)
)

val kategoriMahasiswa = listOf("Semua", "IPK >= 3.5", "IPK < 3.5")

@Composable
fun StudentListScreen() {
    var kategoriTerpilih by remember { mutableStateOf(kategoriMahasiswa.first()) }
    val mahasiswaTampil = when (kategoriTerpilih) {
        "IPK >= 3.5" -> dummyMahasiswa.filter { it.ipk >= 3.5 }
        "IPK < 3.5" -> dummyMahasiswa.filter { it.ipk < 3.5 }
        else -> dummyMahasiswa
    }

    Column(modifier = Modifier.fillMaxSize()) {
        KategoriRow(
            kategoriList = kategoriMahasiswa,
            kategoriTerpilih = kategoriTerpilih,
            onKategoriDipilih = { kategoriTerpilih = it }
        )
        DaftarMahasiswa(mahasiswaList = mahasiswaTampil)
    }
}

@Composable
fun KategoriRow(
    kategoriList: List<String>,
    kategoriTerpilih: String,
    onKategoriDipilih: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(kategoriList) { kategori ->
            FilterChip(
                selected = kategori == kategoriTerpilih,
                onClick = { onKategoriDipilih(kategori) },
                label = { Text(kategori) }
            )
        }
    }
}

@Composable
fun DaftarMahasiswa(
    mahasiswaList: List<Mahasiswa>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Daftar Mahasiswa",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(
            items = mahasiswaList,
            key = { it.nim }
        ) { mahasiswa ->
            MahasiswaCard(mahasiswa)
        }
        item {
            Text(
                text = "Total: ${mahasiswaList.size} mahasiswa",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MahasiswaCard(mahasiswa: Mahasiswa) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = mahasiswa.nim,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "IPK ${mahasiswa.ipk}",
                style = MaterialTheme.typography.labelLarge,
                color = if (mahasiswa.ipk >= 3.5) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentListScreenPreview() {
    Ch03Theme {
        StudentListScreen()
    }
}
