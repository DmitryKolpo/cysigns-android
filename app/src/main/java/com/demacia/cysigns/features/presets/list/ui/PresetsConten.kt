package com.demacia.cysigns.features.presets.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.cysigns.R
import com.demacia.cysigns.ui.components.Toolbar
import com.demacia.cysigns.utils.Spacer
import com.demacia.cysigns.utils.painter
import com.demacia.cysigns.utils.resource

@Composable
fun PresetsContent(
    uiState: PresetsUiState,
    //TODO: добавить UiEvents и VM
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            title = R.string.presets_title.resource(),
            onBackClick = { /*TODO*/ },
        )
        LazyColumn {
            items(uiState.presets.size) {
                val item = uiState.presets[it]
                Item(
                    presetUi = item,
                    onClick = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
private fun Item(
    presetUi: PresetUi,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = presetUi.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(1f)
        Icon(
            painter = Icons.AutoMirrored.Filled.KeyboardArrowRight.painter(),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        PresetsContent(
            uiState = PresetsUiState(
                presets = listOf(
                    PresetUi("Essential Signs"),
                    PresetUi("Custom list name"),
                )
            )
        )
    }
}