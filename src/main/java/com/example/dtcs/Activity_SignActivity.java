package com.example.dtcs;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dtcs.MypagerAdapter.MypagerAdapter;
import com.example.dtcs.db.Competition;
import com.example.dtcs.db.File;
import com.example.dtcs.db.GsonUser;
import com.example.dtcs.db.Lesson;
import com.example.dtcs.db.Setting;
import com.example.dtcs.db.SignLogin;
import com.example.dtcs.db.User;
import com.example.dtcs.util.HttpUtil;
import com.example.dtcs.util.Utility;
import com.xys.libzxing.zxing.encoding.EncodingUtils;


import org.litepal.crud.DataSupport;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.dtcs.AlarmManagerUtil.AlarmManager.setAlarmTime;
import static com.example.dtcs.AlarmManagerUtil.AlarmManager.setTime;
import static com.example.dtcs.Enum.Major.DAY_SECOND;
import static com.example.dtcs.Enum.Major.DTCSUrl;
import static com.example.dtcs.Enum.Major.SIGNACTION;



/**
 * Created by q9163 on 2017/8/14.
 */


public class Activity_SignActivity extends AppCompatActivity implements  View.OnClickListener {


    private List<View> views = new ArrayList<View>();
    private RadioButton RB_home, RB_shop, RB_me;
    private LinearLayout LL_CheckCanlender;
    private ViewPager viewpager;
    private View tab01, tab02, tab03;

    //Tab01
    private Button Bt_Sgin;
    private String StrBirthdayName="";
    private List<SignLogin> signLoginLists;
    private TextView TV01_Birthday,TV01_Sign;
    private String SignDate;

    //Tab02
    private GridView GV_Info, GV_Other;
    private List<Map<String, Object>> list_manage, list_other;
    String[] S_Manage = new String[]{"实验员管理系统", "管理员管理系统", "研究员管理系统", "设备管理", "文档管理",
            "比赛管理", "权限管理"};
    String[] S_Other = new String[]{"共享文件", "每日一图", "课程", "计划任务", "全局设置","dtcs网页版"};

    int[] Images_Manage = new int[]{R.mipmap.function_r, R.mipmap.function_aa, R.mipmap.function_s, R.mipmap.function_setting,
            R.mipmap.function_doument, R.mipmap.function_game, R.mipmap.function_jurisdiction};
    int[] Images_Other = new int[]{R.mipmap.function_share, R.mipmap.function_message, R.mipmap.function_course, R.mipmap.function_plan,
            R.mipmap.function_setting1,R.mipmap.dtcsicon};

    //Tab03
    private TextView Tv03_userName,TV03_Number,TV03_PRank,TV03_PRemark;
    private List<User> userList;

    private LinearLayout LLexit,LLreFresh,LLPeopleInformation,LLInformetion,LLGrade,LLRegardto,LLSettiing;
    private String html;
    private ImageView IV_headPortrait,IV_QRcode;
    User user;//用户

    //加载（请求网络）
    private ProgressDialog progressDialog;
    private SharedPreferences prf;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init_View();
        ininData();
        Grid_View();

        //TAB 01按钮
        initViewTab01();
        init01Data();

