package cn.chitanda.music.preference

class PreferenceManager(
    cookiesPreference: CookiesPreference,
    uidPreference: UidPreference,
    themeColorPreference: ThemeColorPreference,
) {
    var uid by uidPreference
    var cookies by cookiesPreference
    var themeColor by themeColorPreference
}