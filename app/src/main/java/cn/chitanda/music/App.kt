package cn.chitanda.music

import android.app.Application
import android.util.Log
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
        try{
            val clazz = DynamicStatusBar.Mode::class.java
            clazz.getDeclaredField("delayTime")?.let {
                it.isAccessible = true
                it.set(DynamicStatusBar.mode, 250)
            }
        }catch (e:Exception){
            Log.d("Main-TAG", "onCreate: ",e)
        }
    }
}