package com.kevinserrano.apps.mycareerpathjetpackcompose.elearningcourses.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevinserrano.apps.mycareerpathjetpackcompose.R
import com.kevinserrano.apps.mycareerpathjetpackcompose.elearningcourses.models.Course
import com.kevinserrano.apps.mycareerpathjetpackcompose.elearningcourses.utils.Contants.courses
import com.kevinserrano.apps.mycareerpathjetpackcompose.ui.theme.MCaeerPathJetpackComposeTheme

class CoursesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MCaeerPathJetpackComposeTheme {
                MyApp()
            }
        }
    }
}


@Composable
private fun MyApp() {
    CollapsingEffectScreen(courses)
}

@Composable
private fun Header(lazyListState: LazyListState) {
    var scrolledY = 0f
    var previousOffset = 0
    Box(
        modifier = Modifier
            .graphicsLayer {
                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                translationY = scrolledY * 0.5f
                previousOffset = lazyListState.firstVisibleItemScrollOffset
            }
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.header_courses),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.courses_app)
        )
        val activity = (LocalContext.current as? Activity)
        IconButton(onClick = { activity?.finish() }) {
            Icon(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(50.dp),
                tint = Color.White,
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.exit)
            )
        }
        Text(
            stringResource(R.string.courses),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp),
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(color = Color.White)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CollapsingEffectScreen(courses: List<Course>) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        /*verticalArrangement = Arrangement.spacedBy(4.dp),*/
        state = lazyListState,
    ) {
        item {
            Header(lazyListState)
        }
        items(courses) { course ->
            CourseCard(course = course)
        }
    }
}

@Composable
private fun CourseCard(course: Course) {

    Card(
        backgroundColor = course.color.copy(alpha = 0.6f).compositeOver(Color.White),
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        CardContent(
            title = course.name, subTitle = "${course.hours} ${course.lessons}",
            iconId = course.iconId
        )
    }

}

@Composable
private fun CardContent(title: String, subTitle: String, iconId: Int) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row() {
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .padding(8.dp),
                painter = painterResource(id = iconId),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.courses_app)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(fontSize = 13.sp, text = subTitle, color = Color(0xFF132743))
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }
        }
        if (expanded) {
            Text(
                modifier = Modifier.padding(12.dp),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                text = ("Composem ipsum color sit lazy, " +
                        "padding theme elit, sed do bouncy. ").repeat(8),
            )
        }
    }
}

/*
@Preview(
    showBackground = true,
    widthDp = 420,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
*/
@Preview(showBackground = true, widthDp = 420)
@Composable
private fun DefaultPreview() {
    MCaeerPathJetpackComposeTheme {
        MyApp()
    }
}