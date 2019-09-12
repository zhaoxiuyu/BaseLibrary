package com.base.library.util

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.math.BigDecimal
import java.util.*
import kotlin.collections.HashMap

/**
 * Gson 工具类
 */
object JsonUtils {

    private val gson: Gson

    init {
        val gb = GsonBuilder()
        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING)
        gb.registerTypeAdapter(Double::class.java, JsonSerializer<Double> { originalValue, _, _ ->
            val bigValue = BigDecimal.valueOf(originalValue)
            if (originalValue == bigValue.toDouble()) {
                JsonPrimitive(bigValue.toInt())
            } else {
                JsonPrimitive(originalValue)
            }
        })
        gb.registerTypeAdapter(Long::class.java, JsonSerializer<Long> { originalValue, _, _ ->
            val bigValue = BigDecimal.valueOf(originalValue)
            JsonPrimitive(bigValue.toPlainString())
        })
        gb.registerTypeAdapter(Int::class.java, JsonSerializer<Int> { originalValue, _, _ ->
            val bigValue = BigDecimal.valueOf(originalValue.toLong())
            JsonPrimitive(bigValue.toPlainString())
        })
        gb.registerTypeAdapter(
            object : TypeToken<TreeMap<String, Any>>() {}.type,
            JsonDeserializer { json, _, _ ->
                val treeMap = TreeMap<String, Any>()
                val jsonObject = json.asJsonObject
                val entrySet = jsonObject.entrySet()
                for ((key, value) in entrySet) treeMap[key] = value
                treeMap
            })
        gson = gb.create()
    }

    /**
     * 获取 gson
     */
    fun getGson() = gson

    /**
     * 将 String 解析成指定泛型
     */
    fun <T> toAny(str: String, t: Class<T>): T = gson.fromJson(str, t)

    /**
     * 将 Any 解析成指定泛型
     */
    fun <T> toAny(any: Any, t: Class<T>): T = gson.fromJson(gson.toJson(any) ?: "", t)

    /**
     * 将 Any 解析成指定泛型并返回
     * val token = TypeToken<ArrayList<BannerVo>>(){};
     * val list = JsonUtil.getObject(response.dataInfo, object : TypeToken<ArrayList<BannerVo>>(){})
     */
    fun <T> toAny(str: String, token: TypeToken<T>): Any {
        return gson.fromJson(str, token.type)
    }

    /**
     * 将 Any 解析成指定泛型并返回
     */
    fun <T> toAny(any: Any, token: TypeToken<T>): Any {
        return gson.fromJson(gson.toJson(any), token.type)
    }

    /**
     * 将指定类变成 String
     */
    fun <T> toJson(any: T): String = gson.toJson(any)

    /**
     * 将 String 解析 Map
     */
    fun toMap(str: String): Map<String, Any> {
        val jo = JSONObject(str)
        val keys = jo.keys()
        val map = HashMap<String, Any>()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jo.get(key).toString()
            map[key] = value
        }
        return map
    }
}