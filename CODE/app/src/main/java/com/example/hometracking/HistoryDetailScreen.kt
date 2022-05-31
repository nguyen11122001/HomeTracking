package com.example.hometracking
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Preview
@Composable
fun HistoryDetailScreen()
{
    val linear = Brush.linearGradient(listOf(Color(217, 64, 240, 255), Color(32, 134, 243, 255)))

    Box(modifier = Modifier
        .fillMaxSize()
        .background(linear))


    {
        Column( modifier = Modifier
            .size(100.dp)


        )
        {
            Image(painter = painterResource(id = R.drawable.ic_baseline_check_circle_24 ) ,
                contentDescription ="profile image",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop)
        }


        Column( modifier = Modifier
            .size(300.dp)
            .align(Alignment.Center)
            )
        {
            Image(painter = painterResource(id = R.drawable.images ) ,
                contentDescription ="profile image",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Crop)
        }

        Column( modifier = Modifier
            .padding(50.dp)
        )
        {
            Text(text = "kfjds")
        }




    }

}


