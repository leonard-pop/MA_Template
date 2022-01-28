package com.asd.template_inventar.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asd.template_inventar.components.AddProductScreen
import com.asd.template_inventar.model.viewmodel.ProductsViewModel
import com.asd.template_inventar.components.DisplayAllScreen
import com.asd.template_inventar.components.DisplayCheapestScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {
        composable(
            route = Screen.ListScreen.route
        ) {
            DisplayAllScreen(
                onClickAdd = {
                    navController.navigate(Screen.AddScreen.route)
                },
                onClickTop = {
                    navController.navigate(Screen.TopScreen.route)
                }
            )
        }
        composable(
            route = Screen.AddScreen.route
        ) {
            AddProductScreen(
                addCallback =  { nume: String, tip: String, cantitate: Int, pret: Int,
                                 discount: Int, status: Boolean ->
                    viewModel.add(nume, tip, cantitate, pret, discount, status)
                    navController.navigate(Screen.ListScreen.route)
                }
            )
        }
        composable(
            route = Screen.TopScreen.route
        ) {
            DisplayCheapestScreen()
        }
    }
}