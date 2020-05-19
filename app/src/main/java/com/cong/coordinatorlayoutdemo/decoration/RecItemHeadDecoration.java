package com.cong.coordinatorlayoutdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.cong.coordinatorlayoutdemo.R;
import com.cong.coordinatorlayoutdemo.bean.RecBean;

import java.util.List;

/**
 * @author ：congge
 * @date : 2019/11/28 11:47
 * @desc :参考文档https://www.jianshu.com/p/c0b131b679c0
 **/
public class RecItemHeadDecoration extends RecyclerView.ItemDecoration {

    private List<RecBean.CityListBean> citiList;
    private Context context;
    private int headHeight ;
    private int lineHeight;
    private Paint paint;
    private Rect rectOver;
    private List<String> index;
    private ChangeTagNameListener changeTagNameListener;
    private String lastName = "";


    public RecItemHeadDecoration(Context context, List<String> index) {
        this.context = context;
        headHeight = dip2px(36);
        lineHeight = dip2px(1);
        if(paint == null){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextSize(dip2px(15));
            rectOver = new Rect();
            this.index = index;

        }
    }

    /**
     * 设置Item的布局四周的间隙.
     *
     * @param outRect 确定间隙Left  Top Right Bottom 的数值的矩形.
     * @param view    RecyclerView的ChildView也就是每个Item的的布局.
     * @param parent  RecyclerView本身.
     * @param state   RecyclerView的各种状态.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (citiList == null || citiList.size() == 0) {
            return;
        }
        int adapterPosition = parent.getChildAdapterPosition(view);
        RecBean.CityListBean beanByPosition = getBeanByPosition(adapterPosition);
        if(beanByPosition == null){
            return;
        }
        int preTage = -1;
        int tage = beanByPosition.getTage();
        /*
        * 1.我们要在每组的第一个位置绘制我们需要的头部.
        *
        * 2.绘制头部局有两种方式:
        *   第一种方式:给Item 的头部留出空间,也就是outRect.top.该种方式对应的就是当前的Item就是分组的第一个Item.
        *   第二种方式:给Item 的底部留出空间也就是outRect.bottpm.该种方式对应的就是当前的Item是当前分组的最后一个Item.
        *
        *   这个该怎么选择呢?
        *   1.如果第一个Item需要有分组的布局,那就选择第一种方式.
        *   2.其他可以选择第二种方式.
        *
        *
        *   该方法是给Item设置间距的,有四个属性可以设置四个间距,Left  Top Right Bottom.简单来说如果Item 的高度是50dp 我们再该方法里面设置了outRect.top = 40;
        *   也就是给Item区域的顶部多出了40dp的间隙,那么实际上该Item显示出来的高度为 50 + 40 = 90dp.正好这个40dp用来绘制我们所需要的头布局.
        *
        * 3.这里拿第一种方式,那么怎么判断当前的Item是不是分组的第一个Item呢?
        *
        *   我们再Item的设置的数据里面做好分组的标记,即属于同一组的tag都一样,不同组tag都不一样.
        *   当前Item为头布局的话就要跟前一个Item 的tag比较了,因为每个分组头部的tag的值都是不一样的,如果前一个的Tag跟当前的不一样那么,当前就是下个分组的头部.
        *
        *   a  b c    d e f   g h i
        *
        *   如果 a  d  g  是分组的头部的 .a的tag = 1 , b的tag = 2, c 的tag = 3....等等 ,前一个Item 用 preTag 来表示 ,初始值为 -1.
        *
        *   假如当前的Item为a,当前tag = 1,那么它前一个Item为空,也就是发现preTag和a的tag不一样,那么a就是分组的头部.
        *   假如当前的Item为b,当前tag = 1,那么它前一个preTag 也就是a的tag = 1,发现一样那就是是同一组的.
        *   假如当前的Item为d,当前tag = 2,那么它前一个preTag 也就是c的tag = 1,发现前一个的tag跟当前的不一样,那么当前的就是新分组的第一个头部Item.
        * */
        //一定要记住这个 >= 0
        if(adapterPosition - 1 >= 0) {
            RecBean.CityListBean nextBean = getBeanByPosition(adapterPosition - 1);
            if (nextBean == null) {
                return;
            }
            preTage = nextBean.getTage();
        }
        Log.e("WANG","当前的Position is " + adapterPosition +" 当前的Tage 是  " +tage  +"   前一个 tage  是  "+ preTage);


