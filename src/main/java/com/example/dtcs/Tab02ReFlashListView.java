package com.example.dtcs;

import android.content.Context;
import java.text.SimpleDateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.Date;


/**
 * Created by q9163 on 26/12/2017.
 */

public class Tab02ReFlashListView extends ListView implements AbsListView.OnScrollListener{
    View Header;//顶部布局文件
    int headerHeight;//顶部布局文件的高度
    int firstVisibleItem;
    int scrollState;

    boolean isReamark;//biao ji
    int startY;//按下时候的Y值
    int state;
    final int NODE = 0;//正常状态
    final int PULL = 1;//下拉刷新状态
    final int PELESE = 2;//提示释放状态
    final int REFLASHING = 3;//刷新状态
    IReflashListener iReflashListener;//刷新数据的接口
    static Date lastDate;
    public Tab02ReFlashListView(Context context) {
        super(context);
        initView(context);
    }

    public Tab02ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Tab02ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    //初始化界面，添加顶部布局文件listView
    private void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        Header = inflater.inflate(R.layout.tab02header_layout,null);
        measureView(Header);
        headerHeight = Header.getMeasuredHeight();
        Log.i("顶部布局文件的高度", "headerHeight= "+headerHeight);
        topPadding(-headerHeight);
        this.addHeaderView(Header);
        this.setOnScrollListener(this);
    }

    //通知父布局，占用宽高
    private void measureView(View view){
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p==null){
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight>0){
            height = MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else{
            height = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }
    private void topPadding(int top){
        Header.setPadding(Header.getPaddingLeft(),top,Header.getPaddingRight(),Header.getPaddingBottom());
        Header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        scrollState = i;
    }


    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.firstVisibleItem = i;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem==0){
                    isReamark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (PELESE == state){
                    state = REFLASHING;
                    //加载最新数据
                    reflashViewByState();
                   iReflashListener.onReflash();

                }else if(state == PULL){
                    state = NODE;
                    isReamark = false;
                    reflashViewByState();
                    refilashComplete();
                }
                break;

        }
        return super.onTouchEvent(ev);
    }
    //判断移动过程中的操作
    private void onMove(MotionEvent ev){
        if (!isReamark){
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int toPadding = space - headerHeight;
        switch (state){
            case NODE:
                if (space>0){
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(toPadding);
                if (space>headerHeight+30 && scrollState == OVER_SCROLL_IF_CONTENT_SCROLLS){
                    state = PELESE;
                    reflashViewByState();
                }
                break;
            case PELESE:
                topPadding(toPadding);
                if (space < headerHeight+30 ){
                    state = PULL;
                    reflashViewByState();
                }if (state<=0){
                state = NODE;
                isReamark = false;
                reflashViewByState();
            }
                break;
        }
    }
    //根据当前状态，改变界面显示
    private void reflashViewByState(){
        TextView tip = (TextView) Header.findViewById(R.id.TV_tip);
        ImageView arrow = (ImageView) Header.findViewById(R.id.TV_tab02RefreshArrow);
        ProgressBar progressBar = (ProgressBar) Header.findViewById(R.id.PB_RefreshProgressBar);
        RotateAnimation animation = new RotateAnimation(0,180,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);

        RotateAnimation animation1 = new RotateAnimation(180,0,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation1.setDuration(500);
        animation1.setFillAfter(true);
        switch (state){
            case NODE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tip.setText("下拉可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(animation1);
                break;
            case PELESE:
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tip.setText("松开可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(animation);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                arrow.clearAnimation();
                break;
        }
    }

    //获取完数据：修改时间
    public void refilashComplete(){
        state = NODE;
        isReamark = false;
        reflashViewByState();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        Log.d("今天的日期Date", "refilashComplete1: "+format.format(date));
        if (lastDate==null){
            lastDate = date;
            Log.d("今天的日期lastDate", "refilashComplete: "+format.format(lastDate));
        }else{
            long Second = ((date.getTime() - lastDate.getTime())/1000)%60;
            long Minute = ((date.getTime() - lastDate.getTime())/1000)/60;
            String time = "上次刷新时间"+Minute+"分"+Second+"秒";
            Log.d("lastData日期", "refilashComplete1: "+format.format(lastDate));
            Log.d("time日期", "refilashComplete1: "+time);
            lastDate = date;
            TextView lastupTime = (TextView) Header.findViewById(R.id.TV_lastupdata_time);
            lastupTime.setText(time);
        }


    }
    public void setInterface(IReflashListener iReflashListener){
        this.iReflashListener = iReflashListener;
    }
    //刷新数据接口
    public interface IReflashListener{
        void onReflash();
    }
}
