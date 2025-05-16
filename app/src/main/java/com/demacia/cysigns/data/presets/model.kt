package com.demacia.cysigns.data.presets

import com.demacia.cysigns.data.Signs

data class Preset(
    val id: Int,
    val presetName: String,
    val signs: List<Signs>,
)