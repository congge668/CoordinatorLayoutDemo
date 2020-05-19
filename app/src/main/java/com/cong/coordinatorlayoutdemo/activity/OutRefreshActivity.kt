package com.cong.coordinatorlayoutdemo.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.ccr.ccrecyclerviewlibrary.view.refreshlayout.RefreshLayout
import com.ccr.ccrecyclerviewlibrary.view.refreshlayout.RefreshLayoutDirection
import com.cong.coordinatorlayoutdemo.R
import com.cong.coordinatorlayoutdemo.adapter.BaseFragmentAdapter
import com.cong.coordinatorlayoutdemo.adapter.OutRefreshHeadAdapter
import com.cong.coordinatorlayoutdemo.fragment.AFragment
import com.cong.coordinatorlayoutdemo.fragment.BFragment
import com.cong.coordinatorlayoutdemo.fragment.CFragment
import kotlinx.android.synthetic.main.activity_out_refresh.*
import java.util.*

class OutRefreshActivity : AppCompatActivity() {

    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_refresh)
        context = this

        setRefreshLayout(mRefreshLayout)
        common_recycler.layoutManager = LinearLayoutManager(context)
        val headView = LayoutInflater.from(context).inflate(R.layout.layout_out_refresh_head, common_recycler, false)
        val headAdapter = OutRefreshHeadAdapter()
        common_recycler?.adapter = headAdapter
        headAdapter.addHeaderView(headView)
        //关键,什么时候RefreshLayout可用
        app_bar_layout.addOnOffsetChangedListener(object :AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {
                mRefreshLayout.isEnabled = verticalOffset >=0
                //只能下拉
                mRefreshLayout?.direction = RefreshLayoutDirection.TOP
            }

        })

        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(AFragment())
        fragments.add(BFragment())
        fragments.add(CFragment())
        val titles: MutableList<String> = ArrayList()
        titles.add("推荐")
        titles.add("小岛")
        titles.add("专题")
        val baseFragmentAdapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        vp_pager_ceo_data.setAdapter(baseFragmentAdapter)
        stl_ceo_data.setViewPager(vp_pager_ceo_data)

    }

    protected fun setRefreshLayout(refreshLayout: RefreshLayout) {

        refreshLayout.direction = RefreshLayoutDirection.BOTH
        refreshLayout.setColorSchemeResources(R.color.m_red_two, R.color.m_charcoal_grey, R.color.m_purple, R.color.m_green, R.color.m_blue)
        refreshLayout.setOnRefreshListener(object : RefreshLayout.OnRefreshListener {
            override fun onPullDownToRefresh() {
                //刷新
                Handler().postDelayed(object :Runnable{
                    override fun run() {
                        refreshLayout.isRefreshing = false
                    }

                },1000)
            }

            override fun onPullUpToRefresh() {
                //加载更多
            }
        })
    }
}