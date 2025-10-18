package com.example.projeto_integrador

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ---------------------------
// Modelo de dados
// ---------------------------
data class Tarefa(
    val titulo: String,
    val descricao: String,
    var status: String = "A Fazer"
)

// ---------------------------
// Tela Principal de Quadros (estilo verde claro, coerente com InicioScreen)
// ---------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuadrosScreen(navController: NavController) {
    var tarefas by remember {
        mutableStateOf(
            listOf(
                Tarefa("Definir layout", "Escolher paleta de cores e tipografia", "A Fazer"),
                Tarefa("Criar telas base", "Implementar login e cadastro", "Em andamento"),
                Tarefa("Testar funcionalidades", "Garantir fluidez entre as telas", "Concluído")
            )
        )
    }

    val colunas = listOf("A Fazer", "Em andamento", "Concluído")

    Scaffold(
        containerColor = Color(0xFFE8F5E9), // Fundo verde claro
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Projeto Integrador Mobile",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1B5E20)
                        )
                        Text(
                            "Gerenciamento de Quadros",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Pesquisar */ }) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Pesquisar",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* Notificações */ }) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("configuracao_quadro")
                    }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Configurações",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    tarefas = tarefas + Tarefa("Nova tarefa", "Descrição da tarefa", "A Fazer")
                },
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar tarefa")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFE8F5E9))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                colunas.forEach { coluna ->
                    ColumnTarefas(
                        titulo = coluna,
                        tarefas = tarefas.filter { it.status == coluna },
                        onMoverTarefa = { tarefa, novoStatus ->
                            tarefas = tarefas.map {
                                if (it == tarefa) it.copy(status = novoStatus) else it
                            }
                        },
                        onAdicionarTarefa = { titulo, descricao ->
                            tarefas = tarefas + Tarefa(titulo, descricao, coluna)
                        }
                    )
                }
            }
        }
    }
}

// ---------------------------
// Coluna de tarefas (listas tipo Trello)
// ---------------------------
@Composable
fun ColumnTarefas(
    titulo: String,
    tarefas: List<Tarefa>,
    onMoverTarefa: (Tarefa, String) -> Unit,
    onAdicionarTarefa: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .background(Color(0xFFF1F8E9), RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF81C784), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1B5E20)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(tarefas) { tarefa ->
                TarefaCard(tarefa, onMoverTarefa)
            }

            item {
                OutlinedButton(
                    onClick = { onAdicionarTarefa("Nova tarefa", "Descrição da tarefa") },
                    modifier = Modifier.fillMaxWidth(),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF66BB6A), Color(0xFF43A047))
                        )
                    )
                ) {
                    Text("+ Adicionar cartão", color = Color(0xFF2E7D32))
                }
            }
        }
    }
}

// ---------------------------
// Card individual de tarefa
// ---------------------------
@Composable
fun TarefaCard(tarefa: Tarefa, onMoverTarefa: (Tarefa, String) -> Unit) {
    val bgColor by animateColorAsState(
        targetValue = when (tarefa.status) {
            "A Fazer" -> Color(0xFFFFFFFF)
            "Em andamento" -> Color(0xFFE8F5E9)
            "Concluído" -> Color(0xFFC8E6C9)
            else -> Color(0xFFFFFFFF)
        },
        label = ""
    )

    val scope = rememberCoroutineScope()
    var showHighlight by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .background(if (showHighlight) Color(0xFF81C784).copy(alpha = 0.1f) else Color.Transparent),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            showHighlight = true
            scope.launch {
                delay(200)
                showHighlight = false
            }
        }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = tarefa.titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = tarefa.descricao,
                fontSize = 13.sp,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                DropdownMenuTarefa(tarefa = tarefa, onMoverTarefa = onMoverTarefa)
            }
        }
    }
}

// ---------------------------
// Menu de mover tarefa
// ---------------------------
@Composable
fun DropdownMenuTarefa(tarefa: Tarefa, onMoverTarefa: (Tarefa, String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text("Mover", color = Color(0xFF4CAF50))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true),
            modifier = Modifier.background(Color(0xFFF1F8E9))
        ) {
            listOf("A Fazer", "Em andamento", "Concluído").forEach { status ->
                DropdownMenuItem(
                    text = { Text(status, color = Color(0xFF1B5E20)) },
                    onClick = {
                        onMoverTarefa(tarefa, status)
                        expanded = false
                    }
                )
            }
        }
    }
}
