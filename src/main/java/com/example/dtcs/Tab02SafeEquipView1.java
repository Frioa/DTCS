package com.example.dtcs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dtcs.db.User;
import com.example.dtcs.util.HttpUtil;
import com.example.dtcs.util.Utility;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.dtcs.Enum.Major.DTCSUrl;

/**
 * Created by q9163 on 10/02/2018.
 */

public class Tab02SafeEquipView1 extends AppCompatActivity implements View.OnClickListener {
    private ImageView BackArrow,IV_head;
    private TextView TV_date,TV_Power,TV_Chair,TV_Computer,TV_KeyMap,TV_airConditioner,TV_Sanitation,TV_MainSwitch
            ,TV_Name;


    private List<User> users;
    //加载（请求网络）
    private ProgressDialog progressDialog;
    private SharedPreferences prf;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02safeequipmentinfoview);
        init();
    }

    private void init() {
        IV_head = (ImageView) findViewById(R.id.IV_SafeitemViewHead);
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return3_2_1);
        TV_Name = (TextView) findViewById(R.id.TV_SafeitemViewUserName);
        TV_Power = (TextView) findViewById(R.id.TV_SafePowertView);
        TV_Chair = (TextView) findViewById(R.id.TV_SafeChairView);
        TV_Computer = (TextView) findViewById(R.id.TV_SafeComputerView);
        TV_KeyMap = (TextView) findViewById(R.id.TV_SafeKeyMapView);
        TV_airConditioner = (TextView) findViewById(R.id.TV_SafeairConditionereView);
        TV_Sanitation = (TextView) findViewById(R.id.TV_SafesanitationView);
        TV_MainSwitch = (TextView) findViewById(R.id.TV_SafemainSwitchView);
        TV_date = (TextView) findViewById(R.id.TV_SafeitemViewDate);
        Intent intent = getIntent();

        TV_Name.setText(intent.getStringExtra("name"));
        NameTurnID();


        //TV_Power.setText(intent.getStringExtra("chka"));
        if (intent.getStringExtra("chka").equals("1")){
            TV_Power.setText("合格");
        }else {
            TV_Power.setText("不合格");
            TV_Power.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }
        //TV_Chair.setText(intent.getStringExtra("chkb"));
        if (intent.getStringExtra("chkb").equals("1")){
            TV_Chair.setText("合格");
            TV_Chair.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.colorAccent));
        }else {
            TV_Chair.setText("不合格");
            TV_Chair.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }
        //TV_Computer.setText(intent.getStringExtra("chkc"));
        if (intent.getStringExtra("chkc").equals("1")){
            TV_Computer.setText("合格");
            TV_Computer.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.colorAccent));
        }else {
            TV_Computer.setText("不合格");
            TV_Computer.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }
       // TV_KeyMap.setText(intent.getStringExtra("chkd"));
        if (intent.getStringExtra("chkd").equals("1")){
            TV_KeyMap.setText("合格");
            TV_KeyMap.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.colorAccent));
        }else {
            TV_KeyMap.setText("不合格");
            TV_KeyMap.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }

        if (intent.getStringExtra("chke").equals("1")){
            TV_airConditioner.setText("合格");
            TV_airConditioner.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.colorAccent));
        }else {
            TV_airConditioner.setText("不合格");
            TV_airConditioner.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }

        if (intent.getStringExtra("chkf").equals("1")){
            TV_Sanitation.setText("合格");
            TV_Sanitation.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.colorAccent));
        }else {
            TV_Sanitation.setText("不合格");
            TV_Sanitation.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }

        if (intent.getStringExtra("chkg").equals("1")){
            TV_MainSwitch.setText("合格");
            TV_MainSwitch.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.colorAccent));
        }else {
            TV_MainSwitch.setText("不合格");
            TV_MainSwitch.setTextColor(Tab02SafeEquipView1.this.getResources().getColor(R.color.red));
        }
        TV_date.setText("检查日期: "+intent.getStringExtra("cDate"));
        BackArrow.setOnClickListener(this);

    }

    private void NameTurnID(){
        Intent intent = getIntent();
        String url = DTCSUrl + "Finance/Device/nameturnIDPost/id/"+intent.getStringExtra("name")+".html";
        Log.d("Name转ID URL", "NameTurnID: "+url);
        queryFromServer(url, "NameTrueID");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02Return3_2_1:
                finish();
                break;
            case R.id.TV_equitUpdata://上传

                break;
        }
    }


    //下载TAB03图片
    public void loadHeadPortrai(String userID){
    final String headPortraitURL=DTCSUrl+"Public/img/usrimg/"+userID+".jpg";//头像
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Tab02SafeEquipView1.this).edit();
    editor.putString("headPortrait"+userID,headPortraitURL);
    editor.apply();
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Glide.with(Tab02SafeEquipView1.this).load(headPortraitURL).into(IV_head);
            Log.d("网络", "run: 下载头像"+headPortraitURL);

        }
    });


}

    //与服务器相链接
    public boolean queryFromServer(String URL,final  String type){

        HttpUtil.sendOKHttpRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Tab02SafeEquipView1.this,"获取头像失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();

                if ("NameTrueID".equals(type)){
                    if(responseText.length()>2){
                        Log.d("网络数据", "onResponse: "+responseText);
                        final String ID = Utility.NameTrunID(responseText);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                prf = PreferenceManager.getDefaultSharedPreferences(Tab02SafeEquipView1.this);
                                //自动保存图片
                                if (prf.getString("headPortrait"+ID,null) != null){
                                    Glide.with(Tab02SafeEquipView1.this).load(prf.getString("headPortrait"+ID,null)).into(IV_head);    //头像
                                    Log.d("显示头像", "本地头像 "+ID);
                                }else{
                                    loadHeadPortrai(ID);//下载图片
                                    Log.d("显示头像", "网络下载头像 ");
                                }


                            }
                        });
                    }
                }

            }//发送请求
        });
        return true;
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

}
