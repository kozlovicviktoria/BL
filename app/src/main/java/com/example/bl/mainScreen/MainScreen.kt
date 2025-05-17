package com.example.bl.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bl.bottomMenu.BottomMenu
import com.example.bl.data.PlaceDBEntity
import com.example.bl.navigation.MainScreenObject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navData: MainScreenObject,
    navController: NavController) {

    val allPlaces = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }
    val db = Firebase.firestore
    val selectedCategory = remember { mutableStateOf("all") } // Для хранения выбранной категории
    val coroutineScope =  rememberCoroutineScope()

    // Функция для загрузки мест
    suspend fun loadPlaces(category: String) {
        val place = getAllPlace(db, category)
            allPlaces.value = place
    }
    // Загружаем все места при запуске
    LaunchedEffect(Unit) {
        loadPlaces(selectedCategory.value)
    }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu(
                onCategoryClick = { category ->
                    selectedCategory.value = category
                    coroutineScope.launch {
                        loadPlaces(category)
                    }
                }
            ) },
        ) {
            CustomMap(allPlaces.value, navController::navigate)

        }
    }

suspend fun getAllPlace(
    db: FirebaseFirestore,
    category: String
): List<PlaceDBEntity> {
    return try {
        val query = if (category == "all") {
            db.collection("places")
        } else {
            db.collection("places").whereEqualTo(category, true)
        }
        val snapshot = query
            .get()
            .await()
        snapshot.documents.mapNotNull { doc ->
            val place = doc.toObject(PlaceDBEntity::class.java)
            place?.apply { id = doc.id }
        }// <- сохранить id документа}
    } catch (e: Exception){
        emptyList()
    }
}

