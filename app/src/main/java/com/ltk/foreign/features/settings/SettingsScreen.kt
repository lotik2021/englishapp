package com.ltk.foreign.features.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ltk.foreign.R
import com.ltk.foreign.features.settings.component.SettingsComponent
import com.ltk.foreign.features.settings.component.SettingsSwitchCard
import com.ltk.foreign.features.settings.viewmodel.SettingsViewModel
import com.ltk.foreign.features.settings.viewmodel.ThemeViewModel

@Composable
fun SettingsScreen() {
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    val context = LocalContext.current
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val themeState by themeViewModel.themeState.collectAsState()

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let { settingsViewModel.onImport(uri) }
        }
    )

    Scaffold {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                /** App Settings. */

                item {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = stringResource(R.string.app_settings),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    SettingsSwitchCard(
                        text = stringResource(id = R.string.dark_mode),
                        icon = painterResource(id = R.drawable.ic_moon),
                        isChecked = themeState.isDarkMode,
                        onCheckedChange = {
                            themeViewModel.toggleTheme()
                        }
                    )
                }

                /** Import **/

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = stringResource(R.string.data),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )
                    SettingsComponent(
                        settingHeaderText = stringResource(R.string.import_data),
                        painterResourceID = R.drawable.ic_import
                    ) {
                        importLauncher.launch(arrayOf("*/*"))
                    }
                }
            }
        }
    }
}
