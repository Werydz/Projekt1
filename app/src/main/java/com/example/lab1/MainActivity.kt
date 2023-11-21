package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab1.ui.theme.Lab1Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen" ){
        composable("splash_screen"){
            SplashScreen(navController)
        }
        composable("main_screen"){
            MainScreen(navController)
        }
        composable("e_przycisk"){
            ButtonWithIcon(navController)
        }
        composable("e_obraz/{param}",
            arguments = listOf(navArgument("param") { type = NavType.StringType })
        )
        {
            val param = it.arguments?.getString("param") ?:""
            Imagine(navController, param = param)
        }
    }
}

@Composable
fun Imagine(navController: NavHostController, param: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(id = R.drawable.krajobraz),
            contentDescription = "wybrzeże",
            modifier = Modifier.size(400.dp))
        Text(text = param)
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(text = "Wróć do poprzedniego ekranu")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonWithIcon(navController: NavHostController) {
    var przedmiot by remember { mutableStateOf(" ") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Zapraszam na wycieczkę!"
        )
        TextField(value = przedmiot, label = {
            Text("Co chcesz ze sobą zabrać?")
        }, onValueChange = {
            przedmiot = it
        }, singleLine = true, modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            navController.navigate("e_obraz/$przedmiot")
        }) {
            Text(text = "Wycieczka", color = Color.Yellow)
        }
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(text = "Wróć do poprzedniego ekranu")
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true){
        delay(3000L)
        navController.navigate("main_screen")
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(Modifier.height(200.dp)){
            Image(painter = painterResource(id = R.drawable.baseline_web_stories_24), contentDescription = "Logo", Modifier.size(195.dp))
        }
        Spacer(Modifier.height(65.dp))
        Text(text = "Autor: Weronika Rydz", fontSize = 25.sp, color = Color.White)
        Text(text = "Temat: Jetpack Compose", fontSize = 25.sp, color = Color.White)
    }
}

@Composable
fun MainScreen(navController: NavController) {
    var klik by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            klik++
        }) {
            Image(
                painterResource(id = R.drawable.baseline_add_reaction_24),
                contentDescription = "Cart button icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Kliknij Mnie", color = Color.Yellow)
        }
        Text(
            text = "Przycisk został kliknięty $klik razy"
        )
        OutlinedButton(onClick = {
            navController.navigate("e_przycisk")
        }
        ) {
            Text(text = "Przejście do ekranu drugiego", color = Color.Green)
        }
    }
}