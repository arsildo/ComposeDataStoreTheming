package com.arsildo.composedatastorethemeing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrightnessAuto
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.arsildo.composedatastorethemeing.datastore.ApplicationThemes
import com.arsildo.composedatastorethemeing.datastore.ApplicationViewModel
import com.arsildo.composedatastorethemeing.datastore.ApplicationViewModelFactory
import com.arsildo.composedatastorethemeing.datastore.ChangeApplicationTheme
import com.arsildo.composedatastorethemeing.ui.theme.ComposeDataStoreThemingTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ApplicationViewModel by viewModels {
        ApplicationViewModelFactory((application as ChangeApplicationTheme).dataStoreRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val themeSetting =
                viewModel.themeSetting.collectAsState(ApplicationThemes.Automatic).value

            val userDarkTheme = themeSetting?.let {
                when (it) {
                    ApplicationThemes.Automatic -> isSystemInDarkTheme()
                    ApplicationThemes.Light -> false
                    ApplicationThemes.Dark -> true
                }
            }

            if (userDarkTheme != null) {
                ComposeDataStoreThemingTheme(darkTheme = userDarkTheme) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Text(
                            text = "Current Application Theme ${themeSetting?.name}",
                            color = MaterialTheme.colors.primary
                        )


                        ChangeThemeButton(
                            themeName = "Light Mode",
                            themeIcon = Icons.Rounded.LightMode
                        ) {
                            val theme = ApplicationThemes.Light
                            viewModel.changeTheme(theme)
                        }

                        ChangeThemeButton(
                            themeName = "Dark Mode",
                            themeIcon = Icons.Rounded.DarkMode
                        ) {
                            val theme = ApplicationThemes.Dark
                            viewModel.changeTheme(theme)
                        }

                        ChangeThemeButton(
                            themeName = "Automatic",
                            themeIcon = Icons.Rounded.BrightnessAuto
                        ) {
                            val theme = ApplicationThemes.Automatic
                            viewModel.changeTheme(theme)
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ChangeThemeButton(
    themeName: String,
    themeIcon: ImageVector,
    onThemeChanged: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.background
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onThemeChanged
    ) {
        Icon(
            themeIcon,
            "icon",
            modifier = Modifier
                .weight(.2f)
        )
        Text(
            "Change theme to $themeName",
            modifier = Modifier.weight(.8f)
        )
    }
}
