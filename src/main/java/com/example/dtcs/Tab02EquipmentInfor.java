package com.example.dtcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dtcs.Adapter.EquipInfoAdapter;
import com.example.dtcs.db.Equipment;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q9163 on 09/02/2018.
 */

public class Tab02EquipmentInfor extends AppCompatActivity implements View.OnClickListener  {
    private ImageView BackArrow;
    private ListView LV_Equipment;
    private List<Equipment> equip = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02equipmentinfo);
        init();
    }

    private void init() {
        equip = DataSupport.findAll(Equipment.class);
        Log.d("equip", "init: "+equip.toString());
        EquipInfoAdapter equipAdapter = new EquipInfoAdapter(Tab02EquipmentInfor.this,R.layout.item_equipinfo,equip);
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return3_1);
        LV_Equipment = (ListView) findViewById(R.id.LV_EquipmentInfo);
        LV_Equipment.setAdapter(equipAdapter);
        BackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02Return3_1:
                finish();
                break;
        }
    }
}
