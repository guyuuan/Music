package cn.chitanda.music.http

import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.preference.CookiesPreference
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

private const val TAG = "MyCookieJar"

class MyCookieJar(cookiesPreference: CookiesPreference) : CookieJar {
    private var cookies by cookiesPreference
    private val jsonAdapter = moshi.adapter(
        Cookie::class.java
    )

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (url.host == "music.chitanda.cn") {
            val cookie = try {
                jsonAdapter.fromJson(cookies)
            } catch (e: Exception) {
                null
            }
            return cookie?.let { listOf(it) } ?: emptyList()
        }
        return emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.host == "music.chitanda.cn") {
            cookies.find { cookie -> cookie.name == "MUSIC_U" }?.let {
                this.cookies = jsonAdapter.toJson(it)
            }
        }
    }

}
