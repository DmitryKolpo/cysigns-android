package com.demacia.cysigns.features.info

import androidx.compose.runtime.Composable
import com.demacia.cysigns.data.Signs
import com.demacia.cysigns.features.info.ui.InfoContent
import com.demacia.cysigns.features.info.ui.InfoSignUi
import com.demacia.cysigns.features.info.ui.InfoUiState
import com.demacia.cysigns.utils.resource

@Composable
fun InfoScreen(
) {
    val state = InfoUiState(
        Signs.entries.map {
            InfoSignUi(
                imageRes = it.imageResId,
                name = it.signName.resource(),
            )
        }
    )

    InfoContent(state = state)
}