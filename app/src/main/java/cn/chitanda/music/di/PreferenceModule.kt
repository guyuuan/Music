package cn.chitanda.music.di

import cn.chitanda.music.preference.CookiesPreference
import cn.chitanda.music.preference.PreferenceManager
import cn.chitanda.music.preference.UidPreference
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *@author: Chen
 *@createTime: 2021/8/13 16:48
 *@description:
 **/
@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun provideMMKV() = MMKV.defaultMMKV() ?: throw RuntimeException("Can't get default mmkv")

    @Provides
    @Singleton
    fun provideCookiesPreference(mmkv: MMKV) = CookiesPreference(mmkv)

    @Provides
    @Singleton
    fun provideUidPreference(mmkv: MMKV) = UidPreference(mmkv)

    @Provides
    @Singleton
    fun providePreferenceManager(
        cookiesPreference: CookiesPreference,
        uidPreference: UidPreference
    ) = PreferenceManager(cookiesPreference, uidPreference)
}