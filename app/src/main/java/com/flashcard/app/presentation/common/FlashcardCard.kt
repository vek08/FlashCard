package com.flashcard.app.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flashcard.app.data.local.entity.Flashcard
import com.flashcard.app.presentation.theme.CardGradientEnd
import com.flashcard.app.presentation.theme.CardGradientEndDark
import com.flashcard.app.presentation.theme.CardGradientStart
import com.flashcard.app.presentation.theme.CardGradientStartDark

/**
 * A premium-looking flashcard card composable with gradient background,
 * animated answer reveal, and edit/delete actions.
 *
 * @param flashcard The flashcard data to display.
 * @param isAnswerVisible Whether the answer section is currently shown.
 * @param onToggleAnswer Callback to show/hide the answer.
 * @param onEdit Callback to edit this flashcard.
 * @param onDelete Callback to delete this flashcard.
 * @param cardPosition Optional card position text (e.g., "3 / 10").
 * @param modifier Modifier applied to the card.
 */
@Composable
fun FlashcardCard(
    flashcard: Flashcard,
    isAnswerVisible: Boolean,
    onToggleAnswer: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    cardPosition: String? = null,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    // Select gradient colors based on theme
    val gradientBrush = Brush.linearGradient(
        colors = if (isDark) {
            listOf(CardGradientStartDark, CardGradientEndDark)
        } else {
            listOf(CardGradientStart, CardGradientEnd)
        }
    )

    // Semi-transparent white for glassmorphism-lite effect
    val glassOverlay = if (isDark) Color.White.copy(alpha = 0.06f) else Color.White.copy(alpha = 0.15f)
    val textOnGradient = Color.White
    val subtleTextOnGradient = Color.White.copy(alpha = 0.80f)
    val dividerColor = Color.White.copy(alpha = 0.25f)

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 350.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = gradientBrush)
        ) {
            // Glassmorphism overlay layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .matchParentSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(glassOverlay)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card position badge (optional)
                if (cardPosition != null) {
                    Text(
                        text = cardPosition,
                        style = MaterialTheme.typography.labelMedium,
                        color = subtleTextOnGradient,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Question label
                Text(
                    text = "QUESTION",
                    style = MaterialTheme.typography.labelMedium,
                    color = subtleTextOnGradient,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing * 1.5
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Question text
                Text(
                    text = flashcard.question,
                    style = MaterialTheme.typography.headlineMedium,
                    color = textOnGradient,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Show/Hide Answer button
                FilledTonalButton(
                    onClick = onToggleAnswer,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = textOnGradient
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = if (isAnswerVisible) Icons.Outlined.VisibilityOff
                        else Icons.Outlined.Visibility,
                        contentDescription = if (isAnswerVisible) "Hide Answer" else "Show Answer",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAnswerVisible) "Hide Answer" else "Show Answer",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Divider between question and answer
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    thickness = 1.dp,
                    color = dividerColor
                )

                // Animated answer section
                AnimatedVisibility(
                    visible = isAnswerVisible,
                    enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Answer label
                        Text(
                            text = "ANSWER",
                            style = MaterialTheme.typography.labelMedium,
                            color = subtleTextOnGradient,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing * 1.5
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Answer text
                        Text(
                            text = flashcard.answer,
                            style = MaterialTheme.typography.titleLarge,
                            color = textOnGradient,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom divider
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = dividerColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Action buttons row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Edit button
                    IconButton(
                        onClick = onEdit,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = subtleTextOnGradient
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit flashcard"
                        )
                    }

                    // Delete button
                    IconButton(
                        onClick = onDelete,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White.copy(alpha = 0.65f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete flashcard"
                        )
                    }
                }
            }
        }
    }
}
