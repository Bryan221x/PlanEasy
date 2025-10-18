package com.example.projeto_integrador

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

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
                .padding(horizontal = 32.dp)
                .padding(top = 24.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = "Crie sua conta",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Preencha seus dados para começar",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 28.dp)
            )

            // Campo: Nome
            Text("NOME", color = Color.White, fontSize = 13.sp, modifier = Modifier.align(Alignment.Start))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                placeholder = { Text("Seu nome completo", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF2E7D55),
                    focusedContainerColor = Color(0xFF2E7D55),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF81C784),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            )

            // Campo: Celular
            Text("CELULAR", color = Color.White, fontSize = 13.sp, modifier = Modifier.align(Alignment.Start))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                singleLine = true,
                placeholder = { Text("(00) 00000-0000", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF2E7D55),
                    focusedContainerColor = Color(0xFF2E7D55),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF81C784),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            )

            // Campo: Email
            Text("E-MAIL", color = Color.White, fontSize = 13.sp, modifier = Modifier.align(Alignment.Start))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                placeholder = { Text("email@exemplo.com", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF2E7D55),
                    focusedContainerColor = Color(0xFF2E7D55),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF81C784),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            )

            // Campo: Senha
            Text("SENHA", color = Color.White, fontSize = 13.sp, modifier = Modifier.align(Alignment.Start))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                placeholder = { Text("••••••••", color = Color.White.copy(alpha = 0.6f)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF2E7D55),
                    focusedContainerColor = Color(0xFF2E7D55),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF81C784),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            )

            // Mensagem de erro (animada)
            AnimatedVisibility(visible = errorMessage != null, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    text = errorMessage ?: "",
                    color = Color(0xFFFFCDD2),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Botão de Cadastro
            Button(
                onClick = {
                    focusManager.clearFocus()
                    coroutineScope.launch {
                        // Validação simples
                        if (name.isBlank() || phone.isBlank() || email.isBlank() || password.isBlank()) {
                            errorMessage = "Por favor, preencha todos os campos."
                            return@launch
                        }

                        isLoading = true
                        errorMessage = null
                        delay(1500)

                        // Simulação de criação de conta
                        if (email.contains("@") && password.length >= 6) {
                            navController.navigate("login") {
                                popUpTo("cadastro") { inclusive = true }
                            }
                        } else {
                            errorMessage = "Dados inválidos. Verifique seu e-mail e senha."
                        }

                        isLoading = false
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Text("Cadastrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Link para Login
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Já tem conta? ", color = Color.White, fontSize = 14.sp)
                Text(
                    text = "Faça login",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { navController.navigate("login") }
                )
            }
        }
    }
}
