package com.demacia.shared.components.info

import com.arkivanov.decompose.ComponentContext

class DefaultInfoComponent(
    componentContext: ComponentContext,
) : InfoComponent, ComponentContext by componentContext {
}