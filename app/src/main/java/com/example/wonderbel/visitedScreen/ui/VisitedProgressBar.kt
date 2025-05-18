package com.example.wonderbel.visitedScreen.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("SuspiciousIndentation")
@Composable
fun VisitedProgressBar(visitedCount: Int, totalCount: Int) {
    val progress = if (totalCount != 0) visitedCount.toFloat() / totalCount else 0f

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Вы посетили $visitedCount из $totalCount мест",
                color = Color.Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .background(Color(0xFF4CAF50))
                )
            }
        }
    }

