@file:Suppress("DEPRECATION")

package com.cong.coordinatorlayoutdemo.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


object UtilHelper {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context?, dpValue: Float): Int {
        val scale = context?.resources?.displayMetrics?.density
        return (dpValue * scale!! + 0.5f).toInt()
    }

    fun getJson(context: Context, fileName: String?): String? {
        val stringBuilder = StringBuilder()
        //获得assets资源管理器
        val assetManager = context.assets
        //使用IO流读取json文件内容
        try {
            val bufferedReader = BufferedReader(InputStreamReader(
                    assetManager.open(fileName), "utf-8"))
            var line: String? = ""
            while (bufferedReader.readLine().also({ line = it }) != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    fun <T> JsonToObject(json: String?, type: Class<T>?): T {
        Log.i("JsonToObject",json)
        val gson = Gson()
        return gson.fromJson(json, type)
    }
}
