package com.example.hometracking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun HomeScreen()
{
    val linear = Brush.linearGradient(listOf(Color(217, 64, 240, 255), Color(32, 134, 243, 255)))
    Box(modifier = Modifier
        .fillMaxSize()
        .background(linear))

    {

            Surface( modifier = Modifier
                .padding(20.dp, 70.dp)
                .size(400.dp, 100.dp)
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
            elevation = 4.dp,
                color = Color(67, 75, 221, 255)

            )
        {
            Column(
                modifier =Modifier.padding(15.dp)

            )
            {


                Text(
                    text = "HOME TRACKING ",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 40.sp
                )


            }
            Column(
                modifier = Modifier
                    .padding(10.dp, 70.dp,100.dp, 10.dp)
            )
            {
                Text(
                    text = "Welcome Linh !",
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    fontSize = 15.sp


                )
            }
        }


        Surface( modifier = Modifier
            .padding(20.dp, 200.dp, 20.dp, 20.dp)
            .size(170.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
            elevation = 4.dp,
            color = Color(193, 67, 221, 255)
     )
        {

            Column(
                modifier =Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally


            )
            {
                Text(
                    text = "5",

                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 60.sp
                )

            }
            Column(
                modifier =Modifier.padding(10.dp, 90.dp,10.dp,10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            )
            {
                Text(
                    text = "Members",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }

        Surface( modifier = Modifier
            .padding(20.dp, 390.dp, 20.dp, 20.dp)
            .size(170.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
            elevation = 4.dp,
            color = Color(59, 109, 209, 255)
        )
        {

            Column(
                modifier =Modifier.padding(10.dp,20.dp),
                horizontalAlignment = Alignment.CenterHorizontally


            )
            {

            }

        }
        Surface( modifier = Modifier
            .padding(210.dp, 200.dp, 10.dp, 20.dp)
            .size(170.dp, 100.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
            elevation = 4.dp,
            color = Color(59, 109, 209, 255)
        )
        {

        }
        Surface( modifier = Modifier
            .padding(210.dp, 320.dp, 10.dp, 20.dp)
            .size(170.dp, 220.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
            elevation = 4.dp,
            color = Color(192, 62, 221, 255)
        )
        {

            Column(
                modifier =Modifier.padding(10.dp,40.dp),
                horizontalAlignment = Alignment.CenterHorizontally


            )
            {
                Text(
                    text = "10",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 60.sp
                )
            }
            Column(
                modifier =Modifier.padding(10.dp, 110.dp,10.dp,10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            )
            {
                Text(
                    text = "Turn in in today ",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }


    }
}