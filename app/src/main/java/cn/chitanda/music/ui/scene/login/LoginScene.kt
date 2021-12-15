package cn.chitanda.music.ui.scene.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.R
import cn.chitanda.music.http.DataState
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.Scene

/**
 *@author: Chen
 *@createTime: 2021/8/31 13:35
 *@description:
 **/
@Composable
fun LoginScene(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController = LocalNavController.current
) {
    val login by viewModel.login.collectAsState()
    var showLoading by remember {
        mutableStateOf(false)
    }
    val cxt = LocalContext.current
    var accountName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Surface(color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.padding(
                    start = 36.dp,
                    top = 54.dp,
                    end = 36.dp,
                    bottom = 16.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = accountName, onValueChange = {
                    accountName = it
                }, label = {
                    Text(text = stringResource(R.string.text_enter_phone_number))
                }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    value = password, onValueChange = {
                        password = it
                    }, singleLine = true,
                    label = {
                        Text(text = stringResource(R.string.text_enter_password))
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(shape = RoundedCornerShape(30.dp), onClick = {
                    if (accountName.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(username = accountName, password = password)
                    }
                }) {
                    Text(text = "Login", fontSize = 18.sp)
                }
            }
        }
    }

    if (showLoading) {
        Dialog(onDismissRequest = {
        }) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = login) {
        when (login.status) {
            DataState.STATE_LOADING -> {
                showLoading = true
            }
            DataState.STATE_SUCCESS -> {
                showLoading = false
                Toast.makeText(cxt, "login success", Toast.LENGTH_SHORT).show()
                navController.navigate(Scene.Home.id) {
                    popUpTo(Scene.Login.id) { inclusive = true }
                }
            }
            DataState.STATE_FAILED -> {
                showLoading = false
                Toast.makeText(cxt, login.msg.toString(), Toast.LENGTH_SHORT).show()
            }
            DataState.STATE_ERROR -> {
                showLoading = false
                Toast.makeText(cxt, login.error.toString(), Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }
}

private const val TAG = "LoginScene"