package com.chill.mallang.ui.feature.fort_detail.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.UserRecord
import com.chill.mallang.ui.component.LoadingBox
import com.chill.mallang.ui.feature.fort_detail.TeamRecordState
import com.chill.mallang.ui.theme.Typography

@Composable
fun TeamRecordBody(
    teamRecordState: TeamRecordState,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs =
        listOf(
            stringResource(R.string.my_team_records_label),
            stringResource(R.string.oppo_team_records_label),
        )

    when (teamRecordState) {
        is TeamRecordState.Loading -> {
            LoadingBox()
        }

        is TeamRecordState.Error -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Text(teamRecordState.errorMessage.getErrorMessage(LocalContext.current))
            }
        }

        is TeamRecordState.Success -> {
            Column(
                modifier = modifier,
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = {},
                    divider = {},
                ) {
                    tabs.forEachIndexed { index, title ->
                        Box {
                            CustomBorderBox(isSelected = selectedTabIndex == index) {
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { selectedTabIndex = index },
                                    text = {
                                        Text(
                                            title,
                                            color = if (selectedTabIndex == index) Color.Black else MaterialTheme.colorScheme.background,
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
                when (selectedTabIndex) {
                    0 -> TeamTab(recordList = teamRecordState.teamRecords.myTeamRecords)
                    1 -> TeamTab(recordList = teamRecordState.teamRecords.oppoTeamRecords)
                }
            }
        }
    }
}

@Composable
fun TeamTab(
    recordList: List<UserRecord>,
    modifier: Modifier = Modifier,
) {
    if (recordList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.no_record))
        }
    } else {
        LazyColumn(
            modifier =
                modifier
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 10.dp),
        ) {
            items(recordList) { record ->
                RecordListItem(record)
            }
        }
    }
}

@Composable
fun RecordListItem(
    userRecord: UserRecord,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(height = 65.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = stringResource(R.string.user_record_rank, userRecord.userPlace),
                fontSize = 24.sp,
                style = Typography.displayLarge,
                color = Color.Red,
            )
            Text(
                text = userRecord.userName,
                fontSize = 22.sp,
                style = Typography.displayLarge,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(R.string.user_record_score, userRecord.userScore?.toInt() ?: 0),
                fontSize = 22.sp,
                style = Typography.displayLarge,
            )
        }
    }
}

@Composable
fun CustomBorderBox(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    contrastColor: Color = Color.Black,
    content: @Composable () -> Unit = {},
) {
    Surface(
        modifier =
            modifier
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = if (isSelected) Color.Black else Color.Transparent,
                    shape =
                        RoundedTopCornersShape(
                            cornerRadius = 16.dp,
                        ),
                ),
        color = if (isSelected) MaterialTheme.colorScheme.background else contrastColor,
        shape =
            RoundedTopCornersShape(
                cornerRadius = 16.dp,
            ),
    ) {
        content()
    }
}

class RoundedTopCornersShape(
    private val cornerRadius: Dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        with(density) {
            val path =
                Path().apply {
                    moveTo(0f, size.height)
                    lineTo(0f, cornerRadius.toPx())
                    arcTo(
                        rect =
                            Rect(
                                offset = Offset(0f, 0f),
                                size = Size(cornerRadius.toPx() * 2, cornerRadius.toPx() * 2),
                            ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false,
                    )
                    lineTo(size.width - cornerRadius.toPx(), 0f)
                    arcTo(
                        rect =
                            Rect(
                                offset = Offset(size.width - cornerRadius.toPx() * 2, 0f),
                                size = Size(cornerRadius.toPx() * 2, cornerRadius.toPx() * 2),
                            ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false,
                    )
                    lineTo(size.width, size.height)
                }
            return Outline.Generic(path)
        }
    }
}

@Preview
@Composable
fun CustomBorderBoxPreview() {
    CustomBorderBox()
}
