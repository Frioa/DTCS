package com.example.dtcs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dtcs.Adapter.CompetAdapter;
import com.example.dtcs.db.Competition;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by q9163 on 16/02/2018.
 */

public class Tab02Compet extends AppCompatActivity implements View.OnClickListener   {
    private ImageView BackArrow;
    private ListView listView;
    private List<Competition> compets;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02compet);
        init();
    }

    private void init() {
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return5);
        listView = (ListView) findViewById(R.id.LV_CompetListView);
        compets = DataSupport.findAll(Competition.class);

        Log.d("比赛列表", "init: "+compets.toString());
        CompetAdapter competAdapter = new CompetAdapter(Tab02Compet.this,R.layout.item_compet,compets);
        listView.setAdapter(competAdapter);
        BackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02Return5:
                finish();
                break;
            case R.id.TV_equitUpdata://上传
                Intent intent = getIntent();
                break;
        }
    }
}
