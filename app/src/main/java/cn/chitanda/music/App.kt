package cn.chitanda.music

import android.app.Application
import cn.chitanda.dynamicstatusbar.DynamicStatusBar
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

/**
 *@author: Chen
 *@createTime: 2021/8/31 16:01
 *@description:
 **/
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        DynamicStatusBar.mode = DynamicStatusBar.Mode.Fast
    }
}