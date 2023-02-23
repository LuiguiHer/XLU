package com.example.xlu.ui.sign_up.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.xlu.R
import com.example.xlu.ui.login.ui.Field
import com.example.xlu.ui.theme.Roboto

@Composable
fun SingUpScreen(navController: NavController, viewModel: SignUpViewModel) {
    viewModel.tokenDevice()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.signUp_dp20)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    )
    {
        ButtonBackSignUp { viewModel.onBackSelected(navController) }
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom)
    {
        ImageSignUp()
    }
    Box(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.signUp_dp90),
                bottom = dimensionResource(id = R.dimen.signUp_dp20),
                start = dimensionResource(id = R.dimen.signUp_dp40),
                end = dimensionResource(id = R.dimen.signUp_dp40)
            )
            .fillMaxSize()
    )
    {
        SignUp(navController, viewModel)
    }

}

@Composable
fun ButtonBackSignUp(onSignUpSelect: () -> Unit) {

    OutlinedButton(
        onClick = { onSignUpSelect() },
        modifier = Modifier.size(dimensionResource(id = R.dimen.signUp_dp50)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.signUp_dp5)),
        border = BorderStroke(0.dp, Color(0xFF8B8B8B))
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = stringResource(
                id = R.string.button_back
            )
        )
    }
}

@Composable
fun ImageSignUp() {
    Image(
        painter = painterResource(id = R.drawable.header_bottom),
        contentDescription = stringResource(
            id = R.string.header_image
        )
    )
}

@Composable
fun SignUp(navController: NavController, viewModel: SignUpViewModel) {
    Column {
        val name: String by viewModel.name.observeAsState(initial = "")
        val password: String by viewModel.password.observeAsState(initial = "")
        val email: String by viewModel.email.observeAsState(initial = "")
        val enableSignUp: Boolean by viewModel.signUpEnable.observeAsState(false)
        val isError: Boolean by viewModel.isError.observeAsState(initial = false)
        val userNameLabel = stringResource(id = R.string.user_name)
        val emailLabel = stringResource(id = R.string.email)
        val passwordLabel = stringResource(id = R.string.password)
        val fontSizeTitle = dimensionResource(id = R.dimen.signUp_dp50).value.sp
        val fontSize = dimensionResource(id = R.dimen.signUp_dp15).value.sp
        val paddingValue = dimensionResource(id = R.dimen.signUp_dp10)


        Text(
            text = stringResource(id = R.string.sign_up).uppercase(),
            fontSize = fontSizeTitle,
            fontWeight = FontWeight.Bold,
            fontFamily = Roboto,
            color = Color(0xFFFF2323)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.signUp_dp15)))
        Field(name, userNameLabel) { viewModel.onSignUpChanged(it, email, password) }
        Spacer(modifier = Modifier.padding(paddingValue))
        Field(email, emailLabel) { viewModel.onSignUpChanged(name, it, password) }
        if (isError) {
            Text(
                text = stringResource(id = R.string.email_registered),
                color = Color(0xFFFF0000),
                fontSize = fontSize
            )
        }
        Spacer(modifier = Modifier.padding(paddingValue))
        Field(password, passwordLabel) { viewModel.onSignUpChanged(name, email, it) }
        Text(
            text = stringResource(id = R.string.password_must_contain),
            fontStyle = FontStyle.Italic,
            fontSize = fontSize
        )
        Spacer(modifier = Modifier.padding(paddingValue))
        ButtonSignUp(enableSignUp) { viewModel.onSignUpSelected(navController) }
    }
}

@Composable
fun ButtonSignUp(enableButton: Boolean, onSignUpSelect: () -> Unit) {
    val height = dimensionResource(id = R.dimen.signUp_dp50)
    val fontSize = dimensionResource(id = R.dimen.signUp_dp18).value.sp
    Button(
        onClick = { onSignUpSelect() },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF1800),
            disabledBackgroundColor = Color(0xFFF36253),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = enableButton,
        shape = RoundedCornerShape(40)
    ) {
        Text(
            text = stringResource(id = R.string.accept),
            color = Color(0xFFFFFFFF),
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize
        )
    }
}

