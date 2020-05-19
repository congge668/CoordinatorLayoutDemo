package com.cong.coordinatorlayoutdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.cong.coordinatorlayoutdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：congge
 * @date : 2020/5/8 11:56
 * @desc :这控件百度来的
 **/
public class QuickLocationBar extends View {
    private List<String> characters = new ArrayList<>();

    private int choose = -1;
    private Paint paint = new Paint();
    private OnTouchLetterChangedListener mOnTouchLetterChangedListener;
    private TextView mTextDialog;

    /**
     * 选择的圆的半径
     */
    private Paint circlePaint;
    private String selectChars = "热";

    public QuickLocationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public QuickLocationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickLocationBar(Context context) {
        super(context);

    }

    public void setOnTouchLitterChangedListener(
            OnTouchLetterChangedListener onTouchLetterChangedListener) {
        this.mOnTouchLetterChangedListener = onTouchLetterChangedListener;
    }

    public void setTextDialog(TextView dialog) {
        this.mTextDialog = dialog;
    }

    private void init(){
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.c_0091ff));
        circlePaint.setStyle(Paint.Style.FILL);


        // 对paint进行相关的参数设置
        paint.setColor(getResources().getColor(R.color.c_33));
//            paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (characters.size() > 0){
            int singleHeight = height / characters.size();
            for (int i = 0; i < characters.size(); i++) {

                paint.setTextSize(150*(float) width/320);
//            if (i == choose) {// choose变量表示当前显示的字符位置，若没有触摸则为-1
//                paint.setColor(getResources().getColor(R.color.bg_653fac));
//                paint.setFakeBoldText(true);
//            }
                // 计算字符的绘制的位置
                float xPos = width / 2 - paint.measureText(characters.get(i)) / 2;
                float yPos = singleHeight * i + singleHeight;
                if (selectChars.equals(characters.get(i))){
                    canvas.drawCircle(xPos+ paint.measureText(characters.get(i)) / 2, yPos-singleHeight/4,width/3,circlePaint);
                    paint.setColor(Color.WHITE);
                } else {
                    paint.setColor(getResources().getColor(R.color.c_33));
                }
                // 在画布上绘制字符
                canvas.drawText(characters.get(i), xPos, yPos, paint);
                paint.reset();// 每次绘制完成后不要忘记重制Paint
            }
        }

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int c = (int) (y / getHeight() * characters.size());

        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;//
                setBackgroundColor(0x0000);
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.GONE);
                }
                break;

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //setBackgroundColor(getResources().getColor(R.color.bg_653fac));
                if (choose != c) {
                    if (c >= 0 && c < characters.size()) {
                        if (mOnTouchLetterChangedListener != null) {
                            mOnTouchLetterChangedListener
                                    .touchLetterChanged(characters.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(characters.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getContext(),characters.get(c),Toast.LENGTH_SHORT).show();

                        choose = c;
                        selectChars = characters.get(c);
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public interface OnTouchLetterChangedListener {
        public void touchLetterChanged(String s);
    }

    /**
    * @desc : 设置字母
    * @author : congge on 2019/12/16 17:49
    **/
    public void setCharacters(ArrayList<String> characters ,Boolean hasHot){
        if (hasHot){
            this.characters.add("热");
        }
        this.characters.addAll(characters);

        invalidate();

    }

    /**
    * @desc : 设置选择的字母
    * @author : congge on 2019/12/28 14:53
    **/
    public void setSelectCharacter(String character){
        selectChars = character;
        invalidate();
    }

    public String getSelectChars() {
        return selectChars;
    }

}
