package com.base.common.entitys.response

class WanLogin {

    var admin: String = "" //  ": false,
    var email: String = "" //  ": "",
    var icon: String = "" //  ": "",
    var nickname: String = "" //  ": "zxy_it@yeah.net",
    var password: String = "" //  ": "",
    var publicName: String = "" //  ": "zxy_it@yeah.net",
    var token: String = "" //  ": "",
    var username: String = "" //  ": "zxy_it@yeah.net"

    override fun toString(): String {
        return "WanLogin(admin='$admin', email='$email', icon='$icon', nickname='$nickname', password='$password', publicName='$publicName', token='$token', username='$username')"
    }

}