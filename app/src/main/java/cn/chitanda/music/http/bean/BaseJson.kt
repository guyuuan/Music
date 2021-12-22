package cn.chitanda.music.http.bean

/**
 *@author: Chen
 *@createTime: 2021/8/31 15:15
 *@description:
 **/

abstract class BaseJson<T> {
    abstract val code: Int
    abstract val message: String?
    abstract val msg: String?
    abstract val data: T?
}
/*
*     @Json(name = "code")
    val _code: Int,
    @Json(name = "message")
    val _message: String,
    @Json(name = "msg")
    val _msg: String,
* */