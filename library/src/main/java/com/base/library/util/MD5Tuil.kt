package com.base.library.util

import java.security.MessageDigest

/**
 * 采用MD5加密解密
 */
object MD5Utils {
  /***
   * MD5加码 生成32位md5码
   */
  fun string2MD5(inStr: String): String {
    var md5: MessageDigest? = null
    try {
      md5 = MessageDigest.getInstance("MD5")
    } catch (e: Exception) {
      println(e.toString())
      e.printStackTrace()
      return ""
    }

    val charArray = inStr.toCharArray()
    val byteArray = ByteArray(charArray.size)

    for (i in charArray.indices)
      byteArray[i] = charArray[i].toByte()
    val md5Bytes = md5!!.digest(byteArray)
    val hexValue = StringBuffer()
    for (i in md5Bytes.indices) {
      val `val` = md5Bytes[i].toInt() and 0xff
      if (`val` < 16)
        hexValue.append("0")
      hexValue.append(Integer.toHexString(`val`))
    }
    return hexValue.toString()

  }
}
