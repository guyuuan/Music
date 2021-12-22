package cn.chitanda.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import cn.chitanda.music.ui.LocalThemeViewModel
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.Router
import cn.chitanda.music.ui.scene.ThemeViewModel
import cn.chitanda.music.ui.scene.UserViewModel
import cn.chitanda.music.ui.theme.MusicTheme
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel by viewModels<ThemeViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalCoilApi::class,
        ExperimentalMaterialApi::class,
        ExperimentalPagerApi::class,
        ExperimentalAnimationApi::class,
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalThemeViewModel provides themeViewModel,
                LocalUserViewModel provides userViewModel
            ) {
                MusicTheme(themeViewModel.monetColor.value) {
                    Router()
                }
            }
        }
    }

}