package cn.chitanda.music.http.bean

data class RefreshLogin(
    override val code: Int,
    override val msg: String?,
    override val message: String?,
    override val data: Nothing
) : BaseJson<Nothing>()
