package com.example.xlu.ui.login.ui

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.xlu.MainActivity
import com.example.xlu.R
import com.example.xlu.ui.theme.Roboto


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    activity: MainActivity,
    signInLauncher: ActivityResultLauncher<Intent>
){
    viewModel.tokenDevice()
    Box(modifier = Modifier.fillMaxWidth(), Alignment.TopEnd){
        HeaderImage()
    }
    Box(modifier = Modifier
        .padding(
            top = dimensionResource(id = R.dimen.dp120),
            bottom = dimensionResource(id = R.dimen.dp20),
            start = dimensionResource(id = R.dimen.dp40),
            end = dimensionResource(id = R.dimen.dp40)
        )
        .fillMaxSize()) {
        Login(signInLauncher,navController,Modifier.align(Alignment.TopCenter), viewModel,activity)
    }
}

@Composable
fun Login(
    signInLauncher: ActivityResultLauncher<Intent>,
    navController: NavController,
    modifier: Modifier,
    viewModel: LoginViewModel,
    activity: MainActivity
) {
    val email:String by viewModel.email.observeAsState(initial = "")
    val password:String by viewModel.password.observeAsState(initial = "")
    val enableButton: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isErrorEmail:Boolean by viewModel.isErrorEmail.observeAsState(initial = false)
    val isErrorPassword: Boolean by viewModel.isErrorPassword.observeAsState(initial = false)
    val paddingValue = dimensionResource(id = R.dimen.dp10)
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    val titleUser = stringResource(id = R.string.User)
    val titlePassword = stringResource(id = R.string.password)

    Column(modifier = modifier) {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(paddingValue))
        Field(email,titleUser) { viewModel.onLoginChanged(it, password) }
        Spacer(modifier = Modifier.padding(paddingValue))
        Field(password,titlePassword) {viewModel.onLoginChanged(email,it)}
        if (isErrorPassword){
            val typeError = stringResource(id = R.string.password_incorrect)
            ErrorField(typeError, fontSize)
        }else if (isErrorEmail){
            val typeError = stringResource(id = R.string.email_not_registered)
            ErrorField(typeError, fontSize)
        }
        Spacer(modifier = Modifier.padding(paddingValue))
        ForgotPassword()
        Spacer(modifier = Modifier.padding(paddingValue))
        LoginButton(enableButton) {viewModel.onLoginSelected(navController)}
        Spacer(modifier = Modifier.padding(paddingValue))
        OptionsLogin(signInLauncher,viewModel,activity) {viewModel.onSignUp(navController)}

    }
}

@Composable
fun OptionsLogin(
    signInLauncher: ActivityResultLauncher<Intent>,
    viewModel: LoginViewModel,
    activity: MainActivity,
    onSignUp: () -> Unit
) {
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    val paddingValue = dimensionResource(id = R.dimen.dp15)
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = stringResource(id = R.string.or_sign_in_with),
            fontSize = fontSize,
            color = Color(0xFF353535),
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.padding(paddingValue))
        ButtonsLogin(viewModel){viewModel.signInWithGoogle(activity,signInLauncher)}
        Spacer(modifier = Modifier.padding(paddingValue))
        SignUp(onSignUp)
        LikeGuest()
    }
}

@Composable
fun LikeGuest() {
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    Text(
        text = stringResource(id = R.string.continue_like_guest),
        fontSize = fontSize,
        color = Color(0xFF353535),
        textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
    )
}

@Composable
fun SignUp( onSignUp: () -> Unit) {
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    Row {
        Text(
            text = stringResource(id = R.string.you_need_a_account),
            fontSize = fontSize,
            color = Color(0xFF353535),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp5)))
        Text(
            text = stringResource(id = R.string.sign_up),
            fontSize = fontSize,
            color = Color(0xFF353535),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.
            clickable(enabled = true){onSignUp()}
        )
    }
}


@Composable
fun ButtonsLogin(
    viewModel: LoginViewModel,
    loginWithGoogle: () -> Unit
) {
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    val paddingValue = dimensionResource(id = R.dimen.dp10)
    val size = dimensionResource(id = R.dimen.dp50)
    val notAccess:Boolean by viewModel.notAccessByGoogle.observeAsState(initial = false)
    Row {
        OutlinedButton(onClick = { /*TODO*/ },
            modifier = Modifier.size(size),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.apple_icon),
                contentDescription = stringResource(
                    id = R.string.apple_logo
                )
            )
        }
        Spacer(modifier = Modifier.padding(start = paddingValue, end = paddingValue))
        OutlinedButton(onClick = { /*TODO*/ },
            modifier = Modifier.size(size),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons_facebook),
                contentDescription = stringResource(
                    id = R.string.facebook_logo
                )
            )
        }
        Spacer(modifier = Modifier.padding(start = paddingValue, end = paddingValue))
        OutlinedButton(
            onClick = { loginWithGoogle() },
            modifier = Modifier.size(size),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons_google),
                contentDescription = stringResource(
                    id = R.string.google_logo
                )
            )
        }
    }

    if(notAccess){
            Text(text = stringResource(id = R.string.access_denied),
                color=Color(0xFFFF0000),
                fontSize = fontSize,
                fontFamily = Roboto,
                modifier = Modifier.padding(start = paddingValue))
    }
}

@Composable
fun HeaderImage() {
    Image(painter = painterResource(id = R.drawable.header1), contentDescription = stringResource(id = R.string.header_image))
}

@Composable
fun LoginButton(enableButton: Boolean, onLoginSelected: () -> Unit) {
    val height = dimensionResource(id = R.dimen.dp50)
    val fontSize = dimensionResource(id = R.dimen.dp18).value.sp
    Button(onClick = { onLoginSelected() },
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
            text = stringResource(id = R.string.login),
            color = Color(0xFFFFFFFF),
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize
        )
    }
}

@Composable
fun ForgotPassword() {
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    val paddingValue = dimensionResource(id = R.dimen.dp10)
    Text(text = stringResource(id = R.string.forgot_Password),
        fontSize = fontSize,
        fontWeight = FontWeight.Normal,
        fontFamily = Roboto,
        color = Color(0xFF1B1B1B),
        modifier = Modifier.padding(start = paddingValue)
    )
}

@Composable
fun Field(value:String,title:String, onTextFieldChanged:(String) -> Unit) {
    val fontSize = dimensionResource(id = R.dimen.dp15).value.sp
    Text(text = title,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        fontFamily = Roboto,
        color = Color(0xFF181818)
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp3)))
    OutlinedTextField(
        value = value, onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dp60)),
        shape = RoundedCornerShape(40),
        placeholder = { Text(text = "") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF090909),
            backgroundColor = Color(0xFFECECEC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun ErrorField(typeError: String, fontSize: TextUnit){
    Text(text = typeError,
        color=Color(0xFFFF0000),
        fontSize = fontSize,
        fontFamily = Roboto,
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp10))
    )
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.logo_dark),
        contentDescription = stringResource(id = R.string.description_logo_app),
        modifier = modifier)
}

