package cn.chitanda.music.preference

import com.tencent.mmkv.MMKV

/**
 *@author: Chen
 *@createTime: 2021/8/13 17:54
 *@description:
 **/
class CookiesPreference(mmkv: MMKV) : MMKVPreference<Set<String>>(mmkv, "cookies", emptySet())