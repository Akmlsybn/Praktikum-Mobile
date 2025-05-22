package com.example.mlistcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.mlistcompose.model.ListMovie
import com.example.mlistcompose.ui.theme.MListComposeTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory("Test Debugging")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.plant(Timber.DebugTree())
        setContent {
            MListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    MovieListScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MovieListScreen(viewModel: MovieViewModel) {
    val movieList by viewModel.movies.collectAsState()
    val navigateToUrl by viewModel.navigateToUrl.collectAsState()
    val navigateToDetail by viewModel.navigateToDetail.collectAsState()
    val context = LocalContext.current

    navigateToUrl?.let { url ->
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
        viewModel.onUrlNavigated()
    }

    navigateToDetail?.let { movie ->
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("title", movie.title)
            putExtra("years", movie.years)
            putExtra("image", movie.image)
            putExtra("description", movie.description)
        }
        context.startActivity(intent)
        viewModel.onDetailNavigated()
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movieList) { movie ->
            MovieItems(
                movie = movie,
                onImdbClicked = { viewModel.onLinkClicked(movie.url) },
                onDetailClicked = { viewModel.onDetailClicked(movie) }
            )
        }
    }
}

@Composable
fun MovieItems(
    movie: ListMovie,
    onImdbClicked: () -> Unit,
    onDetailClicked: () -> Unit
) {
    val releaseDate = stringResource(movie.years)

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF343539)),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(movie.title),
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .weight(1f)
                    )
                    Text(
                        text = releaseDate,
                        style = TextStyle(fontSize = 20.sp, color = Color.White),
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                        style = TextStyle(fontSize = 16.sp, color = Color.White),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onImdbClicked) {
                        Text("IMDB")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = onDetailClicked) {
                        Text("Detail")
                    }
                }
            }
        }
    }
}