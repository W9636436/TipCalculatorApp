package com.example.tipcalculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import com.example.tipcalculator.util.calculateTip
import com.example.tipcalculator.util.calculateTip1
import com.example.tipcalculator.util.calculateTotalPerPerson
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun Content() {
    val modifier = Modifier.padding(8.dp)
    val radioOptions = listOf("Amazing (20%)", "Good (18%)", "OK (15%)")
    val textField = remember { mutableStateOf(TextFieldValue("")) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val checkedState = remember { mutableStateOf(true) }
    val tip = remember { mutableStateOf(0.00) }
    val tipPercentage = when (selectedOption) {
        radioOptions[0] -> 0.20
        radioOptions[1] -> 0.18
        else -> 0.15
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = null,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_store_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )

            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Cost of Service") },
                placeholder = { Text("Cost of Service") },
                value = textField.value,
                onValueChange = {
                    textField.value = it
                    tip.value =
                        calculateTip(textField.value.text, tipPercentage, checkedState.value)
                },
                modifier = modifier.padding(start = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = null,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_room_service_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text("How was the service?", modifier.padding(start = 16.dp))
        }
        radioOptions.forEach { text ->
            Row(
                modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            tip.value = calculateTip(
                                textField.value.text,
                                tipPercentage,
                                checkedState.value
                            )
                        }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                        tip.value =
                            calculateTip(
                                textField.value.text,
                                tipPercentage,
                                checkedState.value
                            )
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = null,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_call_made_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text(" Round up tip?", modifier = modifier.padding(start = 16.dp))
            Spacer(Modifier.requiredWidth(150.dp))
            Switch(
                colors = SwitchDefaults.colors(MaterialTheme.colors.secondary),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    tip.value =
                        calculateTip(textField.value.text, tipPercentage, checkedState.value)
                }
            )
        }
        Divider(modifier = Modifier.padding(8.dp))
        Text("Tip Amount: $${tip.value}", modifier = modifier.align(Alignment.End))
    }
}


@Composable
fun HomeScreen(navController: NavHostController) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text("Tip Time")
//                }
//            )
//        }
//    ) {it.calculateTopPadding()

    Column {
        TopAppBar(
            title = {
                Text(text = "Tip Calculator", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
                IconButton(onClick = {/* Do Something*/ }) {
//                Icon(Icons.Filled.Home, null)
                    Image(
                        painter = painterResource(id = R.drawable.tip_cal),
                        contentDescription = null,
                        Modifier.size(24.dp)
                    )
                }
            },
            actions = {
                Button(
                    onClick = {
                        val authHelper = FirebaseAuth.getInstance()
                        authHelper.signOut()
                        navController.navigate(DestinationScreen.LoginScreenDest.route){
                            popUpTo(route = DestinationScreen.HomeScreenDest.route) {
                                inclusive = true
                            }
                        }



                    }, colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "Logout", color = Color.White )
                }
            },
            backgroundColor =Color(0xFF4CAF50)
        )
        MainContent()
    }
//    }
}

@Composable
fun JetTipApp() {
    TipCalculatorTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Column {
                MainContent()
            }
        }
    }
}


@Composable
fun TopHeaderSection(totalPerPerson: Double = 0.0) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xffE9D7F7)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person", style = MaterialTheme.typography.h6)
            Text(
                text = "$${String.format("%.2f",totalPerPerson)}",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.ExtraBold
            )
        }

    }
}


@Composable
fun MainContent() {
    val timAmount = remember {
        mutableStateOf(0.0)
    }
    val totalPerson = remember {
        mutableStateOf(0.0)
    }
    val splitBy = remember {
        mutableStateOf(1)
    }

    BillForm(
        splitByState = splitBy,
        tipAmountState = timAmount,
        totalPerPersonState = totalPerson,
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {},
) {
    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    val tipPercent = (sliderPositionState.value)

Column {
    TopHeaderSection(totalPerPersonState.value)

    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column {
            InputField(
                valueState = totalBillState, label = "Enter bill",
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyBoardController?.hide()
                }
            )

            if (validState) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(20.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(imageVector = Icons.Default.Close,
                            onClick = {
                                splitByState.value = if (splitByState.value > 1) splitByState.value - 1
                                else 1

                                totalPerPersonState.value = calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercent = tipPercent
                                )

                            })
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "${splitByState.value}",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        RoundIconButton(imageVector = Icons.Default.Add,
                            onClick = {
                                splitByState.value += 1

                                totalPerPersonState.value = calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercent = tipPercent
                                )

                            })

                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "$${tipAmountState.value.toInt()}",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )

                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${tipPercent.toInt()}%")
                    Spacer(modifier = Modifier.height(20.dp))
                    Slider(value = sliderPositionState.value,
                        valueRange = 0f..100f,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        onValueChange = { newVal ->
                            sliderPositionState.value = newVal
                            tipAmountState.value =
                                calculateTip1(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercent = tipPercent
                                )

                            totalPerPersonState.value = calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitByState.value,
                                tipPercent = tipPercent
                            )

                        })
                }
            } else {
                Box {

                }
            }
        }
    }

}

}