        //Tab 03
        Tab03Data();
        initViewTab03();//初始化控件

    }
    //初始化
    private void Init_View() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        RadioGroup RB_G = (RadioGroup) findViewById(R.id.radiogroup);
        RB_home = (RadioButton) findViewById(R.id.RB_home);
        RB_shop = (RadioButton) findViewById(R.id.RB_shop);
        RB_me = (RadioButton) findViewById(R.id.RB_me);
        //传递参数(初始化用户信息)
        Intent intent = getIntent();
        String userDate = intent.getStringExtra("userDate");
        //从数据库中得到资料
        userList = DataSupport.where("user_id=?", userDate).find(User.class);
        user = userList.get(0);
        Log.d("用户资料userDate", "Tab03Date: "+user.toString());

        RB_G.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                changeTab(i);
            }
        });

        viewpager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void ininData() {
        LayoutInflater mInflater = LayoutInflater.from(this);

        tab01 = mInflater.inflate(R.layout.tab01sign, null);
        tab02 = mInflater.inflate(R.layout.tab02function, null);
        tab03 = mInflater.inflate(R.layout.tab03personage, null);


        views.add(tab01);
        views.add(tab02);
        views.add(tab03);

        MypagerAdapter adapter = new MypagerAdapter(views);
        viewpager.setAdapter(adapter);


    }

    //Tab01----------------------------------------------------------------------------------
    private void initViewTab01(){
        LL_CheckCanlender = (LinearLayout) tab01.findViewById(R.id.LL_CheckCanlender);

        TV01_Birthday = (TextView) tab01.findViewById(R.id.TV_Tab01Birthday);
        TV01_Sign = (TextView) tab01.findViewById(R.id.TV_sign);
        Bt_Sgin = (Button) tab01.findViewById(R.id.Bt_Sign);
        Bt_Sgin.setOnClickListener(this);
        LL_CheckCanlender.setOnClickListener(this);
    }

    private void init01Data(){
        loadBirthday(); // 获取生日信息并设置TextView
        Log.d("过生日的人", "init01Data: "+StrBirthdayName);
        Log.d("签到日历", "init01Data: "+StrBirthdayName);
        CheckCanlender();//显示签到天数
    }

    /**
     *TAB01方法
     */
    //下载生日信息
    private void loadBirthday(){
        String Url = DTCSUrl + "Home/Index/birthInterface.html";
        queryFromServer(Url,"loadBirthday");
    }
    //签到功能
    private void UserSign(){
        String SignURL = DTCSUrl+"Home/Index/acceptPost";
        new MyAsync_Tack().execute(SignURL,user.getUser_id());
    }

    //签到日历
    private void CheckCanlender(){
        signLoginLists= DataSupport.where("user_id=?",user.getUser_id()).find(SignLogin.class);
        SignDate = signLoginLists.size()+"";
        //Log.d(user.getUser_id()+"一共签到", "CheckCanlender: "+ signLoginLists.toString());
        //子线程更新UI
        mTimeHandler.sendEmptyMessageDelayed(0, 200);
    }
    //更新UI线程
    Handler mTimeHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                TV01_Sign.setText(SignDate); //View.ininvalidate()
                sendEmptyMessageDelayed(0, 200);
            }
        }
    };


    //Tab02 图标------------------------------------------------------------------------------
    private void Grid_View() {

        GV_Info = (GridView) tab02.findViewById(R.id.GV_Manage);
        GV_Other = (GridView) tab02.findViewById(R.id.GV_Other);

        //创建一个List集合
        list_manage = new ArrayList<Map<String, Object>>();
        SimpleAdapter adapter1 = new SimpleAdapter(this,
                Get_Data(),
                R.layout.gridview_item,
                new String[]{"title", "image"},
                new int[]{R.id.TV_girdview, R.id.IV_gridview});
        GV_Info.setAdapter(adapter1);

        list_other = new ArrayList<Map<String, Object>>();
        SimpleAdapter adapter2 = new SimpleAdapter(this,
                Get_Data2(),
                R.layout.gridview_item,
                new String[]{"title", "image"},
                new int[]{R.id.TV_girdview, R.id.IV_gridview});
        GV_Other.setAdapter(adapter2);
        OnItemClick();
    }

    private List<Map<String, Object>> Get_Data2() {
        for (int i = 0; i < Images_Other.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", Images_Other[i]);
            map.put("title", S_Other[i]);
            list_other.add(map);
        }
        return list_other;
    }

    private List<Map<String, Object>> Get_Data() {
        for (int i = 0; i < Images_Manage.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", Images_Manage[i]);
            map.put("title", S_Manage[i]);
            list_manage.add(map);
        }
        return list_manage;
    }

    //Tab03数据----------------------------------------------------------------------------
    private void  Tab03Data() {
        //控件初始化
        //用户名字编号
        Tv03_userName = (TextView) tab03.findViewById(R.id.TV_user);
        TV03_Number = (TextView) tab03.findViewById(R.id.TVNumber);
        Tv03_userName.setText(user.getName());
        TV03_Number.setText(user.getUser_id());

        //图片
        IV_headPortrait = (ImageView) tab03.findViewById(R.id.IV_head);     //头像
        IV_QRcode = (ImageView) tab03.findViewById(R.id.IV_code);       //二维码
        make();     //生成二维码
        prf = PreferenceManager.getDefaultSharedPreferences(this);

        //自动保存图片
        if (prf.getString("headPortrait"+user.getUser_id(),null) != null){
            Glide.with(this).load(prf.getString("headPortrait"+user.getUser_id(),null)).into(IV_headPortrait);    //头像
            Log.d("显示头像", "本地头像 "+user.getUser_id());
        }else{
            String userId = user.getUser_id();
            loadHeadPortrai(userId);//下载图片
            Log.d("显示头像", "网络下载头像 ");
        }
    }
    //tab03初始化控件
    private void initViewTab03(){
        LLPeopleInformation = (LinearLayout) tab03.findViewById(R.id.LL_PeopleInformation);//个人中心
        LLInformetion = (LinearLayout) tab03.findViewById(R.id.LL_Information); //信息
        LLGrade = (LinearLayout) tab03.findViewById(R.id.LL_Grade);         //成绩
        LLRegardto = (LinearLayout) tab03.findViewById(R.id.LL_Regardto);       //关于
        LLSettiing = (LinearLayout) tab03.findViewById(R.id.LL_Setting);//设置
        LLreFresh = (LinearLayout) tab03.findViewById(R.id.LL_reFresh);//刷新
        LLexit = (LinearLayout) tab03.findViewById(R.id.LL_exit);//退出按钮

        TV03_PRank = (TextView) tab03.findViewById(R.id.TV_Prank);
        TV03_PRemark = (TextView) tab03.findViewById(R.id.TV_PRemark);

        LLPeopleInformation.setOnClickListener(this);
        LLInformetion.setOnClickListener(this);
        LLGrade.setOnClickListener(this);
        LLRegardto.setOnClickListener(this);
        LLSettiing.setOnClickListener(this);
        LLreFresh.setOnClickListener(this);
        LLexit.setOnClickListener(this);

        if (user.getRank()==0){
            TV03_PRank.setText("  研究员  ");
        }
        if (user.getRank()==1){
            TV03_PRank.setText("  管理员  ");
        }
        Log.d("(user.getRemark()", "initViewTab03: "+user.getRemark());

        //设置状态
        if (user.getRemark().equals("null") || user.getRemark().equals("")){
            //TV03_PRemark.setBackgroundColor(0xff000000);
            TV03_PRemark.setBackground(getResources().getDrawable(R.drawable.button_dayborde));
        }else{
            TV03_PRemark.setText("  "+user.getRemark()+"  ");
            TV03_PRemark.setBackground(getResources().getDrawable(R.drawable.textview_tab03borde));

        }//R.drawable.textview_tab03borde
    }
    //GridView不可滑动
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true; // 禁止GridView滑动
        }
        return super.dispatchTouchEvent(ev);
    }


    //按钮
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //TAB01
            case R.id.Bt_Sign:  //点击签到
                //签到功能
                UserSign();
                break;
            case R.id.LL_CheckCanlender:
                Intent intentCheckCanlender =new Intent(Activity_SignActivity.this,Tab01CheckCanlender.class);
                intentCheckCanlender.putExtra("userID", user.getUser_id());
                intentCheckCanlender.putExtra("SignDate", SignDate);
                startActivity(intentCheckCanlender);  //启动

                break;
            //TAB02

            //TAB03

            case R.id.LL_Information://个人信息页面
                Intent intentInformation =new Intent(Activity_SignActivity.this,Tab03PeoPleInformation.class);
                intentInformation.putExtra("userID", user.getUser_id());

                startActivity(intentInformation);  //启动
                Log.d("onInformation", "information");
                break;
            case R.id.LL_exit://退出
                //Toast.makeText(Activity_SignActivity.this,"点击退出",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Activity_SignActivity.this,Activity_Login.class);
                intent.putExtra("onClickExit", "exit");
                startActivity(intent);  //启动
                Log.d("onClickExit", "exit");
                finish();
                break;
            case R.id.LL_reFresh://刷新
                showProgressDialog();
                String refreshUrl = DTCSUrl+"settings/index/verifyLabInterface/id/"+user.getUser_id()+".html";
                queryFromServer(refreshUrl,"linkServer");//更新头像
                updataUser();//更新用户资料
                Log.d("刷新","onClick()");
                loadHeadPortrai(user.getUser_id());
                break;
            case R.id.LL_PeopleInformation:

                Intent intentWeb =new Intent(Activity_SignActivity.this,Tab03DTCSWebView.class);
                intentWeb.putExtra("WebuserID",user.getUser_id());
                intentWeb.putExtra("Webpassword",user.getPassword());
                Log.d("个人信息", "onClick: "+user.getUser_id()+user.getPassword());
                startActivity(intentWeb);  //启动
                break;
            case R.id.LL_Setting:
                Intent intent1 =new Intent(Activity_SignActivity.this,Tab02Setting.class);
                startActivity(intent1);  //启动
                break;
        }


    }

    //点击GridView
    public void OnItemClick(){
        GV_Info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("GV_Info点击事件", "onItemClick: "+"GV_Info"+position+"被点击");
                switch (position){
                    case 0:
                        // 页面跳转
                        Intent intent0 =new Intent(Activity_SignActivity.this,Tab02GridViewInfo0.class);
                        startActivity(intent0);  //启动
                        break;
                    case 1:
                        // 页面跳转
                        Intent intent1 =new Intent(Activity_SignActivity.this,Tab02GridViewInfo1.class);
                        startActivity(intent1);  //启动
                        break;
                    case 2:
                        Intent intent2 =new Intent(Activity_SignActivity.this,Tab02GridViewInfo2.class);
                        startActivity(intent2);  //启动
                        break;
                    case 3://设备管理
                        Intent intent3 =new Intent(Activity_SignActivity.this,Tab02Equipment.class);
                        intent3.putExtra("Name",user.getName());
                        startActivity(intent3);  //启动
                        break;
                    case 4://文档管理
                        Toast.makeText(Activity_SignActivity.this,"文档管理暂时未实现",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        showProgressDialog();
                        String url = DTCSUrl + "Event/Compet/competInterfaces.html";
                        DataSupport.deleteAll(Competition.class);
                        queryFromServer(url,"CompetInfo");
                        break;
                    case 6:
                        Toast.makeText(Activity_SignActivity.this,"暂不支持权限功能",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        });

        GV_Other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("GV_Info点击事件", "onItemClick: "+"GV_Other"+position+"被点击");
                switch (position){
                    case 0://共享文件
                        showProgressDialog();
                        String url = DTCSUrl + "Advanced/File/IntefaceDownload.html";
                        DataSupport.deleteAll(File.class);
                        queryFromServer(url,"DownloadFile");
                        break;
                    case 1:
                        Intent intent1 =new Intent(Activity_SignActivity.this,LoadingActivity.class);
                        intent1.putExtra("start","0");
                        startActivity(intent1);  //启动
                        break;
                    case 2://课程http://127.0.0.1:81/DTCS/Event/Lesson/interfaces.html
                        showProgressDialog();
                        String urlLesson = DTCSUrl + "Event/Lesson/interfaces.html";
                        DataSupport.deleteAll(Lesson.class);
                        queryFromServer(urlLesson,"Lessons");
                        break;
                    case 3://计划任务
                        Toast.makeText(Activity_SignActivity.this,"暂无计划任务",Toast.LENGTH_SHORT).show();
                        break;
                    case 4://全局设置
                        Intent intent4 =new Intent(Activity_SignActivity.this,Tab02Setting.class);
                        startActivity(intent4);  //启动
                        break;

                    case 5://DTCS网页
                        Intent intentWeb =new Intent(Activity_SignActivity.this,Tab03DTCSWebView.class);
                        intentWeb.putExtra("WebuserID",user.getUser_id());
                        intentWeb.putExtra("Webpassword",user.getPassword());
                        Log.d("个人信息", "onClick: "+user.getUser_id()+user.getPassword());
                        startActivity(intentWeb);  //启动
                        break;

                    default:
                }
            }
        });
    }

    //下载TAB03图片
    public void loadHeadPortrai(String userID){
        final String headPortraitURL=DTCSUrl+"Public/img/usrimg/"+userID+".jpg";//头像
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Activity_SignActivity.this).edit();
        editor.putString("headPortrait"+userID,headPortraitURL);
        editor.apply();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(Activity_SignActivity.this).load(headPortraitURL).into(IV_headPortrait);
                Log.d("网络", "run: 下载头像"+headPortraitURL);

            }
        });


    }
    //更新账号信息
    public void updataUser(){
        String GET_NEWS_URL=DTCSUrl+"settings/index/verifyLabInterface/id/"+user.getUser_id()+".html";
        queryFromServer(GET_NEWS_URL,"updateUser");
    }

    //与服务器相链接
    public boolean queryFromServer(String URL,final  String type){

        HttpUtil.sendOKHttpRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Activity_SignActivity.this,"刷新失败（网络错误或服务端断开链接）",Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                //刷新
                if ("linkServer".equals(type)) {
                    if (responseText.length() > 2) {
                        //刷新成功
                        Log.d("刷新成功","onResponse()");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_SignActivity.this,"刷新成功 (刷新消耗流量)",Toast.LENGTH_SHORT).show();
                                closeProgressDialog();
                            }
                        });
                    } else {
                        //刷新失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_SignActivity.this,"刷新失败（网络错误或服务端断开链接）",Toast.LENGTH_SHORT).show();
                                closeProgressDialog();
                            }
                        });
                    }
                }
                //更新用户数据
                if ("updateUser".equals(type)){
                    if(responseText.length()>2){
                        //找到数据并，添加数据
                         Utility.updataUserResqponse(responseText);
                        Log.d("数据responseText", "onResponse: "+responseText);
                    }else{
                        Log.d("账号错误", "onResponse: ");
                        // Toast.makeText(Activity_Login.this,"账号错误",Toast.LENGTH_SHORT).show();
                    }

                }//else if

                if ("loadBirthday".equals(type)){
                    if (responseText.length()>3){
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                        StrBirthdayName = "今天 "+ df.format(new Date())+"祝 ";
                        StrBirthdayName = StrBirthdayName + Utility.loadBirthday(responseText)+"生日快乐";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               TV01_Birthday.setText(StrBirthdayName);
                                TV01_Birthday.setTextColor(0xffDC143C);
                            }
                        });
                        Log.d("过生日的人", "loadBirthday: "+StrBirthdayName);
                    }
                }
                //下载签到数据
                if ("loadSignDay".equals(type)){
                    if (responseText.length()>3){
                        try {
                            Utility.loafSignData(responseText);

                        } catch (Exception e) {
                           // e.printStackTrace();
                        }
                        Log.d("签到", "loadSignDay: "+responseText);
                        CheckCanlender();       //显示天数
                    }
                }

                if ("DownloadFile".equals(type)) {
                    if (responseText.length() > 3) {
                        try {
                            Utility.DownloadFileData(responseText);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent0 = new Intent(Activity_SignActivity.this,Tab02DownloadFile.class);
                                    startActivity(intent0);
                                    closeProgressDialog();
                                }
                            });

                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        Log.d("共享文件数据", "DownloadFile: " + responseText);
                    }
                }

                if ("Lessons".equals(type)) {
                    if (responseText.length() > 3) {
                        try {
                            Utility.LessonsInfo(responseText);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Activity_SignActivity.this,Tab02Lessons.class);
                                    startActivity(intent);
                                    closeProgressDialog();
                                }
                            });
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        Log.d("课程信息", "DownloadFile: " + responseText);
                    }
                }

                if ("CompetInfo".equals(type)) {
                    if (responseText.length() > 3) {
                        try {
                            Utility.CompetInfo(responseText);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Activity_SignActivity.this,Tab02Compet.class);
                                    startActivity(intent);
                                    closeProgressDialog();
                                }
                            });
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        Log.d("比赛信息", "CompetInfo: " + responseText);
                    }
                }


            }//发送请求
        });
        return true;
}


    /**
     *生成二维码
     */
    public void make(){
        //tab03生成二维码
        String input=user.getUser_id();
        //生成二维码，然后为二维码增加logo
        Bitmap bitmap= EncodingUtils.createQRCode(input,500,500,null);
        Log.d("二维码", "make: ");
        IV_QRcode.setImageBitmap(bitmap);
    }

    //页卡切换
    private void changeTab(int id) {
        switch (id) {
            case R.id.RB_home:

                if (id != R.mipmap.main_fram1) {
                    RB_home.setBackgroundResource(R.mipmap.main_fram1);
                    RB_shop.setBackgroundResource(R.mipmap.main_shop);
                    RB_me.setBackgroundResource(R.mipmap.main_me);
                }

                viewpager.setCurrentItem(0);
            case 0:
                if (id != R.mipmap.main_fram1) {
                    RB_home.setBackgroundResource(R.mipmap.main_fram1);
                    RB_shop.setBackgroundResource(R.mipmap.main_shop);
                    RB_me.setBackgroundResource(R.mipmap.main_me);

                }
                break;
            case R.id.RB_shop:
                if (id != R.mipmap.main_shop1) {
                    RB_home.setBackgroundResource(R.mipmap.main_fram);
                    RB_shop.setBackgroundResource(R.mipmap.main_shop1);
                    RB_me.setBackgroundResource(R.mipmap.main_me);
                }

                viewpager.setCurrentItem(1);
            case 1:
                if (id != R.mipmap.main_shop1) {
                    RB_home.setBackgroundResource(R.mipmap.main_fram);
                    RB_shop.setBackgroundResource(R.mipmap.main_shop1);
                    RB_me.setBackgroundResource(R.mipmap.main_me);
                }
                break;
            case R.id.RB_me:
                if (id != R.mipmap.main_me1) {
                    RB_home.setBackgroundResource(R.mipmap.main_fram);
                    RB_shop.setBackgroundResource(R.mipmap.main_shop);
                    RB_me.setBackgroundResource(R.mipmap.main_me1);
                }
                viewpager.setCurrentItem(2);
            case 2:
                if (id != R.mipmap.main_me1) {
                    RB_home.setBackgroundResource(R.mipmap.main_fram);
                    RB_shop.setBackgroundResource(R.mipmap.main_shop);
                    RB_me.setBackgroundResource(R.mipmap.main_me1);
                }
                break;

            default:
                break;
        }
    }

    //开启加载
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在读取数据...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }
    //关闭加载
    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    //
    /**
     *AsyncTack
     */
    class MyAsync_Tack extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String uerID = strings[1];
            Log.d("url", "doInBackground: "+url);
            Log.d("uerID", "doInBackground: "+uerID);
            String result = Utility.UserSignResponse(url,uerID);
            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            Log.d("result", "onPostExecute: "+result);
            if ("\uFEFFdefeat".equals(result)){
                Toast.makeText(Activity_SignActivity.this,"签到失败（网络错误或服务端断开链接）",Toast.LENGTH_SHORT).show();

            }
            if ("\uFEFFsuccessSign".equals(result)){
                Bt_Sgin.setText("已经签到");
                Bt_Sgin.setTextColor(0xffAFABAB);
                DataSupport.deleteAll(SignLogin.class,"user_id = ?",user.getUser_id());    //删除原来数据
                String SignDayURL = DTCSUrl+"Settings/index/signInterface/id/"+user.getUser_id();
                queryFromServer(SignDayURL,"loadSignDay");
                Toast.makeText(Activity_SignActivity.this,"签到成功",Toast.LENGTH_SHORT).show();
            }
            if ("\uFEFFdefeatSign_login".equals(result)){//请登录后签到
                Toast.makeText(Activity_SignActivity.this,"请登录后签到",Toast.LENGTH_SHORT).show();
            }
            if ("\uFEFFdefeatSign_repetition".equals(result)){//重复签到

                Bt_Sgin.setText("已经签到");
                Bt_Sgin.setTextColor(0xffAFABAB);
                // DataSupport.deleteAll(SignLogin.class,"user_id = ?",user.getUser_id());    //删除原来数据
                //Lo.d( "删除原来数据", "run: "+user.getUser_id());
                // String SignDayURL = DTCSUrl+"Settings/index/signInterface/id/"+user.getUser_id();
                // queryFromServer(SignDayURL,"loadSignDay");
                Toast.makeText(Activity_SignActivity.this,"请勿重复签到",Toast.LENGTH_SHORT).show();

            }

            if ("\uFEFFdefeatSign_ban".equals(result)){//系统关闭
                Toast.makeText(Activity_SignActivity.this,"系统当前处于禁止签到状态",Toast.LENGTH_SHORT).show();
            }
            closeProgressDialog();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //广播开启
        Setting set = DataSupport.findAll(Setting.class).get(0);
        if (set.getSign_switch().equals("1")){
            setAlarmTime(Activity_SignActivity.this, setTime(Activity_SignActivity.this,set.getHour(),set.getMinute()) , SIGNACTION, DAY_SECOND);
        }
    }
}







