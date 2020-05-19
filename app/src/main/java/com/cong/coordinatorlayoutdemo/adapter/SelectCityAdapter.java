package com.cong.coordinatorlayoutdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cong.coordinatorlayoutdemo.R;
import com.cong.coordinatorlayoutdemo.bean.RecBean;

public class SelectCityAdapter extends BaseQuickAdapter<RecBean.CityListBean, BaseViewHolder> {


    public SelectCityAdapter() {
        super(R.layout.active_city_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecBean.CityListBean item) {
        helper.setText(R.id.tv_city,item.getName());
    }
}
