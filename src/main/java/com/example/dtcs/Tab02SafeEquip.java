package com.example.dtcs;

import android.content.Intent;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dtcs.Adapter.EquipSafeInfoAdapter;
import com.example.dtcs.db.Chk;


import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.List;

/**
 * Created by q9163 on 10/02/2018.
 */

public class Tab02SafeEquip extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener {
    private ImageView BackArrow;
    private ListView listView;
    private List<Chk> Safeequip;
    private TextView TV_updata;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02safeequipmentinfo);
        init();
    }

    private void init() {
        TV_updata = (TextView) findViewById(R.id.TV_equitUpdata);
        listView = (ListView) findViewById(R.id.LV_SafeEquipmentInfo);

        Safeequip = DataSupport.findAll(Chk.class);
        Collections.reverse(Safeequip);
        Log.d("Safeequip", "init: "+Safeequip.toString());
        EquipSafeInfoAdapter equipAdapter = new EquipSafeInfoAdapter(Tab02SafeEquip.this,R.layout.item_equipsafeinfo,Safeequip);
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return3_2);

        listView.setAdapter(equipAdapter);
        BackArrow.setOnClickListener(this);
        TV_updata.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02Return3_2:
                finish();
                break;
            case R.id.TV_equitUpdata://上传
                Intent intent = getIntent();
                Intent intent1 =new Intent(Tab02SafeEquip.this,Tab02SafeUpdata.class);
                intent1.putExtra("Name",intent.getStringExtra("Name"));

                startActivity(intent1);  //启动
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Chk chk = Safeequip.get(position);
        Log.d("ListView被点击", "onItemClick: "+position);
        Intent intentView =new Intent(Tab02SafeEquip.this,Tab02SafeEquipView1.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(chk.getCdate());

        Log.d("chk:",chk.toString());
        intentView.putExtra("cDate",date);
        intentView.putExtra("chka",chk.getChka()+"");
        intentView.putExtra("chkb",chk.getChkb()+"");
        intentView.putExtra("chkc",chk.getChkc()+"");
        intentView.putExtra("chkd",chk.getChkd()+"");
        intentView.putExtra("chke",chk.getChke()+"");
        intentView.putExtra("chkf",chk.getChkf()+"");
        intentView.putExtra("chkg",chk.getChkg()+"");
        intentView.putExtra("name",chk.getName());
        startActivity(intentView);  //启动
    }
}
