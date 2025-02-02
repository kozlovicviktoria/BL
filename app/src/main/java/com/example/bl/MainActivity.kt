package com.example.bl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.logScreen.LoginScreen
import com.example.bl.mainScreen.CustomMap


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            LoginScreen()
//            main1()


        }
    }
}


@Preview
@Composable
fun MainTitle() {
        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(80.dp)

        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "Travel",
                color = Color.DarkGray,
                fontSize = 35.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                letterSpacing = 3.sp
            )
        }

    }



//setContent { val hydePark = LatLng(51.508610, -0.163611)
//    val regentsPark = LatLng(51.531143, -0.159893)
//    val primroseHill = LatLng(51.539556, -0.16076088)
//    val crystalPalacePark = LatLng(51.42153, -0.05749)
//    val greenwichPark = LatLng(51.476688, 0.000130)
//    val lloydPark = LatLng(51.364188, -0.080703)
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(hydePark, 10f)
//    } }
//GoogleMap( modifier = Modifier.fillMaxSize(),
//cameraPositionState = cameraPositionState)
//{ val parkMarkers = remember { mutableStateListOf(
//    ParkItem(hydepark, "Hyde Park", "Marker in hyde Park"),
//    ParkItem(regentspark, "Regents Park", "Marker in Regents Park"),
//    ParkItem(primroseHill, "Primrose Hill", "Marker in Primrose Hill"),
//
//    ParkItem(crystalPalacePark, "Crystal Palace", "Marker in Crystal Palace"),
//    ParkItem(greenwichPark, "Greenwich Park", "Marker in Greenwich Park"),
//    ParkItem(lloydPark, "Lloyd park", "Marker in Lloyd Park"), ) }
//    Clustering(items = parkMarkers, onClusterClick = {
//    // Handle when the cluster is tapped },
//    // onClusterItemClick = { marker -> // Handle when a marker in the cluster is tapped }) } }
//    // data class ParkItem( val itemPosition: LatLng,
//    // val itemTitle: String, val itemSnippet: String) : ClusterItem { override
//    // fun getPosition(): LatLng = itemPosition override fun getTitle(): String = itemTitle override
//    // fun getSnippet(): String = itemSnippet }
//    }

@Composable
fun Footer(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.DarkGray)
    ){

        Text("jfjfjfj")
    }
}

@Composable
fun main1(){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            MainTitle()
        }
        Row {
            CustomMap()
        }
        Row {
            Footer()
        }
    }
}











