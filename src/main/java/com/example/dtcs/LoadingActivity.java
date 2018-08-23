package com.example.dtcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dtcs.db.ExperimentUser;
import com.example.dtcs.db.Setting;
import com.example.dtcs.util.HttpUtil;
import com.example.dtcs.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.dtcs.AlarmManagerUtil.isConnected.isWifiConnected;

/**
 * Created by q9163 on 24/02/2018.
 */

public class LoadingActivity extends AppCompatActivity {
    private ImageView imageView;
    private LinearLayout LL_background;
    //SharedPreferences保存密码
    private SharedPreferences prf;
    private SharedPreferences.Editor editor;
    private String note = "";
    private String content = "";
    private TextView TV_DaySpeed,TV_EDaySpeed,TV_Day,TV_Year;
    Map<Integer,String> Month = new HashMap<Integer,String>() ;
    private boolean start = true;

    // 设置信息
    private List<Setting> sets;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.loading);
        intit();


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome() {
        Intent intent = new Intent(LoadingActivity.this, Activity_Login.class);
        startActivity(intent);
        finish();
    }

    private void intit() {
        InitMap();
        imageView = (ImageView) findViewById(R.id.Loading);
        LL_background = (LinearLayout) findViewById(R.id.LL_LoadingBackground);
        TV_DaySpeed = (TextView) findViewById(R.id.TV_DaySpeed);
        TV_EDaySpeed = (TextView) findViewById(R.id.TV_EDaySpeed);
        TV_Day = (TextView) findViewById(R.id.TV_LoadingDay);
        TV_Year = (TextView) findViewById(R.id.TV_LoadingYear);
        prf = PreferenceManager.getDefaultSharedPreferences(this);
        String Firsts = prf.getString("First", "");

        Intent intent = getIntent();

        sets = DataSupport.findAll(Setting.class);
        //设置初始化
        if (sets.size()==0){
            Log.d("设置信息为空", "Init_View: ");
            Setting set = new Setting();
            set.setHour(20);
            set.setMinute(0);
            set.setSign_switch("1");
            set.setImage_switch("1");
            set.setWifi_switch("1");
            set.saveThrows();
        }else {
            Log.d("设置信息不为空", "Init_View: "+sets.toString());
        }

        if (intent.getStringExtra("start")==null){
            start = true;
        }else {
            start = false;
        }
        Log.d("First", "intit: " + Firsts);


        if (Firsts.equals("0")) {
            Setting set = DataSupport.findAll(Setting.class).get(0);
            if (set.getImage_switch().equals("1") ){
                Log.d("加载每日一图", "intit: " );
                if (set.getWifi_switch().equals("1") ){
                    Log.d("WIFI加载每日一图", "intit: " );
                    if (isWifiConnected(LoadingActivity.this)){
                        Log.d("当前WIFI环境", "intit: " );
                        loadBingPic();
                    }else {
                        Log.d("当前非WIFI环境", "intit: " );
                        Toast.makeText(LoadingActivity.this,"非WIFI条件下无法加载每日一图",Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessageDelayed(0, 3000);
                    }
                }else {
                    Log.d("4G加载每日一图", "intit: " );
                    loadBingPic();
                }
            }else {
                Log.d("不加载每日一图", "intit: " );
                handler.sendEmptyMessageDelayed(0, 3000);
            }

        } else {
            save();
            Log.d("首次打开APP", "intit: ");
            Log.d("不加载每日一图", "intit: " );
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    private void save() {
        //保存信息
        editor = prf.edit();
        editor.putString("First", "0");
        editor.apply();
    }

    private void loadBingPic(){
        String requsetBingPic = "http://guolin.tech/api/bing_pic";
        JinShanCiBa();
        HttpUtil.sendOKHttpRequest(requsetBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      Toast.makeText(LoadingActivity.this,"网络链接错误",Toast.LENGTH_SHORT).show();
                                      handler.sendEmptyMessageDelayed(0, 2000);
                                  }
                              });
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoadingActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //放大
                        Animation animation;
                        animation= AnimationUtils.loadAnimation(LoadingActivity.this,R.anim.anim_small);
                        Glide.with(LoadingActivity.this).load(bingPic).into(imageView);
                        imageView.startAnimation(animation);
                        LL_background.setBackgroundColor(LoadingActivity.this.getResources().getColor(R.color.halfalpha));
                        TV_DaySpeed.setText("    "+note);
                        TV_EDaySpeed.setText("  "+content);
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        if (day<10){
                            TV_Day.setText("0"+day+"");
                        }else {
                            TV_Day.setText(day+"");
                        }


                        //设置年月
                        for (Map.Entry<Integer, String> m :Month.entrySet())  {
                            if (m.getKey().equals(month)){
                                TV_Year.setText(m.getValue()+"."+year);
                                break;
                            }
                        }
                        if (start){
                            handler.sendEmptyMessageDelayed(0, 4500);
                        }

                    }
                });
            }
        });
    }

    private void JinShanCiBa(){
        String requsetBingPic = "http://open.iciba.com/dsapi";

        HttpUtil.sendOKHttpRequest(requsetBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String JinShanCiBa = response.body().string();
                try {
                    JSONObject userJson = new JSONObject(JinShanCiBa);
                    note = userJson.getString("note").toString();
                    content = userJson.getString("content").toString();
                    Log.d("每日一句话", "onResponse: "+note);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }

    void InitMap(){
        Month.put(1,"Jan");
        Month.put(2,"Feb");
        Month.put(3,"Mar");
        Month.put(4,"Apr");
        Month.put(5,"May");
        Month.put(6,"June");
        Month.put(7,"July");
        Month.put(8,"Aug");
        Month.put(9,"Sept");
        Month.put(10,"Oct");
        Month.put(11,"Nov");
        Month.put(12,"Dec");
    }

}
