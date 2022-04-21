package cn.chitanda.music

import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import cn.chitanda.music.ui.LocalMusicViewModel
import cn.chitanda.music.ui.LocalThemeViewModel
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.Router
import cn.chitanda.music.ui.scene.LocaleUserViewModel
import cn.chitanda.music.ui.scene.ThemeViewModel
import cn.chitanda.music.ui.theme.MusicTheme
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val themeViewModel by viewModels<ThemeViewModel>()
    private val userViewModel by viewModels<LocaleUserViewModel>()
    private val musicViewModel by viewModels<MusicViewModel>()

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
                LocalUserViewModel provides userViewModel,
                LocalMusicViewModel provides musicViewModel
            ) {
                MusicTheme(themeViewModel.monetColor.value) {
                    Router()
                }
            }
        }
        //在Android12中添加对等待登录完成后再移除splashScreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            keepSplashScreen()
        }

    }


    private fun keepSplashScreen() {
        window.decorView.viewTreeObserver?.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return (themeViewModel.isReady.value && userViewModel.isReady.value).also {
                    if (it) {
                        window.decorView.viewTreeObserver?.removeOnPreDrawListener(this)
                    }
                }
            }
        })
    }

}