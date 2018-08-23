package com.example.dtcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcs.db.GsonSafe;
import com.example.dtcs.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.dtcs.Enum.Major.DTCSUrl;

/**
 * Created by q9163 on 12/02/2018.
 */

public class Tab02SafeUpdata extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private ImageView BackArrow;
    private Spinner S_power,S_tableChair,S_computer,S_keyMap,S_airConditioner,S_sanitation,S_mainSwitch;
    private SimpleAdapter adapter;
    private EditText ET_name;
    private TextView TV_submit;
    private String Name="";
    private List<Map<String ,Object>>dataList;
    private GsonSafe safe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02safupdatei);
        init();
    }

    private void init() {
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return3_2_2);
        S_power = (Spinner) findViewById(R.id.spinner_power);
        S_tableChair = (Spinner) findViewById(R.id.spinner_tableChair);
        S_computer = (Spinner) findViewById(R.id.spinner_computer);
        S_keyMap = (Spinner) findViewById(R.id.spinner_keyMap);
        S_airConditioner = (Spinner) findViewById(R.id.spinner_airConditioner);
        S_sanitation = (Spinner) findViewById(R.id.spinner_sanitation);
        S_mainSwitch = (Spinner) findViewById(R.id.spinner_mainSwitch);
        ET_name = (EditText) findViewById(R.id.ET_safeUpdataName);
        TV_submit = (TextView) findViewById(R.id.TV_safeUpdataSubmit);
        Intent intent = getIntent();
        Name = intent.getStringExtra("Name");
        Log.d("name", "onClick: "+Name);
        ET_name.setText(" "+Name);
        ET_name.setFocusable(false);


        dataList = new ArrayList<Map<String,Object>>();
        getData();
        adapter = new SimpleAdapter(this ,dataList,R.layout.item_safeupdata,new String[]{"item_text"},new int[]{R.id.item_text});
        adapter.setDropDownViewResource(R.layout.item_safeupdata);
        S_power.setAdapter(adapter);
        S_tableChair.setAdapter(adapter);
        S_computer.setAdapter(adapter);
        S_keyMap.setAdapter(adapter);
        S_airConditioner.setAdapter(adapter);
        S_sanitation.setAdapter(adapter);
        S_mainSwitch.setAdapter(adapter);

        S_power.setOnItemSelectedListener(this);
        BackArrow.setOnClickListener(this);
        TV_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02Return3_2_2:
                finish();
                break;
            case R.id.TV_safeUpdataSubmit://上传

                safe = new GsonSafe();
                safe.setName(Name);
               if(S_power.getSelectedItem().toString().contains("不合格")){
                   safe.setChka(0);
               }else {
                   safe.setChka(1);
               }
                //S_tableChair
                if(S_tableChair.getSelectedItem().toString().contains("不合格")){
                    safe.setChkb(0);
                }else {
                    safe.setChkb(1);
                }
                //S_computer
                if(S_computer.getSelectedItem().toString().contains("不合格")){
                    safe.setChkc(0);
                }else {
                    safe.setChkc(1);
                }
                //S_keyMap
                if(S_keyMap.getSelectedItem().toString().contains("不合格")){
                    safe.setChkd(0);
                }else {
                    safe.setChkd(1);
                }
                //S_airConditioner
                if(S_airConditioner.getSelectedItem().toString().contains("不合格")){
                    safe.setChke(0);
                }else {
                    safe.setChke(1);
                }
                //S_sanitation
                if(S_sanitation.getSelectedItem().toString().contains("不合格")){
                    safe.setChkf(0);
                }else {
                    safe.setChkf(1);
                }
                //S_mainSwitch
                if(S_mainSwitch.getSelectedItem().toString().contains("不合格")){
                    safe.setChkg(0);
                }else {
                    safe.setChkg(1);
                }

                Log.d("提交安全检查信息", "onClick: "+ safe.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String URL = DTCSUrl+"Finance/Device/UploadSafePost.html";
                        String result = Utility.UploadSafeResponse(URL,safe);
                        if (result.equals("success_upload")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Tab02SafeUpdata.this,"上传数据成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                        }
                        if (result.equals("dafeat_exist")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Tab02SafeUpdata.this,"上传失败（今已上传）",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                }).start();

                break;
        }
    }

    public void getData() {
        Map<String ,Object> map1 = new HashMap<String, Object>();
        map1.put("item_text","合格");
        Map<String ,Object> map2 = new HashMap<String, Object>();
        map2.put("item_text","不合格");
        dataList.add(map1);
        dataList.add(map2);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Log.d("a", "onItemSelected: "+S_power.getSelectedItem().toString()+ " ");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
