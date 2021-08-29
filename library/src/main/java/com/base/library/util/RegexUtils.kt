package com.base.library.util

import com.blankj.utilcode.util.RegexUtils

object RegexUtils {

    const val HTML = "/^<([a-z]+)([^<]+)*(?:>(.*)<\\/\\1>|\\s+\\/>)\$/"

    /**
     * 是否是HTML
     */
    fun isHTML(str: String): Boolean {
        return RegexUtils.isMatch(HTML, str)
    }

}