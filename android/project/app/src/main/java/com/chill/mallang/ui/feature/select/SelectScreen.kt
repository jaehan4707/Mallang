package com.chill.mallang.ui.feature.select

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Faction
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.PercentageBar
import com.chill.mallang.ui.feature.nickname.TextWithIcon
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Red01
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.Typography
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

@Composable
fun SelectScreen(
    navigateToMain: () -> Unit = {},
    viewModel: SelectViewModel = hiltViewModel(),
    onShowErrorSnackBar: (message: String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HandleSignUpEvent(
        signUpUiState = uiState,
        onSuccess = navigateToMain,
        event = viewModel.event,
        showSnackBar = { errorMessage ->
            onShowErrorSnackBar(errorMessage)
        },
        onSignUp = {
            viewModel.join(it)
        },
    )
}

@Composable
fun HandleSignUpEvent(
    signUpUiState: SignUpUiState,
    event: SharedFlow<SignUiEvent>,
    showSnackBar: (message: String) -> Unit,
    onSuccess: () -> Unit,
    onSignUp: (team: String?) -> Unit,
) {
    LaunchedEffect(event) {
        event.collectLatest { signUiEvent ->
            when (signUiEvent) {
                is SignUiEvent.Error -> showSnackBar(signUiEvent.message)
                SignUiEvent.SignUpSuccess -> onSuccess()
            }
        }
    }
    SelectContent(
        factionsStatus = signUpUiState.factionsStatus,
        onSignUp = onSignUp,
    )
}

@Composable
fun SelectContent(
    factionsStatus: PersistentList<Faction> = persistentListOf(),
    onSignUp: (String?) -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_background))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    var selectedTeam by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_title),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.6f),
            )
            Spacer(modifier = Modifier.weight(0.2f))
            TextWithIcon(
                text = stringResource(R.string.select_faction_message),
                icon = R.drawable.ic_team,
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (factionsStatus.isNotEmpty()) {
                PercentageBar(
                    leftPercentage = factionsStatus.first().ratio.roundToInt(),
                    rightPercentage = factionsStatus.last().ratio.roundToInt(),
                    leftLabel = factionsStatus.first().name,
                    rightLabel = factionsStatus.last().name,
                    leftColor = Red01,
                    rightColor = SkyBlue,
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TeamButton(
                    onClick = {
                        selectedTeam = it
                    },
                    text = stringResource(R.string.team_mal),
                    color = Red01,
                    isSelected = selectedTeam == stringResource(R.string.team_mal),
                )
                Spacer(modifier = Modifier.width(25.dp))
                TeamButton(
                    onClick = {
                        selectedTeam = it
                    },
                    text = stringResource(R.string.team_rang),
                    color = SkyBlue,
                    isSelected = selectedTeam == stringResource(R.string.team_rang),
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            if (selectedTeam == stringResource(id = R.string.team_mal)) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.2f),
                    painter = painterResource(id = R.drawable.img_mal_default_character),
                    contentDescription = null,
                )
            } else if (selectedTeam == stringResource(id = R.string.team_rang)) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.2f),
                    painter = painterResource(id = R.drawable.img_lang_default_character),
                    contentDescription = null,
                )
            }
            if (selectedTeam != null) {
                Spacer(modifier = Modifier.height(50.dp))
                LongBlackButton(
                    onClick = { onSignUp(selectedTeam) },
                    text = stringResource(id = R.string.button_decide_message),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// 팀 버튼
@Composable
fun TeamButton(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    text: String,
    color: Color,
    isSelected: Boolean,
) {
    Button(
        onClick = { onClick(text) },
        colors =
            ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = color,
            ),
        shape = RoundedCornerShape(13.dp),
        modifier =
            modifier
                .width(100.dp)
                .height(48.dp)
                .then(
                    if (isSelected) {
                        Modifier.border(
                            2.dp,
                            Gray6,
                            RoundedCornerShape(13.dp),
                        )
                    } else {
                        Modifier
                    },
                ),
    ) {
        Text(
            text,
            style = Typography.displayLarge,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectScreenPreview() {
    MallangTheme {
        SelectContent()
    }
}
