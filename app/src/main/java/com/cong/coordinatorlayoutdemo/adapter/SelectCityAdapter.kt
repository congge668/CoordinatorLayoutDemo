package com.cong.coordinatorlayoutdemo.adapter

import android.view.View
import android.widget.TextView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cong.coordinatorlayoutdemo.R
import com.cong.coordinatorlayoutdemo.bean.RecBean


/**
 * @author ：congge
 * @date : 2019/12/4 9:58
 * @desc :选择城市adapter
 **/
class SelectCityAdapter(layoutResId: Int, data: List<RecBean.CityListBean>? = null) : BaseQuickAdapter<RecBean.CityListBean, BaseViewHolder>(layoutResId, data){


    override fun convert(helper: BaseViewHolder, item:RecBean.CityListBean) {
        helper.setText(R.id.tv_city,item.name)

    }



}