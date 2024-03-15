package me.darthwithap.hotel_app.presentation.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import me.darthwithap.hotel_app.R
import me.darthwithap.hotel_app.ui.components.ButtonSize
import me.darthwithap.hotel_app.ui.components.EmailInputField
import me.darthwithap.hotel_app.ui.components.PasswordInputField
import me.darthwithap.hotel_app.ui.components.PrimaryButton
import me.darthwithap.hotel_app.ui.theme.AppTheme

@Composable
fun RegisterScreen(
  onNavigateBackClick: () -> Unit,
  onRegisterAndAcceptClick: () -> Unit,
  onEmailValueChange: (String) -> Unit,
  onPasswordValueChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  RegisterScreenContent(
    onNavigateBackClick,
    onEmailValueChange,
    onPasswordValueChange,
    onRegisterAndAcceptClick,
    modifier
      .navigationBarsPadding()
      .systemBarsPadding(),
  )
}

@Composable
fun RegisterScreenContent(
  onNavigateBackClick: () -> Unit,
  onEmailValueChange: (String) -> Unit,
  onPasswordValueChange: (String) -> Unit,
  onRegisterAndAcceptClick: () -> Unit,
  modifier: Modifier
) {
  // Todo: Add Loading Widget
  Column(modifier = modifier.padding(16.dp)) {
    // Navigation bar with back icon
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onNavigateBackClick) {
        Icon(
          painter = painterResource(R.drawable.nav_appbar_back_chevron_black),
          contentDescription = stringResource(R.string.navigate_up),
        )
      }
    }
    Text(
      modifier = modifier,
      text = stringResource(id = R.string.register_screen_heading),
      style = AppTheme.typography.headlineSmall24Regular,
      //color = AppTheme.primaryTextColor
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = modifier,
      text = stringResource(id = R.string.register_screen_subheading),
      style = AppTheme.typography.forms16Regular,
      color = if (AppTheme.isDark) AppTheme.colors.white70 else AppTheme.colors.black70
    )

    Spacer(modifier = Modifier.height(26.dp))
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.Start
    ) {
      Text(
        text = stringResource(R.string.email_address),
        style = AppTheme.typography.caption12Regular
      )
      EmailInputField(
        modifier = Modifier.fillMaxWidth(),
        value = "", // state.email
        onValueChange = onEmailValueChange,
        isError = false, // !state.isValidEmail
        //supportingText = if (!state.isValidEmail) "Invalid email" else null,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = stringResource(R.string.set_password),
        style = AppTheme.typography.caption12Regular
      )
      PasswordInputField(
        modifier = Modifier.fillMaxWidth(),
        value = "Password", // state.password
        onValueChange = onPasswordValueChange,
        isError = true, // !state.isValidPassword
        //supportingText = if (!state.isValidPassword) "Invalid password" else null
      )
    }

    Spacer(modifier = Modifier.height(26.dp))
    PrivacyTermsText(onPrivacyPolicyClick = {}, onTermsAndConditionsClick = {})
    Spacer(modifier = Modifier.weight(1f))

    var buttonEnabled by remember { mutableStateOf(true) }
    PrimaryButton(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.register_and_accept),
      onClick = onRegisterAndAcceptClick,
      buttonSize = ButtonSize.Large,
      enabled = buttonEnabled // state.isRegisterButtonEnabled
    )
  }
}

@Composable
private fun PrivacyTermsText(
  onPrivacyPolicyClick: () -> Unit,
  onTermsAndConditionsClick: () -> Unit
) {

  val annotatedText = buildAnnotatedString {
    append("By tapping ")
    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
      append("Register & Accept")
    }
    append(" that you have read our ")

    pushStringAnnotation(tag = "privacy_policy", annotation = "privacy_policy")
    withStyle(style = SpanStyle(color = Color.Blue)) {
      append("Privacy Policy")
    }
    pop() // This call removes the last pushed annotation, so we can add more annotations later
    append(" and agree our ")
    pushStringAnnotation(tag = "terms_and_conditions", annotation = "terms_and_conditions")
    withStyle(style = SpanStyle(color = Color.Blue)) {
      append("Terms and Conditions")
    }
    pop()
  }

  ClickableText(
    text = annotatedText,
    onClick = { offset ->
      // We check if there's an annotation attached to the text at the clicked position
      annotatedText.getStringAnnotations(tag = "privacy_policy", start = offset, end = offset)
        .firstOrNull()?.let {
          onPrivacyPolicyClick()
        }
      annotatedText.getStringAnnotations(tag = "terms_and_conditions", start = offset, end = offset)
        .firstOrNull()?.let {
          onTermsAndConditionsClick()
        }
    }
  )
}