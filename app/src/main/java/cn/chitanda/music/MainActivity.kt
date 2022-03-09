package cn.chitanda.music

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        var statsHolder: PerformanceMetricsState.MetricsStateHolder? = null
            private set
    }

    private val themeViewModel by viewModels<ThemeViewModel>()
    private val userViewModel by viewModels<LocaleUserViewModel>()
    private val musicViewModel by viewModels<MusicViewModel>()
    private lateinit var jankStats: JankStats

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

        // metrics state holder can be retrieved regardless of JankStats initialization
        val metricsStateHolder = PerformanceMetricsState.getForHierarchy(window.decorView)
        statsHolder = metricsStateHolder
        // initialize JankStats for current window
        jankStats = JankStats.createAndTrack(
            window,
            Dispatchers.Default.asExecutor(),
        ) { df ->
            if (df.isJank) Log.i(
                "JankStats",
                "${df.states}该帧 绘制耗时${df.frameDurationUiNanos / 1000000f}ms"
            )
        }
    }

    override fun onResume() {
        super.onResume()
        jankStats.isTrackingEnabled = true
    }

    override fun onPause() {
        jankStats.isTrackingEnabled = false
        super.onPause()
    }

    override fun onDestroy() {
        statsHolder = null
        super.onDestroy()
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