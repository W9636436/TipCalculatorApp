package com.example.tipcalculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
) {
    OutlinedTextField(
        modifier = modifier.padding(10.dp),
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = {
            Text(text = label)
        },
        enabled = enabled,
        singleLine = singleLine,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.dlr), contentDescription = "Money icon",Modifier.size(20.dp))
        },
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black,
    backGroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 4.dp,

    ) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(40.dp),
        onClick = onClick,
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(backGroundColor)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)) {
            Icon(imageVector = imageVector, contentDescription = "", tint = tint)
        }
    }
}