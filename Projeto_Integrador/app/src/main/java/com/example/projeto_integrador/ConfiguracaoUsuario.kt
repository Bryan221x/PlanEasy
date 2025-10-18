package com.example.projeto_integrador

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracaoUsuarioScreen(navController: NavController) {
    var nome by remember { mutableStateOf("Bryan Oliveira") }
    var email by remember { mutableStateOf("bryan@email.com") }
    var temaEscuro by remember { mutableStateOf(true) }
    var avatarRes by remember { mutableStateOf(R.drawable.ic_account) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showAvatarOptions by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val bgGradientStart = if (temaEscuro) Color(0xFF0B4F2F) else Color(0xFFAEEBC6)
    val bgGradientEnd = if (temaEscuro) Color(0xFF113F2A) else Color(0xFF00C853)
    val animatedStart by animateColorAsState(targetValue = bgGradientStart)
    val animatedEnd by animateColorAsState(targetValue = bgGradientEnd)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Perfil & Configurações",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
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
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Sair",
                            tint = Color(0xFFFF5252)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0B4F2F))
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(animatedStart, animatedEnd)))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.96f)
                    .background(
                        Color(0xFF0B4F2F).copy(alpha = 0.92f),
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SmallProfileHeader(
                    avatarRes = avatarRes,
                    nome = nome,
                    email = email,
                    onEditAvatar = { showAvatarOptions = true }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.06f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Informações Pessoais",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            label = { Text("Nome completo") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFBEE6C8),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("E-mail") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFBEE6C8),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                cursorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.06f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Preferências",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Tema escuro", color = Color.White)
                                Text(
                                    "Alterne entre modo claro/escuro nesta tela",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                            val thumbAnim by animateFloatAsState(targetValue = if (temaEscuro) 1f else 0f)
                            Switch(
                                checked = temaEscuro,
                                onCheckedChange = { temaEscuro = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color(0xFF66BB6A),
                                    uncheckedThumbColor = Color.LightGray,
                                    checkedTrackColor = Color(0xFF2E7D32)
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            nome = "Bryan Oliveira"
                            email = "bryan@email.com"
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Alterações revertidas")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Reverter", tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reverter")
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (nome.isBlank() || !email.contains("@")) {
                                    snackbarHostState.showSnackbar("Verifique nome e e-mail")
                                } else {
                                    snackbarHostState.showSnackbar("Alterações salvas")
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Salvar", tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Salvar", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedButton(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF5252))
                ) {
                    Icon(Icons.Default.Logout, contentDescription = "Sair", tint = Color(0xFFFF5252))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sair da conta", color = Color(0xFFFF5252))
                }
            }
        }
    }

    if (showAvatarOptions) {
        AvatarSelectorDialog(
            onDismiss = { showAvatarOptions = false },
            onAvatarSelected = {
                avatarRes = it
                showAvatarOptions = false
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Confirmar logout") },
            text = { Text("Deseja realmente sair da sua conta?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }) { Text("Sair", color = Color(0xFFFF5252)) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun SmallProfileHeader(
    avatarRes: Int,
    nome: String,
    email: String,
    onEditAvatar: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
                .background(Color(0xFF2E7D32)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = avatarRes),
                contentDescription = "Avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(onClick = onEditAvatar, modifier = Modifier.align(Alignment.BottomEnd)) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Editar avatar", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(nome, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(email, color = Color.White.copy(alpha = 0.85f))
    }
}

@Composable
private fun AvatarSelectorDialog(
    onDismiss: () -> Unit,
    onAvatarSelected: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Escolher avatar") },
        text = {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                item { AvatarOption(R.drawable.ic_account, onAvatarSelected) }
                item { AvatarOption(R.drawable.logo, onAvatarSelected) }
                item { AvatarOption(R.drawable.ic_edit, onAvatarSelected) }
                item { AvatarOption(R.drawable.ic_launcher_foreground, onAvatarSelected) }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Fechar") }
        }
    )
}

@Composable
private fun AvatarOption(resId: Int, onSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .size(72.dp)
            .clickable { onSelected(resId) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.06f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
