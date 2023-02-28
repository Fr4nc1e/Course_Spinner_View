package com.course.spinnerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.course.spinnerview.ui.theme.SpinnerViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpinnerViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val myData = ArrayList<MyData>()
                    resources.getStringArray(R.array.months_array).forEachIndexed { index, s ->
                        myData.add(MyData(index, s))
                    }

                    SpinnerSample(
                        list = myData,
                        preselected = myData.first(),
                    ) {
                        Toast.makeText(
                            baseContext,
                            "You have selected item : " + it.name,
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun SpinnerSample(
    list: List<MyData>,
    preselected: MyData,
    onSelectionChanged: (myData: MyData) -> Unit,
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selected.name,
                modifier = Modifier
                    .padding(8.dp),
            )
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.padding(8.dp),
            ) {
                Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry.name,
                                modifier = Modifier
                                    .wrapContentWidth() // optional instad of fillMaxWidth
//                                    .fillMaxWidth()
                                    .align(Alignment.Start),
                            )
                        },
                    )
                }
            }
        }
    }
}

data class MyData(
    val id: Int,
    val name: String,
)
