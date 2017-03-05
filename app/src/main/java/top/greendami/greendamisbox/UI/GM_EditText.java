package top.greendami.greendamisbox.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import top.greendami.greendamisbox.R;


/**
 * edit的背景
 * Created by zhaopy on 2017/3/2.
 */

public class GM_EditText extends EditText {

    float lineWidth = 5f;//线的宽度
    float signWidth = 100;//右边符号的尺寸，正方形
    int mColor = 0;

    Context mContext;

    int state = 0; //0:正常状态，1：loading状态，2：yes状态，3：no状态

    Point yes1,yes2,yes3,no1,no2,no3,no4;
    Point end,end2;//动画的结束点
    int LoadingAngle = 0;
    float LoadingLineLong = 0;
    RectF oval;

    float x = 0,y = 0;//对号第二笔动画时的终点坐标

    public int getState() {
        return state;
    }

    public GM_EditText(Context context) {
        super(context);
        mContext = context;
    }

    public GM_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mColor = mContext.getResources().getColor(R.color.login_line);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public GM_EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


        signWidth = getHeight() * 0.8f;
        Paint paint = new Paint();

        paint.setStrokeWidth(lineWidth);
        paint.setAntiAlias(true);

        //normal
        if(state == 0){
            paint.setColor(mColor);
            canvas.drawLine(0,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    getMeasuredWidth(),
                    getMeasuredHeight() - 0.5f * lineWidth,
                    paint)
            ;
        }
        //loading
        if(state == 1){
            paint.setColor(mColor);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(0,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    LoadingLineLong,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    paint);


            canvas.drawArc(oval, LoadingAngle , 120, false, paint);



            //逐渐缩小范围
            if(oval.left < getMeasuredWidth() + 2 * lineWidth - signWidth){
                oval.left = oval.left + 0.25f;
            }else{
                oval.left = getMeasuredWidth() + 2 * lineWidth - signWidth;
            }

            if(oval.top < getMeasuredHeight() + 2.5f * lineWidth - signWidth){
                oval.top = oval.top + 0.25f;
            }else{
                oval.top = getMeasuredHeight() + 2.5f * lineWidth - signWidth;
            }

            if(oval.right > getMeasuredWidth() - 3 * lineWidth){
                oval.right = oval.right - 0.25f;
            }else{
                oval.right = getMeasuredWidth() - 3 * lineWidth;
            }

            //转动的速度和底线的长度变化，写在这个判断里面了
            if(oval.bottom > getMeasuredHeight() - 3.5f * lineWidth){
                oval.bottom = oval.bottom - 0.25f;
                LoadingAngle = LoadingAngle - 3;//此处控制转动速度
                LoadingLineLong  = LoadingLineLong + 0.8f;
            }else{
                oval.bottom = getMeasuredHeight() - 3.5f * lineWidth;
                LoadingAngle = LoadingAngle - 5;//此处控制转动速度
                LoadingLineLong = getMeasuredWidth();
            }
            postInvalidate();
        }


        //yes
        if(state == 2){
            paint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
            canvas.drawLine(0,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    getMeasuredWidth() ,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    paint);
            paint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
            //对号的动画
            if(yes2.x > end.x){
                //第一笔
                float x = end.x + (yes2.x - yes1.x)/10f;
                float y = end.y + (yes2.y - yes1.y)/10f;
                if( x >= yes2.x){
                    x = yes2.x;
                    y = yes2.y;
                }
                end.set((int)x , (int)y);
                canvas.drawLine(yes1.x, yes1.y , x , y , paint);
                postInvalidate();
            }else{
                Log.e("GM_EditText", "end1:" + end);
                canvas.drawLine(yes1.x, yes1.y , yes2.x , yes2.y , paint);
                if(yes3.x > x){
                    //第二笔
                    if(x == 0 ){
                        x = end.x;
                        y = end.y;
                    }
                    x = x + (yes3.x - yes2.x)/10f;
                    y = y + (yes3.y - yes2.y)/10f;
                    canvas.drawLine(yes2.x, yes2.y , x , y , paint);
                    postInvalidate();
                }else{
                    canvas.drawLine(yes2.x, yes2.y , yes3.x , yes3.y , paint);
                }

            }

        }

