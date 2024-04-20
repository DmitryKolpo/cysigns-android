package com.demacia.cysigns.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp

@Composable
fun @receiver:StringRes Int.resource(): String = resource(formatArgs = emptyArray())

@Composable
fun @receiver:StringRes Int.resource(vararg formatArgs: Any): String =
    stringResource(id = this, formatArgs = formatArgs)

@Composable
fun @receiver:DrawableRes Int.painter(): Painter = painterResource(this)

@Composable
fun ImageVector.painter(): Painter = rememberVectorPainter(this)

@Composable
fun ColumnScope.Spacer(height: Dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun RowScope.Spacer(width: Dp) = Spacer(modifier = Modifier.width(width))

@Composable
fun RowScope.Spacer(weight: Float) = Spacer(modifier = Modifier.weight(weight))

@Composable
fun ColumnScope.Spacer(weight: Float) = Spacer(modifier = Modifier.weight(weight))