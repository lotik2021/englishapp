package com.ltk.foreign.features.settings.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ltk.foreign.R

@Composable
fun LayoutToggleButton(
    isGridView: Boolean,
    onToggleClick: () -> Unit,
) {
    val customGridViewImage = painterResource(R.drawable.ic_grid)
    val customAgendaViewImage = painterResource(R.drawable.ic_list)

    val imageToShow = if (isGridView) customGridViewImage else customAgendaViewImage

    val tint = LocalContentColor.current

    IconButton(
        onClick = onToggleClick,
        modifier = Modifier.padding(4.dp)
    ) {
        Image(
            imageToShow,
            contentDescription = "Toggle Button",
            colorFilter = ColorFilter.tint(tint)
        )
    }
}
