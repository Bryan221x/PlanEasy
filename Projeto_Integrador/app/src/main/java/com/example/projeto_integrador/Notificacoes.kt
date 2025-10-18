package com.example.projeto_integrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NotificacoesScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFA7EABD), Color(0xFF0D7037))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Título
            Text(
                text = "Notificações",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cartão de notificação (exemplo)
            NotificationCard(
                titulo = "Documentação e Planejamento",
                descricao = "Entrega: 27 de setembro de 2025 às 08:00",
                data = "26 de setembro, 08:00"
            )

            NotificationCard(
                titulo = "Novo membro adicionado",
                descricao = "Ana Beatriz pode ver o quadro Projeto Integrador Mobile.",
                data = "22 de setembro, 21:29"
            )

            NotificationCard(
                titulo = "Novo membro adicionado",
                descricao = "Douglas pode ver o quadro Projeto Integrador Mobile.",
                data = "21 de setembro, 21:58"
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("inicio") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text("Voltar ao Início", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun NotificationCard(titulo: String, descricao: String, data: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20).copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = descricao,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = data,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 13.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