        if(preTage != tage){
            outRect.top = headHeight;
        }else {
            outRect.top = lineHeight;
        }


    }


    /**
     * 绘制*除Item内容*以外的东西,这个方法是再****Item的内容绘制之前****执行的,
     * 所以呢如果两个绘制区域重叠的话,Item的绘制区域会覆盖掉该方法绘制的区域.
     * 一般配合getItemOffsets来绘制分割线等.
     *
     * @param c      Canvas 画布
     * @param parent RecyclerView
     * @param state  RecyclerView的状态
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * 绘制*除Item内容*以外的东西,这个方法是在****Item的内容绘制之后****才执行的,
     * 所以该方法绘制的东西会将Item的内容覆盖住,既显示在Item之上.
     * 一般配合getItemOffsets来绘制分组的头部等.
     *
     * @param c      Canvas 画布
     * @param parent RecyclerView
     * @param state  RecyclerView的状态
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(citiList == null || citiList.size() == 0){
            return;
        }

        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        int tag = -1;
        int preTag;
        /*

          当列表滑动的时候RecyclerView会不断的加载之后的Item,布局发生复用,我们要在不断的变化中去重新绘制我们的头部Item的布局.这个方法当每个Item消失或者出现的时候都会被调用,我们在这里去绘制头部的区域.
          所以在该方法里面我们会遍历所有可见的Item去重新判断分组的头布,去重新绘制.
          1.判断头布局绘制头布局.
          那么我们在这里呢还是需要判重新去判断哪个Item是分组的头部.按照getItemOffsets里面的我们需要跟之前的Item的tag作比较.但是有个问题就是我们再这里并不能拿到Item的布局或者别的东西,只能遍历所有已经显示的Item.
          这样的话我们的前一个preTag就需要我们自己去定义,然后把tag赋值给preTag,当遍历到下个Item的Tag跟之前的preTag一样的话,那就继续遍历不去绘制头布局,当遍历到Item的tag跟preTag不一样的时候就去绘制有布局.
          2.怎么让头布局悬停在顶部.
          这个问题其实拿一个例子去说明是最好的了,当我们要绘制头部的Item正好出现在屏幕的顶部的时候,我们继续滑动她的头布局就会渐渐的消失,也就是Item的getTop距离会不断的小于我们要绘制的头部的高度,当出现这种情况的时候,
          我们就让Item的getTop和头部的高度中去一个最大值.这样就好保证当getTop小于头部的高度的时候我们的头部布局一直留在顶部.
          3.下个头部来的时候怎么替换呢.
          当顶部悬浮的有一个头部的时候,我们滑动列表俩个头部肯定会和当前的头部相遇.我们再这里做的是当悬浮的头布局跟下个悬浮的头布局相遇的时候有个渐变的效果.那么我们就要来实现这个效果了.
          首先我们要判断下个头部什么时候滑动到屏幕顶部,我们这里就需要判断当前遍历到的Itme的下个Item时候有头部,还是当前的tag跟nextTag比较的结果,如果不同的话那下个Item就是有头布局的.

          那个渐变的效果需要有一个渐变值.我们想想啊,



        * 1.先做到顶部悬停.
        *
        * */

        for (int i = 0; i <childCount; i++) {
            View childView = parent.getChildAt(i);
            if(childView == null){
                continue;
            }
            int adapterPosition = parent.getChildAdapterPosition(childView);
            int top = childView.getTop();
            int bottom = childView.getBottom() ;
            preTag = tag;

            if(adapterPosition >= citiList.size()){
                break;
            }

            tag = citiList.get(adapterPosition).getTage();
            if(preTag == tag){
                continue;
            }

            String name = index.get((tag - 1 ) < 0 ? 0 : (tag -1));
            int height = Math.max(top,headHeight);

            if(adapterPosition + 1 < citiList.size()){
                int nextTag = citiList.get(adapterPosition + 1).getTage();
                if(tag != nextTag){
                    height = bottom;
                }
            }

            paint.setColor(context.getResources().getColor(R.color.c_f2efef));
            c.drawRect(parentLeft,height - headHeight,parentRight,height,paint);
            paint.setColor(context.getResources().getColor(R.color.c_757575));
            paint.getTextBounds(name, 0, name.length(), rectOver);

            c.drawText(name, dip2px(10), height - (headHeight - rectOver.height()) / 2, paint);

            if (!lastName.equals(name) && changeTagNameListener != null && top<headHeight){
                changeTagNameListener.changeName(name);
                lastName = name;

            }


        }


    }

    public interface ChangeTagNameListener{
        void changeName(String name);
    }

    public void setChangeTagNameListener(ChangeTagNameListener changeTagNameListener) {
        this.changeTagNameListener = changeTagNameListener;
    }

    private RecBean.CityListBean getBeanByPosition(int position) {
        if (position < citiList.size()) {
            RecBean.CityListBean citiListBean = citiList.get(position);
            return citiListBean;
        }
        return null;
    }


    /**
     * 列表的数据包括分组信息 ,每个组的开始会有个tage字段标记.通过set方法把数据给设置进去
     */
    public void setCitiList(List<RecBean.CityListBean> citiList) {
        this.citiList = citiList;
    }

    public int dip2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
