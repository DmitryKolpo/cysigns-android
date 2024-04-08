package com.demacia.cysigns.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.demacia.cysigns.R

internal sealed class Signs(
    @DrawableRes resId: Int,
    @StringRes name: Int,
) {
    data object TrafficSignalsAhead : Signs(
        resId = R.drawable.uk_traffic_sign_543,
        name = R.string.traffic_signals_ahead,
    )
    data object Crossroads : Signs(
        resId = R.drawable.cyprus_warning_road_sign_crossroads,
        name = R.string.crossroads,
    )
    data object TJunction : Signs(
        resId = R.drawable.uk_traffic_sign_505_1__left_,
        name = R.string.t_junction,
    )
    //TODO: fill it up. don't like original json
}