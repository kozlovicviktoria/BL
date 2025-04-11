package com.example.bl.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bl.bottomMenu.BottomMenu
import com.example.bl.data.PlaceDBEntity
import com.example.bl.navigation.LoginScreenObject
import com.example.bl.navigation.MainScreenObject
import com.example.bl.topMenu.TopMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navData: MainScreenObject, navController: NavController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val allPlaces = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }
    val db = Firebase.firestore
    val selectedCategory = remember { mutableStateOf("all") } // Для хранения выбранной категории

    // Функция для загрузки мест
    fun loadPlaces(category: String) {
        getAllPlaces(db, category) { place ->
            allPlaces.value = place
        }
    }
    // Загружаем все места при запуске
    LaunchedEffect(Unit) {
        loadPlaces(selectedCategory.value)
    }

    ModalNavigationDrawer(
        drawerState=drawerState,
        gesturesEnabled = false,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {

                DrawerHeader(drawerState)
                DrawerBody()

            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu(
                onCategoryClick = { category ->
                    selectedCategory.value = category
                    loadPlaces(category)
                }
            ) },
            topBar = { TopMenu(drawerState) }
        ) {
            CustomMap(allPlaces.value, navController::navigate)

        }
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
//                val result = task.result?.documents?.map { doc ->
//                    val place = doc.toObject(PlaceDBEntity::class.java)
//                    place?.id = doc.id
//                    place
//                }?.filterNotNull() ?: emptyList()
//                onPlace(result)
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Ошибка чтения", e)
        }
}
