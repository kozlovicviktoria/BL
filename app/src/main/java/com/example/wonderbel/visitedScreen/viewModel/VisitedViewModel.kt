package com.example.wonderbel.visitedScreen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderbel.data.PlaceDBEntity
import com.example.wonderbel.data.VisitedEntity
import com.example.wonderbel.mainScreen.viewModel.MainRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class VisitedViewModel(
    private val repository: VisitedRepository,
    private val mainRepository: MainRepository
): ViewModel() {

    var totalPlaces by mutableStateOf(0)
    var visitedCount by mutableStateOf(0)
    val db = Firebase.firestore
    var visitedPlaces by mutableStateOf(emptyList<PlaceDBEntity>())
    var isVisited by mutableStateOf(true)
    var vis by mutableStateOf(emptyList<VisitedEntity>())

    fun updateVis(uid: String, place: PlaceDBEntity){
        viewModelScope.launch {
            val updatedVisited =
                repository.onVisitedClick(db, isVisited, uid, VisitedEntity(place.id))
            vis = updatedVisited
            isVisited = updatedVisited.any { it.key == place.id }
        }
    }

    fun getCountPlaces(){
        viewModelScope.launch {
            val snapshot = mainRepository.getAllPlace(db, "all")
            totalPlaces = snapshot.size
        }
    }

    fun getCountVisitedPlaces(uid: String){
        viewModelScope.launch {
            val visited = repository.getAllPlacesWithVisited(db, uid)
            visitedCount = visited.size
        }
    }

    fun getAllVisitedPlaces(uid: String){

        viewModelScope.launch {
            if(uid.isNotBlank()){
                val viss = repository.getAllPlacesWithVisited(db, uid)
                vis = viss
                val fullPlaces = repository.fullVisPlaces(db, viss)
                visitedPlaces = fullPlaces

            }
        }
    }


}