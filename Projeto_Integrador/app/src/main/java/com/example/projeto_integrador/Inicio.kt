package com.example.projeto_integrador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ViewKanban
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue

// -------------------------------
// Modelo de dados (Quadro)
// -------------------------------
data class Quadro(
    val titulo: String,
    val progresso: Float,
    val tarefasPendentes: Int
)

// -------------------------------
// Tela de Início Dinâmica
// -------------------------------
@Composable
fun InicioScreen(navController: NavController) {
    // Lista dinâmica de quadros
    val quadros = remember { mutableStateListOf<Quadro>() }

    // Controle de diálogo
    var showDialog by remember { mutableStateOf(false) }
    var tituloTemp by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Quadro")
            }
        },
        containerColor = Color(0xFFE8F5E9)
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Início",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                IconButton(onClick = {  navController.navigate("configuracaoUsuario") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        tint = Color(0xFF1B5E20)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- Ações Rápidas ----------
            Text(
                text = "Ações rápidas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF81C784), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Criar novo quadro", color = Color.White)
                }
                Button(
                    onClick = { /* ação entrar com código */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Entrar com código", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------- Lista Dinâmica de Quadros ----------
            Text(
                text = "Meus Quadros",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (quadros.isEmpty()) {
                Text(
                    text = "Nenhum quadro criado ainda.",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(quadros) { quadro ->
                        // aqui PASSAMOS o navController para o card
                        QuadroCard(
                            titulo = quadro.titulo,
                            progresso = quadro.progresso,
                            tarefasPendentes = quadro.tarefasPendentes,
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    // ---------- Diálogo para adicionar quadro ----------
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Novo Quadro") },
            text = {
                OutlinedTextField(
                    value = tituloTemp,
                    onValueChange = { tituloTemp = it },
                    label = { Text("Título do quadro") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (tituloTemp.isNotBlank()) {
                        quadros.add(
                            Quadro(
                                titulo = tituloTemp,
                                progresso = 0f,
                                tarefasPendentes = 0
                            )
                        )
                        tituloTemp = ""
                        showDialog = false
                    }
                }) {
                    Text("Adicionar", color = Color(0xFF2E7D32))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// -------------------------------
// Card de Quadro com clique (navegação)
// -------------------------------
@Composable
fun QuadroCard(
    titulo: String,
    progresso: Float,
    tarefasPendentes: Int,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navega para a tela do quadro (rota dinâmica)
                // encode título se tiver espaços (opcional). Aqui usamos diretamente.
                navController.navigate("quadro/${titulo}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ViewKanban,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = titulo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF1B5E20)
                    )
                }
                Text(
                    text = "Ver todos",
                    color = Color(0xFF1B5E20),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // LinearProgressIndicator: uso padrão (se receber warning, é só aviso)
            LinearProgressIndicator(
                progress = progresso,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${(progresso * 100).toInt()}% concluído",
                    color = Color(0xFF1B5E20),
                    fontSize = 14.sp
                )
                Text(
                    text = "$tarefasPendentes tarefas pendentes",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color(0xFF81C784), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// -------------------------------
// Bottom Navigation Bar (implementada aqui para evitar "unresolved reference")
// -------------------------------
@Composable
fun BottomNavBar(navController: NavController) {
    // observa rota atual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color(0xFF2E7D32)) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.ViewKanban, contentDescription = "inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == "inicio" || currentRoute == "inicio" || currentRoute == null,
            onClick = {
                if (currentRoute != "inicio") {
                    navController.navigate("inicio") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            selected = currentRoute == "pesquisa",
            onClick = {
                if (currentRoute != "pesquisa") {
                    navController.navigate("pesquisa") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notificações") },
            label = { Text("Notificações") },
            selected = currentRoute == "notificacoes",
            onClick = {
                if (currentRoute != "notificacoes") {
                    navController.navigate("notificacoes") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}
