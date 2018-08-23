package com.example.dtcs;


import android.app.TimePickerDialog;
import android.content.Context;

import java.util.Calendar;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dtcs.db.Setting;
import com.example.dtcs.service.AlarmReceiver;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.example.dtcs.AlarmManagerUtil.AlarmManager.setAlarmTime;
import static com.example.dtcs.AlarmManagerUtil.AlarmManager.setTime;
import static com.example.dtcs.AlarmManagerUtil.AlarmManager.stopPollingService;
import static com.example.dtcs.Enum.Major.DAY_SECOND;
import static com.example.dtcs.Enum.Major.SIGNACTION;

/**
 * Created by q9163 on 17/02/2018.
 */

public class Tab02Setting extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private Calendar mCalendar;
    private ImageView  BackArrow;
    private LinearLayout LL_Date;
    private TextView TV_Date;
    private Switch signSwitch,imageSwitch,wifiSwitch;
    private Context context;

    private List<Setting> sets;
    private Setting set;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02settings);
        init();

    }

    private void init() {
        context = getApplicationContext();
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return04);
        signSwitch = (Switch) findViewById(R.id.signSwitch);
        imageSwitch = (Switch) findViewById(R.id.ConnetedSwitch) ;
        wifiSwitch = (Switch) findViewById(R.id.WifiSwitch) ;
        LL_Date = (LinearLayout) findViewById(R.id.LL_SettingDate);
        TV_Date = (TextView) findViewById(R.id.TV_SettingDate);
        //获取数据
        sets = DataSupport.findAll(Setting.class);
        set = sets.get(0);
        //设置按钮初始化
        if (set.getSign_switch().equals("1")){
            signSwitch.setChecked(true);
        }else {
            signSwitch.setChecked(false);
        }
        //设置初始化日期
        if (set.getMinute()<10){
            TV_Date.setText(set.getHour()+":0"+set.getMinute()); //View.ininvalidate()
        }else {
            TV_Date.setText(set.getHour()+":"+set.getMinute()); //View.ininvalidate()
        }

        //每日一图
        if (set.getImage_switch().equals("1")){
            imageSwitch.setChecked(true);
        }else {
            imageSwitch.setChecked(false);
        }
        //wifi
        if (set.getWifi_switch().equals("1")){
            wifiSwitch.setChecked(true);
        }else {
            wifiSwitch.setChecked(false);
        }

        BackArrow.setOnClickListener(this);
        LL_Date.setOnClickListener(this);
        signSwitch.setOnCheckedChangeListener(this);
        imageSwitch.setOnCheckedChangeListener(this);
        wifiSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02Return04:
                finish();
                break;
            case R.id.LL_SettingDate:
                click();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.signSwitch:
                if(b){
                    Setting set1 = new Setting();
                    set1.setHour(set.getHour());
                    set1.setMinute(set.getMinute());
                    set1.setSign_switch("1");
                    set1.setImage_switch(set.getImage_switch());
                    set1.setWifi_switch(set.getWifi_switch());
                    set1.updateAll();
                    setAlarmTime(context,setTime(context,set.getHour(),set.getMinute()) , SIGNACTION, DAY_SECOND);
                    Toast.makeText(context,"签到提醒功能已开启",Toast.LENGTH_SHORT).show();
                }else {
                    Setting set2 = new Setting();
                    set2.setHour(set.getHour());
                    set2.setMinute(set.getMinute());
                    set2.setSign_switch("0");
                    set2.setImage_switch(set.getImage_switch());
                    set2.setWifi_switch(set.getWifi_switch());
                    set2.updateAll();
                    stopPollingService(context, AlarmReceiver.class, SIGNACTION);
                    Toast.makeText(context,"签到提醒功能已关启",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ConnetedSwitch:
                if(b){
                    Setting set1 = new Setting();
                    set1.setHour(set.getHour());
                    set1.setMinute(set.getMinute());
                    set1.setSign_switch(set.getSign_switch());
                    set1.setImage_switch("1");
                    set1.setWifi_switch(set.getWifi_switch());
                    set1.updateAll();
                    setAlarmTime(context,setTime(context,set.getHour(),set.getMinute()) , SIGNACTION, DAY_SECOND);
                    Toast.makeText(context,"打开每日加载一图",Toast.LENGTH_SHORT).show();
                }else {
                    Setting set1 = new Setting();
                    set1.setHour(set.getHour());
                    set1.setMinute(set.getMinute());
                    set1.setSign_switch(set.getSign_switch());
                    set1.setImage_switch("0");
                    set1.setWifi_switch(set.getWifi_switch());
                    set1.updateAll();
                    stopPollingService(context, AlarmReceiver.class, SIGNACTION);
                    Toast.makeText(context,"关闭每日加载一图",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.WifiSwitch:
                if(b){
                    Setting set1 = new Setting();
                    set1.setHour(set.getHour());
                    set1.setMinute(set.getMinute());
                    set1.setSign_switch(set.getSign_switch());
                    set1.setImage_switch(set.getImage_switch());
                    set1.setWifi_switch("1");
                    set1.updateAll();
                    setAlarmTime(context,setTime(context,set.getHour(),set.getMinute()) , SIGNACTION, DAY_SECOND);
                    Toast.makeText(context,"Wifi下加载每日一图",Toast.LENGTH_SHORT).show();
                }else {
                    Setting set1 = new Setting();
                    set1.setHour(set.getHour());
                    set1.setMinute(set.getMinute());
                    set1.setSign_switch(set.getSign_switch());
                    set1.setImage_switch(set.getImage_switch());
                    set1.setWifi_switch("0");
                    set1.updateAll();
                    stopPollingService(context, AlarmReceiver.class, SIGNACTION);
                    Toast.makeText(context,"关闭Wifi下加载每日一图",Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    public void click(){
        //得到系统的时间
        Calendar calendar=Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);//得到的小时
        int minute=calendar.get(Calendar.MINUTE);//分钟

        //弹出时间对话框
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                //得到设置之后的时间
                stopPollingService(context, AlarmReceiver.class, SIGNACTION);
                Calendar c=Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,i);
                c.set(Calendar.MINUTE,i1);
                Log.d("小时", "onTimeSet: "+i);
                Log.d("秒", "onTimeSet: "+i1);

                Setting set1 = new Setting();
                set1.setHour(i);
                set.setHour(i);
                set1.setMinute(i1);
                set.setMinute(i1);
                set1.setSign_switch(set.getSign_switch());
                set1.setImage_switch(set.getImage_switch());
                set1.setWifi_switch(set.getWifi_switch());
                set1.updateAll();
                //子线程更新UI
                mTimeHandler.sendEmptyMessageDelayed(0, 200);
                //开启服务

                setAlarmTime(context, setTime(context,set.getHour(),set.getMinute()) , SIGNACTION, DAY_SECOND);
                //setAlarmTime(context, System.currentTimeMillis()+10000 , SIGNACTION, DAY_SECOND);
            }
        },hourOfDay,minute,true);
        timePickerDialog.show();
    }



    //更新UI线程
    Handler mTimeHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                if (set.getMinute()<10){
                    TV_Date.setText(set.getHour()+":0"+set.getMinute()); //View.ininvalidate()
                }else {
                    TV_Date.setText(set.getHour()+":"+set.getMinute()); //View.ininvalidate()
                }
                sendEmptyMessageDelayed(0, 200);
            }
        }
    };
}
