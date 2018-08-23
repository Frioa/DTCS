package com.example.dtcs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dtcs.db.Chk;
import com.example.dtcs.db.Equipment;
import com.example.dtcs.util.HttpUtil;
import com.example.dtcs.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.dtcs.Enum.Major.DTCSUrl;

/**
 * Created by q9163 on 09/02/2018.
 */

public class Tab02Equipment extends AppCompatActivity implements View.OnClickListener {

    private ImageView BackArrow;
    private LinearLayout LL_EquipInfo,LL_EquipSafe,LL_EquipRecip;
    //加载（请求网络）
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02equipment);
        init();
    }
    void init(){
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return3);
        LL_EquipInfo = (LinearLayout) findViewById(R.id.LL_EquipmentInformation);
        LL_EquipSafe = (LinearLayout) findViewById(R.id.LL_EquipmentSafety);
        LL_EquipRecip = (LinearLayout) findViewById(R.id.LL_EquipmentReceipt);

        BackArrow.setOnClickListener(this);
        LL_EquipInfo.setOnClickListener(this);
        LL_EquipSafe.setOnClickListener(this);
        LL_EquipRecip.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.IV_tab02Return3:
                finish();
                break;
            case R.id.LL_EquipmentInformation:
                //http://127.0.0.1:81/DTCS/Finance/Device/DeviceInterface
                String URL_Information = DTCSUrl +"Finance/Device/DeviceInterface";
                showProgressDialog();
                DataSupport.deleteAll(Equipment.class);
                queryFromServer(URL_Information,"loadEquipInfo");
                break;
            case R.id.LL_EquipmentSafety:

                String URL_safeInformation = DTCSUrl +"Finance/Device/SafeInterface";
                showProgressDialog();
                DataSupport.deleteAll(Chk.class);
                queryFromServer(URL_safeInformation,"loadSafeEquipInfo");
                break;
            case R.id.LL_EquipmentReceipt:
                Toast.makeText(Tab02Equipment.this,"借条功能暂未实现",Toast.LENGTH_SHORT).show();
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

    //与服务器相链接
    public boolean queryFromServer(String URL,final  String type){

        HttpUtil.sendOKHttpRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Tab02Equipment.this,"网络错误或服务端断开链接",Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();

                //更新用户数据
                if ("loadEquipInfo".equals(type)){
                    if(responseText.length()>2){
                        //找到数据并，添加数据
                        Utility.DownEquipInfo(responseText);
                        Log.d("设备数据", "onResponse: "+responseText);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent1 =new Intent(Tab02Equipment.this,Tab02EquipmentInfor.class);

                                startActivity(intent1);  //启动
                                  closeProgressDialog();
                            }
                        });
                    }
                }

                //更新用户数据
                if ("loadSafeEquipInfo".equals(type)){
                    if(responseText.length()>2){
                        //找到数据并，添加数据
                        Utility.SafeCheckInfo(responseText);
                        Log.d("设备数据", "onResponse: "+responseText);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = getIntent();
                                Intent intent2 =new Intent(Tab02Equipment.this,Tab02SafeEquip.class);
                                intent2.putExtra("Name",intent.getStringExtra("Name"));
                                Log.d("name", "run: "+intent.getStringExtra("Name"));
                                startActivity(intent2);  //启动
                                closeProgressDialog();
                            }
                        });
                    }
                }
            }//发送请求
        });
        return true;
    }

}
