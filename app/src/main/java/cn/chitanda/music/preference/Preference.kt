package cn.chitanda.music.preference

import com.tencent.mmkv.MMKV

/**
 *@author: Chen
 *@createTime: 2021/8/13 17:54
 *@description:
 **/
class CookiesPreference(mmkv: MMKV) : MMKVPreference<String>(mmkv, "cookies", "")
class UidPreference(mmkv: MMKV) : MMKVPreference<String>(mmkv, "uid", "")
class ThemeColorPreference(mmkv: MMKV) : MMKVPreference<Int>(mmkv, "theme_color", Int.MIN_VALUE)