package com.example.projeto_integrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PesquisaScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFE8F5E9))
                .padding(16.dp)
        ) {

            // ---------- Top Bar ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pesquisar Quadros",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- Barra de Pesquisa ----------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF81C784), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Buscar por quadros...",
                                    color = Color(0xFFE0E0E0),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------- Resultados ----------
            Text(
                text = if (searchText.isEmpty()) "Sugestões recentes" else "Resultados para \"$searchText\"",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lista simulada de resultados (pode vir do seu banco futuramente)
            val resultados = listOf(
                "Projeto Verde Sustentável" to "Quadro sobre ações ecológicas",
                "Trabalho de Programação" to "Organização do código Kotlin",
                "Estudos de UI/UX" to "Design mobile minimalista"
            ).filter {
                it.first.contains(searchText, ignoreCase = true) || searchText.isEmpty()
            }

            if (resultados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhum resultado encontrado", color = Color.Gray)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    resultados.forEach { (titulo, descricao) ->
                        ResultadoCard(titulo, descricao)
                    }
                }
            }
        }
    }
}

@Composable
fun ResultadoCard(titulo: String, descricao: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = descricao,
                color = Color(0xFF2E7D32),
                fontSize = 14.sp
            )
        }
    }
}
