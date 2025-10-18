package com.example.projeto_integrador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}

@Composable
fun MainScreen(navController: androidx.navigation.NavController) {
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
            // Logo (adicione seu arquivo em res/drawable/logo.png)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo PlanEasy",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "PlanEasy",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D7037)
            )

            Spacer(modifier = Modifier.height(120.dp))

            // Botão de Login
            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D7037)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Cadastro
            OutlinedButton(
                onClick = { navController.navigate("cadastro") },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = Brush.linearGradient(listOf(Color(0xFF0D7037), Color(0xFF0D7037)))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text("Cadastrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
