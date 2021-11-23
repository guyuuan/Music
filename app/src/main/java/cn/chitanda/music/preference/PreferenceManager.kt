package cn.chitanda.music.preference

class PreferenceManager(
    cookiesPreference: CookiesPreference,
    uidPreference: UidPreference
) {
    var uid by uidPreference
    var cookies by cookiesPreference
}