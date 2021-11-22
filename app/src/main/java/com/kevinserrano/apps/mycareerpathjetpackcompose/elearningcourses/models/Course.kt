package com.kevinserrano.apps.mycareerpathjetpackcompose.elearningcourses.models

import androidx.compose.ui.graphics.Color
import com.kevinserrano.apps.mycareerpathjetpackcompose.R

data class Course(
    val name:String = "",
    val hours:String = "",
    val lessons:String = "",
    val color: Color = Color(0xFFEFF7CF),
    val iconId:Int = R.drawable.ic_course_angular
)
