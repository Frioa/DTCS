package com.example.dtcs.AlarmManagerUtil;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.dtcs.Activity_Login;

import java.text.SimpleDateFormat;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by q9163 on 24/02/2018.
 */

public class AlarmManager {

    public static void setAlarmTime(Context context, long timeInMillis, String action, int time) {
        //实例化闹钟管理器
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);

        Intent intent=new Intent();
        //设置广播名字
        intent.setAction(action);
        intent.putExtra("timeInMillis",timeInMillis);
        //将来执行的操作
        Log.d("秒", "onTimeSet: "+timeInMillis);
        Log.d("秒", "onTimeSet: "+time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        //周期
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //参数2是开始时间、参数3是允许系统延迟的时间
            alarmManager.setWindow(android.app.AlarmManager.RTC, timeInMillis, time, pendingIntent);
        } else {
            alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP, timeInMillis, time, pendingIntent);
        }

    }

    // 停止轮询服务
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);

        Intent intent=new Intent();
        //设置广播名字
        intent.setAction(action);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
        alarmManager.cancel(pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  static  long setTime(Context context, int hour, int minute){
        //得到日历实例，主要是为了下面的获取时间
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        //获取当前毫秒值
        long systemTime = System.currentTimeMillis();

        //是设置日历的时间，主要是让日历的年月日和当前同步
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
        //mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //设置在几点提醒  设置的为13点
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        //设置在几分提醒  设置的为25分
        mCalendar.set(Calendar.MINUTE, minute);
        //下面这两个看字面意思也知道
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        //上面设置的就是13点25分的时间点

        //获取上面设置的13点25分的毫秒值
        long selectTime = mCalendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Log.d("系统时间", "setTime: "+dateformat.format(systemTime));
        Log.d("设置时间", "setTime: "+dateformat.format(selectTime));
        Log.d("设置时间1", "setTime: "+dateformat.format(mCalendar.getTimeInMillis()));
        return mCalendar.getTimeInMillis();
    }

    public static  void send(Context c){
        //实例化通知管理器
        NotificationManager nm= (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化通知
        NotificationCompat.Builder builder=new NotificationCompat.Builder(c);
        //给通知栏设置参数
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        //设置大图标
        Bitmap bitmp= BitmapFactory.decodeResource(c.getResources(),android.R.drawable.btn_star);
        builder.setLargeIcon(bitmp);
        //设置小图标
        builder.setSmallIcon(android.R.drawable.ic_media_pause);
        //设置标题
        builder.setContentTitle("DTCS自动提醒");
        builder.setContentText("消息内容：请您签到，谢谢合作(若已签到可无视)!");
        builder.setNumber(10);

        Intent intent=new Intent(c,Activity_Login.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(c,0x101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //设置跳转页面
        builder.setContentIntent(pendingIntent);

        //设置点击通知之后，状态栏删除通知
        builder.setAutoCancel(true);
        //展示提示文字
        builder.setTicker("主人，您有新消息哦");

        //发送通知
        nm.notify(0x201,builder.build());


    }
}
