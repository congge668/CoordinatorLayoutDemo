package com.cong.coordinatorlayoutdemo.bean

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList


/**
 * 活动-城市列表
 */
data class CityAllBean(
        val city: List<City>,
        val hot_city: List<String>? = null
) {
    data class City(
            val citylist: List<String>,
            val letter: String
    )
}

class RecBean {

    var cityList: List<CityListBean>? = null

    class CityListBean {

        var name: String? = null
        //用来区分城市拼音域
        var tage = -1
    }
}

data class CityAllNewBean(
    val `data`: CityAllBean? = null,
    val message: String? = null,
    val result: String? = null,
    val type: String? = null
)










