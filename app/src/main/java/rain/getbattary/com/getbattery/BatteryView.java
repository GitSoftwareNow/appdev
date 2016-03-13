/**
 * Created by acer on 2016/3/5.
 * E-mail:hanguangyu1268@sohu.com
 */
package rain.getbattary.com.getbattery;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class BatteryView extends View{
    public float currentX=80;
    public float currentY=80;
    public float secondY=80;

    private Paint mPaint=new Paint();
    private Context mContext;
    private Handler mHandler;
    private Bitmap mBitmap;
    private int speedTime=20;

    private float width = 200;
    private float height = 50;
    private float percentage = 0.5f;

    //battary类构造函数
    public BatteryView(Context context){
        super(context);
        this.mContext=context;

    }

    public BatteryView(Context context,AttributeSet set){
        super(context,set);
        this.mContext=context;
        init();
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        width = this.getWidth();
        height = this.getHeight();
        mPaint.setColor(Color.BLUE);
        Resources res = mContext.getResources();
        BitmapDrawable bmpDraw = (BitmapDrawable) res.getDrawable(R.drawable.batteryprocess);
        mBitmap = bmpDraw.getBitmap();
        canvas.clipRect(0, 0, width*percentage, height);
        canvas.drawBitmap(mBitmap, 0, currentY, mPaint);
        canvas.drawBitmap(mBitmap, 0, secondY, mPaint);

    }

    private void init() {
        percentage = 0;
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        currentX ++;
                        currentY ++;
                        if (mBitmap != null && currentY > mBitmap.getHeight()){
                            currentY = -mBitmap.getHeight();
                        }
                        if (mBitmap != null){
                            secondY = currentY+mBitmap.getHeight();
                            if (secondY >= mBitmap.getHeight()){
                                secondY = currentY-mBitmap.getHeight();
                            }
                        }

                        percentage = percentage + 0.003f;
                        if (percentage > 1){
                            percentage = 0;
                        }
                        // 每次计算后都发送消息进入下一次循环，并刷新界面
                        mHandler.sendEmptyMessageDelayed(1, speedTime);
                        postInvalidate();
                        break;
                }
                super.handleMessage(msg);
                postInvalidate();
            }
        };

        // 首次循环刷新界面
        mHandler.sendEmptyMessageDelayed(1, speedTime);
    }

}