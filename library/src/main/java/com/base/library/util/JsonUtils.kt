package com.base.library.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hjq.gson.factory.GsonFactory
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Gson 工具类
 */
object JsonUtils {

    /* private val gson: Gson

     init {
         val gb = GsonBuilder()
         gb.setLenient()

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
             Float::class.java,
             JsonSerializer<Float> { originalValue, _, _ ->
                 val bigValue = BigDecimal.valueOf(originalValue.toDouble())
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
     }*/

    /**
     * 获取 Gson 实例
     */
    //fun getGson(): Gson = gson

    fun getGson(): Gson {
        return GsonFactory.getSingletonGson()
    }

    /**
     * 将json解析成指定泛型并返回
     * @param str json数据
     * @param <T>    指定泛型
     */
    fun <T> getClass(str: String, t: Class<T>): T = getGson().fromJson(str, t)

    /**
     * 将object解析成指定泛型并返回
     * @param any json数据的object
     * @param <T> 指定泛型
     */
    fun <T> getClass(any: Any, t: Class<T>): T = getGson().fromJson(getGson().toJson(any) ?: "", t)

    /**
     * 将 Any 解析成指定泛型并返回
     * val token = TypeToken<ArrayList<BannerVo>>(){};
     * val list = JsonUtil.getClass(response.dataInfo, object : TypeToken<ArrayList<BannerVo>>(){})
     *
     * @param str json数据
     * @param token  解析类型token
     */
    fun <T> getClass(str: String, token: TypeToken<T>): Any {
        return getGson().fromJson(str, token.type)
    }

    /**
     * 将 Any 解析成指定泛型并返回
     * TypeToken token =  new TypeToken<ArrayList<BannerVo>>(){};
     * val list = JsonTool.getClass(response.data, object : TypeToken<ArrayList<SearchOrderVo>>(){})
     *
     * @param any   json数据的object
     * @param token 解析类型token
     */
    fun <T> getClass(any: Any, token: TypeToken<T>): Any {
        return getGson().fromJson(getGson().toJson(any), token.type)
    }

    /**
     * 将指定类变成Json型数据返回
     */
    fun <T> getJsonString(any: T): String = getGson().toJson(any)

    /**
     * 将json字符串解析成Map
     */
    fun getMap(str: String): Map<String, String>? {
        try {
            val mJSONObject = JSONObject(str)
            val keyIter = mJSONObject.keys()
            val valueMap = HashMap<String, String>()
            while (keyIter.hasNext()) {
                val key = keyIter.next()
                val value = mJSONObject.get(key).toString()
                valueMap[key] = value
            }
            return valueMap
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 将object解析成Map
     *
     * @param any 数据object
     */
    fun getMap(any: Any): Map<String, String>? {
        return getMap(getJsonString(any))
    }

    /**
     * 将json 数组转换为Map 对象
     */
    fun getMapObj(str: String): Map<String, Any>? {
        try {
            val mJSONObject = JSONObject(str)
            val keyIter = mJSONObject.keys()
            val valueMap = HashMap<String, Any>()
            while (keyIter.hasNext()) {
                val key = keyIter.next()
                val value = mJSONObject.get(key)
                valueMap[key] = value
            }
            return valueMap
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 将object解析成Map
     *
     * @param any 数据object
     */
    fun getMapObj(any: Any): Map<String, Any>? {
        return getMapObj(getJsonString(any))
    }

    /**
     * 把json 转换为ArrayList 形式
     */
    fun getList(str: String): List<Map<String, Any>>? {
        val list = mutableListOf<Map<String, Any>>()
        try {
            val mJSONArray = JSONArray(str)
            for (i in 0 until mJSONArray.length()) {
                val jsonObject = mJSONArray.getJSONObject(i)
                val mapObj = getMapObj(jsonObject)
                if (mapObj != null) {
                    list.add(mapObj)
                }
            }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 把json 转换为ArrayList 形式
     */
    fun getList(any: Any): List<Map<String, Any>>? {
        return getList(getJsonString(any))
    }

}