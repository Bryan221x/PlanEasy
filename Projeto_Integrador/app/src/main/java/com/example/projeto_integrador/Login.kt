package com.example.projeto_integrador

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = "Bem-vindo de volta \uD83D\uDC4B",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Entre para continuar com seu quadro",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 28.dp)
            )

            // Campo: Email
            Text(
                text = "E-MAIL",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                placeholder = {
                    Text("email@exemplo.com", color = Color.White.copy(alpha = 0.6f))
                },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
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
            Text(
                text = "SENHA",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                placeholder = {
                    Text("••••••••", color = Color.White.copy(alpha = 0.6f))
                },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
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

            Spacer(modifier = Modifier.height(25.dp))

            // Botão Entrar
            Button(
                onClick = { navController.navigate("inicio") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = "Entrar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Link: Esqueceu senha
            Text(
                text = "Esqueceu sua senha?",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.clickable { /* Implementar recuperação futura */ }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Link: Cadastro
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Não tem conta? ", color = Color.White, fontSize = 14.sp)
                Text(
                    text = "Cadastre-se",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { navController.navigate("cadastro") }
                )
            }
        }
    }
}
