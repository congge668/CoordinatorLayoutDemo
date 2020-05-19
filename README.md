# CoordinatorLayoutDemo
Android案例分享系列--悬停列表

关键：协调布局CoordinatorLayout
方法：app:layout_behavior，指定子视图相对于其他视图的行为，当对方的位置发生变化时，本视图的位置也要随之变化

应用栏布局AppBarLayout，继承LinearLayout，可将AppBarLayout称作为头部
功能：1、支持响应页面主体的滑动行为，即页面在上移或者下移时，AppBarLayout能够捕捉到页面主体的滚动操作
2、捕捉到滚动操作之后，通知下级节点你要怎么滚
如app:layout_behavior="@string/appbar_scrolling_view_behavior" 表示通知AppBarLayout捕捉RecycleView的滚动操作

应用场景1：
<android.support.design.widget.CoordinatorLayout

	<android.support.design.widget.AppBarLayout
                             
		<android.support.design.widget.CollapsingToolbarLayout
			app:layout_scrollFlags="scroll|exitUntilCollapsed">
			<!--这里加头部内容，会随列表划走的部分-->
		</android.support.design.widget.CollapsingToolbarLayout>
		<TabLayout>
			<!--悬浮置顶的，不加layout_scrollFlags就不会滚走-->
		</TabLayout>
	</android.support.design.widget.AppBarLayout>
	<RecycleView
		app:layout_behavior="@string/appbar_scrolling_view_behavior">
	</RecycleView>
</android.support.design.widget.CoordinatorLayout>

应用场景2：
1、RecyclerView添头部有问题或者添头部不满足需要，就可以考虑用  CoordinatorLayout ， AppBarLayout 和 CollapsingToolbarLayout组合解决问题
示例代码：
<android.support.design.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <android.support.design.widget.AppBarLayout
                android:id="@+id/abl_city"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <android.support.design.widget.CollapsingToolbarLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:layout_scrollFlags="scroll|exitUntilCollapsed">

                    <include layout="@layout/active_select_city_header"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        app:layout_collapseMode="pin"
                        />

                </android.support.design.widget.CollapsingToolbarLayout>


            </android.support.design.widget.AppBarLayout>

            <android.support.v7.widget.RecyclerView
                android:id="@+id/rv_city"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/white"
                app:layout_behavior="@string/appbar_scrolling_view_behavior"/>
</android.support.design.widget.CoordinatorLayout>
