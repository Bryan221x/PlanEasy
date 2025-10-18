package com.example.projeto_integrador

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") { MainScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("cadastro") { CadastroScreen(navController) }
        composable("inicio") { InicioScreen(navController) }
        composable("notificacoes") { NotificacoesScreen(navController) }
        composable("pesquisa") { PesquisaScreen(navController) }
        composable("quadros") { QuadrosScreen(navController) }
        composable("configuracao_quadro") { ConfiguracaoQuadroScreen(navController) }

        composable("quadro/{titulo}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            QuadrosScreen(navController)
        }

        // 🟩 NOVA ROTA
        composable("configuracaoUsuario") { ConfiguracaoUsuarioScreen(navController) }

    }
}


