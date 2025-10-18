package com.example.projeto_integrador

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracaoQuadroScreen(navController: NavController) {
    // Simulação de dados (depois vem do BD)
    var membros by remember {
        mutableStateOf(
            mutableListOf("Bryan Oliveira", "Ana Beatriz", "Daniel Costa")
        )
    }
    var statusAtivo by remember { mutableStateOf(true) }
    var nomeQuadro by remember { mutableStateOf("Projeto Integrador") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = Color(0xFFE8F5E9),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Configurações do Quadro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E7D32)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFE8F5E9))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Membros
            item {
                SectionCard(
                    titulo = "Membros do Quadro",
                    icone = Icons.Outlined.Group
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        membros.forEach { membro ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF4CAF50)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        membro.take(2).uppercase(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    membro,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1B5E20),
                                    fontSize = 15.sp
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                // Simula adicionar membro (futuro: chamar BD)
                                scope.launch {
                                    membros.add("Novo Membro ${membros.size + 1}")
                                    snackbarHostState.showSnackbar("Novo membro adicionado!")
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF2E7D32)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text("Adicionar Membro")
                        }
                    }
                }
            }

            // Sobre este quadro
            item {
                SectionCard(
                    titulo = "Sobre este Quadro",
                    icone = Icons.Outlined.Info
                ) {
                    Text(
                        "Nome: $nomeQuadro",
                        color = Color(0xFF1B5E20),
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Descrição: Quadro destinado à organização de tarefas e progresso do projeto.",
                        color = Color(0xFF388E3C),
                        fontSize = 14.sp
                    )
                }
            }

            // Status de tarefas
            item {
                SectionCard(
                    titulo = "Status de Tarefas",
                    icone = Icons.Outlined.List
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Mostrar status 'Concluído' nos cartões",
                            color = Color(0xFF1B5E20),
                            fontSize = 15.sp
                        )
                        Switch(
                            checked = statusAtivo,
                            onCheckedChange = { statusAtivo = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF66BB6A),
                                uncheckedThumbColor = Color.LightGray
                            )
                        )
                    }
                }
            }

            // Configurações do quadro
            item {
                SectionCard(
                    titulo = "Configurações do Quadro",
                    icone = Icons.Outlined.Settings
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        SectionItem(
                            titulo = "Renomear Quadro",
                            icone = Icons.Default.Edit,
                            onClick = {
                                // Simula renomear (futuro: abrir Dialog e atualizar no BD)
                                scope.launch {
                                    nomeQuadro = "Novo Nome do Quadro"
                                    snackbarHostState.showSnackbar("Quadro renomeado com sucesso!")
                                }
                            }
                        )
                        SectionItem(
                            titulo = "Excluir Quadro",
                            icone = Icons.Default.Delete,
                            onClick = {
                                // Simula exclusão
                                scope.launch {
                                    snackbarHostState.showSnackbar("Quadro excluído (simulação).")
                                }
                            }
                        )
                    }
                }
            }

            // Atividade recente
            item {
                SectionCard(
                    titulo = "Atividade Recente",
                    icone = Icons.Outlined.Timeline
                ) {
                    val atividades = listOf(
                        "Bryan adicionou uma nova tarefa",
                        "Ana marcou uma tarefa como concluída",
                        "Daniel comentou em 'Design da Tela Principal'"
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        atividades.forEach {
                            Text("• $it", color = Color(0xFF1B5E20), fontSize = 14.sp)
                        }
                    }
                }
            }

            // Sincronização
            item {
                SectionItem(
                    titulo = "Sincronizar Dados",
                    icone = Icons.Default.Sync,
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Sincronização concluída com sucesso!")
                        }
                    }
                )
            }
        }
    }
}

/* -------------------------
   Componentes reutilizáveis
   ------------------------- */

@Composable
fun SectionCard(
    titulo: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icone,
                    contentDescription = titulo,
                    tint = Color(0xFF2E7D32)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1B5E20)
                )
            }
            Spacer(Modifier.height(12.dp))
            conteudo()
        }
    }
}

@Composable
fun SectionItem(
    titulo: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = Color(0xFFF1F8E9),
        label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icone, contentDescription = null, tint = Color(0xFF2E7D32))
        Spacer(Modifier.width(10.dp))
        Text(
            text = titulo,
            color = Color(0xFF1B5E20),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
