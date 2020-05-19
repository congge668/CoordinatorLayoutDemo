package com.cong.coordinatorlayoutdemo.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.CoordinatorLayout.Behavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.cong.coordinatorlayoutdemo.R
import com.cong.coordinatorlayoutdemo.adapter.SelectCityAdapter
import com.cong.coordinatorlayoutdemo.bean.CityAllBean
import com.cong.coordinatorlayoutdemo.bean.CityAllNewBean
import com.cong.coordinatorlayoutdemo.bean.RecBean
import com.cong.coordinatorlayoutdemo.decoration.RecItemHeadDecoration
import com.cong.coordinatorlayoutdemo.utils.UtilHelper
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_select_city.*
import java.lang.Math.abs


/**
 * @author ：congge
 * @date : 2019/12/16 15:14
 * @desc :活动城市搜索
 **/

class MSelectCityActivity : AppCompatActivity(){

    private lateinit var cityAdapter: SelectCityAdapter
    private var headDecoration: RecItemHeadDecoration? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mIndex = 0
    private var move = false
    private var  behavior:Behavior<View>?= null

    private var letterList = arrayListOf<String>()
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)
        context = this
        mLinearLayoutManager = LinearLayoutManager(this)
        rv_city.layoutManager = mLinearLayoutManager

        initViewListener()

        onCityData()
    }


    private fun initViewListener() {
        //左侧A,B,C定位
        qlb_letter.setOnTouchLitterChangedListener {

            if (behavior == null){
                behavior = (abl_city.layoutParams as CoordinatorLayout.LayoutParams).behavior
            }

            if (it == "热") {
                if (behavior is AppBarLayout.Behavior) {
                    val appBarBehavior = behavior as AppBarLayout.Behavior
                    appBarBehavior.topAndBottomOffset = 0
                }
                //rv移动到A
                moveToPosition(0)
                headDecoration?.setLastName("热")
            } else {

                //移动头部AppBarLayout距离
                if (behavior is AppBarLayout.Behavior) {
                    val appBarBehavior = behavior as AppBarLayout.Behavior
                    appBarBehavior.topAndBottomOffset = -cl_select_city_head.height
                }

                // 逻辑ABC...转化为对应数据分组的tag
                var toPosition = 0
                for (i in letterList.indices) {
                    if (it == letterList[i]) {
                        toPosition = i + 1
                        break
                    }
                }

                for (i in cityAdapter.data.indices) {
                    if (cityAdapter.data[i].tage == toPosition) {
                        toPosition = i
                        break
                    }
                }
                //这移动只是看到就停止了
                //rv_city.scrollToPosition(toPosition)
                moveToPosition(toPosition)
            }

        }

        //列表滚动事件，定位出position，再把position置顶
        rv_city.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (move) {
                    move = false
                    //当前已经滚完了即scrollToPosition执行完
                    val n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition()
                    if (0 <= n && n < rv_city.childCount) {
                        rv_city.scrollBy(0, rv_city.getChildAt(n).top - UtilHelper.dip2px(context, 36f))
                    }
                }

                if (qlb_letter.selectChars != "热"){
                    if (behavior == null){
                        behavior = (abl_city.layoutParams as CoordinatorLayout.LayoutParams).behavior
                    }
                    if (behavior is AppBarLayout.Behavior) {
                        val appBarBehavior = behavior as AppBarLayout.Behavior
                        if (abs(appBarBehavior.topAndBottomOffset) < cl_select_city_head.height){
                            qlb_letter.setSelectCharacter("热")
                            headDecoration?.setLastName("热")
                        }

                    }
                }

            }

        })

    }

    /**
     * @desc : 城市滚动置顶
     * @author : congge on 2019/12/4 11:45
     * n所在位置示意图对应下面三个判断
     *            n在这
     *  -----------------firstItem
     *            n在这
     *  -----------------lastItem
     *            n在这
     *
     **/
    private fun moveToPosition(n: Int) {
        mIndex = n
        val firstItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastItem = mLinearLayoutManager.findLastVisibleItemPosition()

        if (n <= firstItem) {
            //已经在列表上面（只是看不见），滚到它，它就置顶了，相当于拉下来。
            rv_city.scrollToPosition(n)
        } else if (n <= lastItem) {
            //已经处于可见列表，已经可见，可能肉眼看不见，但确实处于可见区域。这时用scrollToPosition已不起作用。用scrollBy滚到n到firstItem的top距离
            //减去36dp是减去字母item的高度
            rv_city.scrollBy(0, rv_city.getChildAt(n - firstItem).top - UtilHelper.dip2px(context, 36f))
        } else {
            //n还没出现在列表上，所以要先滚到出现，再通过scrollBy滚到顶部
            rv_city.scrollToPosition(n)
            move = true
        }
    }



    /**
     * @desc : 设置热门城市
     * @author : congge on 2019/12/16 16:03
     **/
    private fun setHotCityData(hotCityData: List<String>?) {
        if (hotCityData.isNullOrEmpty()) {
            tv_hot_city_title.visibility = View.GONE
            tfl_home_city.visibility = View.GONE
        } else {

            val tagAdapter = object : TagAdapter<String>(hotCityData) {
                override fun getView(parent: FlowLayout, position: Int, bean: String): View {
                    val tv = View.inflate(context, R.layout.active_hot_city_item, null) as TextView
                    tv.text = bean

                    return tv
                }
            }

            tfl_home_city?.adapter = tagAdapter
            tfl_home_city?.setOnTagClickListener { view, position, parent ->

                true
            }
            tv_hot_city_title.visibility = View.VISIBLE
            tfl_home_city.visibility = View.VISIBLE
        }

    }


    /**
     * @desc : 设置城市列表
     * @author : congge on 2019/12/16 15:48
     **/
     private fun onCityData() {
        val cityAllNewBean:CityAllNewBean = UtilHelper.JsonToObject(UtilHelper.getJson(context!!,"city.json"),CityAllNewBean::class.java)

        val cityAllBean: CityAllBean = cityAllNewBean.data!!

        //热门城市
        setHotCityData(cityAllBean.hot_city)

        val cityList = arrayListOf<RecBean.CityListBean>()
        var tagFirst = 1
        for (cityItem in cityAllBean.city) {
            if (cityItem.citylist.isNotEmpty()) {
                //获取字母集合，只有城市列表不为空，才添加
                letterList.add(cityItem.letter)
                for (cityName in cityItem.citylist) {
                    val cityBean = RecBean.CityListBean()
                    cityBean.name = cityName
                    //为每个城市打上tage,用于A,B,C...滑动时区分置顶
                    cityBean.tage = tagFirst
                    cityList.add(cityBean)

                }
                tagFirst++
            }
        }


        headDecoration = RecItemHeadDecoration(context, letterList)
        //必须设置列表数据与getTag对比
        headDecoration?.setCitiList(cityList)
        headDecoration?.setChangeTagNameListener {
            qlb_letter.setSelectCharacter(it)
        }
        rv_city.addItemDecoration(headDecoration!!)

        qlb_letter.setCharacters(letterList,!cityAllBean.hot_city.isNullOrEmpty())

        cityAdapter = SelectCityAdapter(R.layout.active_city_item, cityList)
        rv_city.adapter = cityAdapter
    }





}
