package com.example.dtcs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dtcs.Adapter.FileAdapter;
import com.example.dtcs.db.File;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q9163 on 26/01/2018.
 */

public class Tab02DownloadFile extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private TextView TV_loadup;
    private ImageView BackArrow;
    private List<File> files = new ArrayList<>();
    String DTCSUrl = "http://192.168.43.63:81/DTCS/";
    private ListView LV_FlieListView;
    //加载（请求网络）
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02downloadfile);
        init();

    }
    void init(){
        TV_loadup = (TextView) findViewById(R.id.TV_fileloadup);
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return01);
        BackArrow.setOnClickListener(this);

        TV_loadup.setOnClickListener(this);
        files = DataSupport.findAll(File.class);
        Log.d("files数据库资料", "init: "+files.toString());
        FileAdapter fileAdapter = new FileAdapter(Tab02DownloadFile.this,R.layout.item_file,files);
        LV_FlieListView = (ListView) findViewById(R.id.LV_DownloadFileListView);
        LV_FlieListView.setAdapter(fileAdapter);
        LV_FlieListView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.IV_tab02Return01:
                finish();
                break;
            case R.id.TV_fileloadup:
                break;
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case 1:
               if (grantResults.length>0 && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                   Toast.makeText(this,"拒绝权限将无法改功能",Toast.LENGTH_SHORT).show();
                   finish();
               }
               break;
           default:
       }
    }*/

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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        File file = files.get(position);
        Log.d("ListView被点击", "onItemClick: "+position);
        Intent intentView =new Intent(Tab02DownloadFile.this,Tab02DownloadFlieView.class);
        intentView.putExtra("fileID", file.getFid()+"");
        startActivity(intentView);  //启动
    }
}