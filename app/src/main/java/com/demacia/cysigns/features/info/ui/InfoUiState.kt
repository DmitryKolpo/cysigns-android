package com.demacia.cysigns.features.info.ui

import androidx.annotation.DrawableRes

data class InfoUiState(
    val signs: List<InfoSignUi>,
)

data class InfoSignUi(
    @DrawableRes val imageRes: Int,
    val name: String,
)
