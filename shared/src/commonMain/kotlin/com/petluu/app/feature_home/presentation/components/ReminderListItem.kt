package com.petluu.app.feature_home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.petluu.app.core.presentation.util.Dimens
import com.petluu.app.core.presentation.util.Padding
import com.petluu.app.core.presentation.util.Spacing
import com.petluu.app.feature_home.domain.Reminder

@Composable
fun ReminderListItem(
    modifier: Modifier = Modifier,
    reminder: Reminder
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Padding.Vertical.SM, horizontal = Padding.Horizontal.MD),
        shape = RoundedCornerShape(Dimens.RoundedCornerRadius.large),
        /*       elevation = CardDefaults.cardElevation(
                   defaultElevation = Dimens.Elevation.petListItemCardElevation
               ),*/
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Card.reminderCardHeight)
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(1f).padding(Padding.All.MD),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Vertical.XS)
                ) {
                    Text(
                        text = reminder.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = "${reminder.petName} | ${reminder.recordType}",
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.outline
                    )

                    // TODO replace it with reminder.date
                    Text(
                        text = "Sept 20, 2023 at 12:15 PM",
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(end = Padding.Horizontal.MD)
                        .align(Alignment.CenterVertically)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(Dimens.RoundedCornerRadius.large)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                vertical = Padding.Horizontal.SM,
                                horizontal = Padding.Horizontal.MD
                            ),
                            text = "Next week",
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

        }
    }
}