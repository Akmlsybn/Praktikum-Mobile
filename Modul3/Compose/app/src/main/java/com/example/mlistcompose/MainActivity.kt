package com.example.mlistcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mlistcompose.model.ListMovie
import com.example.mlistcompose.data.DataMovie
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.core.net.toUri
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.mlistcompose.ui.theme.MListComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MListComposeTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ){
                    MovieListScreen(movie = DataMovie().loadMovieList())
                }
            }
        }
    }
}
@Composable
fun MovieListScreen(movie: List<ListMovie>) {
    LazyColumn (contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp))
    {
        items(movie){item ->
            MovieItems(movie = item)
        }
    }
}
@Composable
fun MovieItems(movie: ListMovie){
    val context = LocalContext.current
    val url = stringResource(movie.url)
    val releaseDate = stringResource(movie.years)

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF343539)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(movie.image),
                contentDescription = stringResource(movie.title),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 90.dp, height = 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    Text(
                        text = stringResource(movie.title),
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                            .weight(1F)
                    )
                    Text(
                        text = releaseDate,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = "Plot :",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = stringResource(movie.desc),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }) {
                        Text("IMDB")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("title", movie.title)
                            putExtra("years", movie.years)
                            putExtra("image", movie.image)
                            putExtra("description", movie.description)
                        }
                        context.startActivity(intent)
                    }) {
                        Text("Detail")
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MListComposeTheme {
        MovieListScreen(movie = DataMovie().loadMovieList())
    }
}