        //no
        if(state == 3){
            paint.setColor(mContext.getResources().getColor(R.color.colorPrimaryRed));
            canvas.drawLine(0,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    getMeasuredWidth() ,
                    getMeasuredHeight() - 0.5f * lineWidth,
                    paint);
            paint.setColor(mContext.getResources().getColor(R.color.colorPrimaryRed));

            if(no2.x > end.x){
                float x = end.x + (no2.x - no1.x)/15f;
                float y = end.y + (no2.y - no1.y)/15f;
                end.set( (int)x , (int)y);
                canvas.drawLine(no1.x, no1.y , end.x , end.y , paint);
                postInvalidate();
            }else{
                canvas.drawLine(no1.x, no1.y , no2.x , no2.y , paint);
                //第二笔
                if(x == 0 ){
                    x = no4.x;
                    y = no4.y;
                }
                if(no3.x < x){
                    x = x - (no4.x - no3.x)/15f;
                    y = y - (no4.y - no3.y)/15f;
                    canvas.drawLine(no4.x, no4.y , x , y , paint);
                    postInvalidate();
                }else{
                    canvas.drawLine(no3.x, no3.y , no4.x , no4.y , paint);
                }
            }



        }
    }

    public void setLineColor(int color){
        state = 0;
        mColor = color;
        postInvalidate();
    }

    public void startLoading(){
        state = 1;
        signWidth = getHeight();
        oval= new RectF();
        oval.left= getMeasuredWidth() -  lineWidth - signWidth;                  //左边
        oval.top = getMeasuredHeight() - 0.5f * lineWidth - signWidth;           //上边
        oval.right= getMeasuredWidth() -  lineWidth;                             //右边
        oval.bottom = getMeasuredHeight() - 0.5f * lineWidth ;                   //下边
        LoadingAngle = 0;
        LoadingLineLong = getMeasuredWidth() - 0.5f * signWidth;
        postInvalidate();
    }

    public void startYes(){

        state = 2;
        signWidth = getHeight();
        x = 0;
        y = 0;
        yes1 = new Point((int)(getMeasuredWidth() - 0.8f * signWidth),(int)(getMeasuredHeight() - 0.6f * signWidth));
        yes2 = new Point((int)(getMeasuredWidth() - 0.65f * signWidth),(int)(getMeasuredHeight() - 0.35f * signWidth));//中间
        yes3 = new Point((int)(getMeasuredWidth() - 0.18f * signWidth),(int)(getMeasuredHeight() - 0.75f * signWidth));
        end = new Point((int)(getMeasuredWidth() - 0.8f * signWidth),(int)(getMeasuredHeight() - 0.6f * signWidth));
        postInvalidate();

    }

    public void startNo(){
        state = 3;
        signWidth = getHeight();
        no1 = new Point((int)(getMeasuredWidth() - 0.749f * signWidth),(int)(getMeasuredHeight() - 0.75f * signWidth));//左上
        no2 = new Point((int)(getMeasuredWidth() - 0.25f * signWidth),(int)(getMeasuredHeight() - 0.25f * signWidth));//右下
        no3 = new Point((int)(getMeasuredWidth() - 0.75f * signWidth),(int)(getMeasuredHeight() - 0.25f * signWidth));//左下
        no4 = new Point((int)(getMeasuredWidth() - 0.251f * signWidth),(int)(getMeasuredHeight() - 0.75f * signWidth));//右上

        end = new Point((int)(getMeasuredWidth() - 0.749f * signWidth),(int)(getMeasuredHeight() - 0.75f * signWidth));
        end2 =new Point((int)(getMeasuredWidth() - 0.251f * signWidth),(int)(getMeasuredHeight() - 0.75f * signWidth));

        postInvalidate();
    }
}
