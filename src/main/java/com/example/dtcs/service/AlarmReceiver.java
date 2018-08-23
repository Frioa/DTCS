package com.example.dtcs.service;

/**
 * Created by q9163 on 17/02/2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.dtcs.Activity_SignActivity;
import com.example.dtcs.db.Setting;

import org.litepal.crud.DataSupport;

import static com.example.dtcs.AlarmManagerUtil.AlarmManager.send;
import static com.example.dtcs.AlarmManagerUtil.AlarmManager.setAlarmTime;
import static com.example.dtcs.AlarmManagerUtil.AlarmManager.setTime;
import static com.example.dtcs.Enum.Major.DAY_SECOND;
import static com.example.dtcs.Enum.Major.SIGNACTION;



public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(SIGNACTION.equals(intent.getAction()))
        {
            Setting set = DataSupport.findAll(Setting.class).get(0);
            send(context);
            //因为setWindow只执行一次，所以要重新定义闹钟实现循环。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setAlarmTime(context,setTime(context,set.getHour(),set.getMinute()) + DAY_SECOND, SIGNACTION, DAY_SECOND);
            }
            Toast.makeText(context, "每日签到提醒", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            //获取系统的日期
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            Log.d("每日提醒", "onReceive: "+year+"年"+month+"月"+day+"日"+hour+" "+minute+ " "+second);
        }
    }



   /* private Notification getNotification(String title,Context context){

        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        builder.setAutoCancel(true);        //自动清除

        return builder.build();
    }
*/


}