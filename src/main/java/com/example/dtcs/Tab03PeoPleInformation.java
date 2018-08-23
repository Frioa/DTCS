package com.example.dtcs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dtcs.db.User;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by q9163 on 23/12/2017.
 */

public class Tab03PeoPleInformation extends AppCompatActivity implements View.OnClickListener {
    User user;
    List<User> userList;
    Map<String,String> depart = new HashMap<String,String>() ;

    private TextView TV_Name,TV_Class,TV_Major,TV_Department,TV_Phone,TV_Email,TV_Sex,TV_Brithday,TV_Grade;
    private ImageView IV_Head,IV_Retrunarrow;

    private SharedPreferences prf;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab03_information);
        InitMap();
        InitData();
    }

    void InitData(){
        //传递参数
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        //从数据库中得到资料
        userList = DataSupport.where("user_id=?", userID).find(User.class);
        user = userList.get(0);
        Log.d("用户资料userID", "InitData: "+user.toString());


        TV_Name = (TextView) findViewById(R.id.TV_Ppname);
        TV_Sex = (TextView) findViewById(R.id.TV_Ppsex);
        TV_Brithday = (TextView)findViewById(R.id.TV_Ppbirthday);
        TV_Phone = (TextView) findViewById(R.id.TV_Ppphone);
        TV_Email = (TextView) findViewById(R.id.TV_Email);
        TV_Grade =  (TextView) findViewById(R.id.TV_Ppgrade);
        TV_Department = (TextView) findViewById(R.id.TV_Ppdepartment);
        TV_Major = (TextView) findViewById(R.id.TV_Ppmajor);
        TV_Class = (TextView) findViewById(R.id.TV_Ppclass);

        TV_Name.setText(user.getName());
        char sex = user.getUser_id().charAt(5) ;
        if (sex=='M'){
            TV_Sex.setText("男");
        }else{
            TV_Sex.setText("女");
        }

        TV_Brithday.setText(user.getBirth());
        TV_Phone.setText(user.getPhone());
        TV_Email.setText(user.getEmail());
        TV_Grade.setText(user.getGrade());
        TV_Department.setText(user.getDepart());
        TV_Class.setText(user.getClass_()+"班");
        //设置专业
        for (Map.Entry<String, String> m :depart.entrySet())  {
            if (m.getKey().equals(user.getMajornum())){
                TV_Major.setText(m.getValue());
                System.out.println(m.getKey()+"\t"+m.getValue());
                break;
            }
        }
        IV_Head = (ImageView) findViewById(R.id.IV_Pphead);
        IV_Retrunarrow = (ImageView) findViewById(R.id.IV_PpReturn);
        IV_Retrunarrow.setOnClickListener(this);

        prf = PreferenceManager.getDefaultSharedPreferences(this);
        Glide.with(this).load(prf.getString("headPortrait"+user.getUser_id(),null)).into(IV_Head);    //头像
    }

    void InitMap(){
        depart.put("101","计算机科学与技术");
        depart.put("1011","计算机");
        depart.put("102","信息管理与信息系统");
        depart.put("1022","信管");
        depart.put("103","电子商务");
        depart.put("201","勘查技术与贸易");
        depart.put("202","环境工程");
        depart.put("203","资源勘查工程");
        depart.put("301","国际经济与贸易");
        depart.put("302","金融学");
        depart.put("401","会计学");
        depart.put("402","环境工程");
        depart.put("403","市场营销");
        depart.put("501","法学");
        depart.put("502","英语");
        depart.put("503","广告学");
        depart.put("601","工商管理");
        depart.put("602","人力资源管理");
        depart.put("603","物流管理");
        depart.put("604","行政管理");
        depart.put("605","劳动与社会保障");
        depart.put("606","工程管理");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //TAB03
            case R.id.IV_PpReturn://个人信息页面
                finish();
                break;
        }
    }
}