package com.jikexueyuan.multouch;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 多点触摸应用实例
 */
public class MainActivity extends AppCompatActivity {
    //帧布局对象
    private FrameLayout root;
    //ImageView对象
    private ImageView iv;

    //当前距离
    private float currentDistance;
    //最后一次的距离
    private float lastDistance = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Framelayout实例
        root = (FrameLayout) findViewById(R.id.container);
        //ImageView实例
        iv = (ImageView) findViewById(R.id.iv);
        //FrameLayout触摸监听
        rootTouchListener();
    }

    /**
     * FrameLayout触摸监听
     */
    private void rootTouchListener() {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /**
                 * 三种触摸事件,down,move,up, 三种动作是连贯性的，需要return true生效
                 */
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //缩放图片
                        zoomImg(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        float lastDistance = -1;
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 缩放图片
     *
     * @param event
     */
    private void zoomImg(MotionEvent event) {
        //触摸点个数必须大于1
        if (event.getPointerCount() > 1) {
            float offsetX = event.getX(0) - event.getX(1);
            float offsetY = event.getY(0) - event.getY(1);
            //两个触摸点之间的距离(勾股定理)
            currentDistance = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);

            if (lastDistance < 0) {
                lastDistance = currentDistance;
            } else {
                //大于5像素,容错，设为0图片会一直跳动
                if (currentDistance - lastDistance > 5) {
                    System.out.println("放大");
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv.getLayoutParams();
                    lp.width = (int) (1.1f * iv.getWidth());
                    lp.height = (int) (1.1f * iv.getHeight());
                    iv.setLayoutParams(lp);
                    lastDistance = currentDistance;
                } else if (lastDistance - currentDistance > 5 ) {
                    System.out.println("缩小");
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv.getLayoutParams();
                    lp.width = (int) (0.9f * iv.getWidth());
                    lp.height = (int) (0.9 * iv.getHeight());

                    System.out.println("lp.width:"+lp.width);
                    System.out.println("iv.geWidth:" + iv.getWidth());

                    if (lp.width > 720){
                        iv.setLayoutParams(lp);
                    }
                    lastDistance = currentDistance;
                }

            }
        }

    }

    /**
     * 获得当前单个触摸点坐标
     *
     * @param event
     */
    private void getCurrentPointer(MotionEvent event) {
        System.out.println(String.format("x:%f,y:%f", event.getX(), event.getY()));
    }

    /**
     * 获得当前多个触摸点坐标
     */
    private void getPointerNum(MotionEvent event) {
        //触摸点数量
//        System.out.println("pointer count:" + event.getPointerCount());
        //多点触摸坐标
        System.out.println(String.format("X1:%f,Y1:%f,X2:%f,Y2:%f",
                event.getX(0), event.getY(0), //第一个触摸点坐标
                event.getX(1), event.getY(1)));//第二个触摸点坐标
    }

    //拖动图片
    private void moveImg(MotionEvent event) {
        //动态生成FrameLayout布局实例
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv.getLayoutParams();

        /**
         * 模拟拖动效果
         * FrameLayout 设置属性
         * 把xy坐标赋值给ImageView的左边和头部外框边距 ，模拟拖动效果
         */

        lp.leftMargin = (int) event.getX();
        lp.topMargin = (int) event.getY();

        //设置ImageView属性
        iv.setLayoutParams(lp);
    }


}
