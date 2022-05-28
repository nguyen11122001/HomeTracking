package com.example.hometracking

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun HistoryScreen(data: List<String>)
{
//    val linear = Brush.linearGradient(listOf(Color.Red, Color.Blue))

    Box(modifier = Modifier.fillMaxSize())

    {
        LazyColumn {
            items(data) { item ->
                Card(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),

                    ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        CreateImageProfile()
                        Column(
                            modifier = Modifier
                                .padding(20.dp, 5.dp)
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                LocalDateTime.now()
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }
                            val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy ")
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }
                            Text(
                                text = item,
                                fontWeight = FontWeight.Bold,
                                color = Color(138, 0, 187,)
                            )
                            Text(
                                text = current.format(formatter),
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun CreateImageProfile(){
    Surface(
        modifier = Modifier
            .size(70.dp)
            .padding(5.dp),
        border = BorderStroke(0.5.dp , color = Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ){
        Image(painter = painterResource(id = R.drawable.images ) ,
            contentDescription ="profile image",
            modifier = Modifier.size(70.dp),
            contentScale = ContentScale.Crop)


    }
}