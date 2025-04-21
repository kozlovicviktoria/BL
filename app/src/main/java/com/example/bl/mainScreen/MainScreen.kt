package com.example.bl.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bl.bottomMenu.BottomMenu
import com.example.bl.data.Favorites
import com.example.bl.data.PlaceDBEntity
import com.example.bl.favScreen.FavScreen
import com.example.bl.navigation.LoginScreenObject
import com.example.bl.navigation.MainScreenObject
import com.example.bl.topMenu.TopMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
//
fun MainScreen(navData: MainScreenObject,
    navController: NavController
) {

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
           // topBar = { TopMenu(drawerState) }
        ) {
            CustomMap(allPlaces.value, navController::navigate)

        }
    }



fun signOut(
    auth: FirebaseAuth,
    navController: NavController
){
    auth.signOut()
    navController.navigate(LoginScreenObject)
}


private fun getAllPlaces(
    db: FirebaseFirestore,
    category: String,
    onPlace: (List<PlaceDBEntity>) -> Unit
) {

    val query = if (category == "all") {
        db.collection("places")
    } else {
        db.collection("places").whereEqualTo(category, true)
    }
            query
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //onPlace(task.result.toObjects(PlaceDBEntity::class.java))
                val placeList = task.result.documents.mapNotNull { doc ->
                    val place = doc.toObject(PlaceDBEntity::class.java)
                    place?.apply { id = doc.id } // <- сохранить id документа
                }
                onPlace(placeList)
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Ошибка чтения", e)
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

