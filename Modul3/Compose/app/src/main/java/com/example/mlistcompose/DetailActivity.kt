package com.example.mlistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mlistcompose.ui.theme.MListComposeTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getIntExtra("title", 0)
        val years = intent.getIntExtra("years", 0)
        val image = intent.getIntExtra("image", 0)
        val description = intent.getIntExtra("description", 0)

        setContent {
            MListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp)
                    ){
                        Image(
                            modifier = Modifier.height(225.dp).fillMaxWidth(),
                            painter = painterResource(image),
                            contentDescription = stringResource(title)
                        )
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = stringResource(title),
                                style = TextStyle(
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                                    .weight(1F)
                            )
                            Text(
                                text = stringResource(years),
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                                    .wrapContentWidth(Alignment.End)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                            Text(
                                text = "Plot :",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = stringResource(description),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )
                    }
                }
            }
        }
    }
}