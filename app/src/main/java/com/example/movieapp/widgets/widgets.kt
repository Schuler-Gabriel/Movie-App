package com.example.movieapp.widgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovies

@Preview
@Composable
fun MovieRow(

    movie: Movie = getMovies()[0],
    onItemClick: (String) -> Unit = {}
) {
    var expanded by remember {
        mutableStateOf(false)
    }


    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
//            .height(130.dp )
            .clickable {
                onItemClick(movie.id)
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Surface(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(100.dp),
                    shape = CircleShape,
                    elevation = 6.dp
                ) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(movie.images[0])
                            .crossfade(true)
                            .transformations(CircleCropTransformation())
                            .build(),
                        contentDescription = "Movie Poster"
                    )
//                Image(
//                    painter = rememberImagePainter(data = movie.images[0], builder = {crossfade(true)}),
//                    contentDescription = "Movie Poster"
//                )
//                Icon(
//                    imageVector = Icons.Default.AccountBox,
//                    contentDescription = "Movie image"
//                )
                }
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Genre: ${movie.genre}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Released: ${movie.year}",
                        style = MaterialTheme.typography.caption
                    )

                    AnimatedVisibility(visible = expanded) {
                        Column() {
                            CardText(
                                title = "Plot: ",
                                content = movie.plot
                            )
                            Divider(modifier = Modifier.padding(9.dp))
                            Text(
                                text = "Diretor: ${movie.director}",
                                style = MaterialTheme.typography.caption
                            )
                            Text(
                                text = "Actors: ${movie.actors}",
                                style = MaterialTheme.typography.caption
                            )
                            Text(
                                text = "Rating: ${movie.rating}",
                                style = MaterialTheme.typography.caption
                            )


                        }
                    }

                }
            }
            AnimatedVisibility(visible = expanded) {
                Column() {
                    Spacer(modifier = Modifier.height(6.dp))
//                    Text(
//                        text = "Gallery:",
//                        style = MaterialTheme.typography.caption
//                    )
                    HorizontalScrollableImageView(movie)
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Icon(
                imageVector = if (!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                contentDescription = if (!expanded) "Down Arrow" else "Up Arrow",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        expanded = !expanded
                    },
                tint = Color.DarkGray
            )
            }

        }
    }
}

@Composable
fun CardText (
    title: String,
    content: String
){
    Text(

        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                )
            ){
                append(title)
            }
            withStyle(
                style = SpanStyle(
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                )
            ){
                append(content)
            }
        },
        modifier = Modifier.padding(6.dp)
    )
    
}


@Composable
fun HorizontalScrollableImageView(movie: Movie) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 0.dp
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(image)
                        .build(),
                    contentDescription = "Movie Poster"
                )
            }
        }
    }
}