package com.arsinde.foliantstore.feature.home.api

import com.arsinde.foliantstore.core.navigation.AppRoute
import kotlinx.serialization.Serializable

/**
 * Destination key for the home screen.
 *
 * `@Serializable` is required so that Navigation 3 can save and restore the back stack.
 */
@Serializable
data object Home : AppRoute