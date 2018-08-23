package com.example.dtcs;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcs.db.File;
import com.example.dtcs.db.User;
import com.example.dtcs.service.DownloadService;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.example.dtcs.Enum.Major.DTCSUrl;

/**
 * Created by q9163 on 28/01/2018.
 */

public class Tab02DownloadFlieView extends AppCompatActivity implements View.OnClickListener {

    private ImageView BackArrow;
    private LinearLayout LL_download,LL_pause,LL_cancel;
    private TextView TV_fname,TV_fdesript,TV_fdate,TV_fID,TV_ftype,TV_fsize,TV_fremark;
    private DownloadService.DownloadBinder downloadBinder;
    List<File> files;
    List<User> users;
    String fileName="";
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            downloadBinder =  (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName Name) {

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02downloadfileview);
        inti();
    }

    private void inti() {
        Intent intent = getIntent();
        files = DataSupport.where("fid = ?",intent.getStringExtra("fileID")).find(File.class);
        Log.d("files", "inti: "+files.toString());
        File file = files.get(0);
        Log.d("file", "inti: "+file.toString());
        users = DataSupport.where("user_id = ?",file.getPid()).find(User.class);
//        String UserName = users.get(0).getName();

      //  Log.d("共享者", "inti: "+UserName);
        TV_fname = (TextView) findViewById(R.id.TV_fileitemViewUserName);
        TV_fID = (TextView) findViewById(R.id.TV_fileitemViewID);
        TV_fdate = (TextView) findViewById(R.id.TV_flieitemDateView);
        TV_fdesript = (TextView) findViewById(R.id.TV_fileDesriptView);
        TV_ftype = (TextView) findViewById(R.id.TV_fileTypeView);
        TV_fsize = (TextView) findViewById(R.id.TV_fileSizeView);
        TV_fremark = (TextView) findViewById(R.id.TV_fileRemarkView);

        BackArrow = (ImageView) findViewById(R.id.IV_tab02Returnview);
        LL_download = (LinearLayout) findViewById(R.id.LL_tab02FileDown);
        LL_pause = (LinearLayout) findViewById(R.id.LL_tab02Filepause);
        LL_cancel = (LinearLayout) findViewById(R.id.LL_tab02FileCancel);

        fileName = file.getFname();
        TV_fname.setText(fileName);
        TV_fID.setText("ID:"+file.getFid());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TV_fdate.setText(format.format(file.getFdate()) + " "+file.getFid()+"上传");

        TV_fsize.setText(String.format("%.2f", (file.getSize()/1024))+"MB");
        String[] strs = file.getFtype().split("/");
        TV_ftype.setText("."+strs[strs.length-1]);
        TV_fdesript.setText(file.getFdescript()+"");
        if (file.getRemark()==""){
            TV_fremark.setText("这里什么都没有~~~");
        }else{
            TV_fremark.setText(file.getRemark());
        }
        LL_download.setOnClickListener(this);
        LL_pause.setOnClickListener(this);
        LL_cancel.setOnClickListener(this);
        BackArrow.setOnClickListener(this);
        Intent intent1 =new Intent(this, DownloadService.class);
        startService(intent1);//启动服务,保证服务一直在后台运行
        bindService(intent1, connection, BIND_AUTO_CREATE);//绑定服务，让MainActivity和服务进行通信
        if (ContextCompat.checkSelfPermission(Tab02DownloadFlieView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Tab02DownloadFlieView.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (downloadBinder == null){
            Log.d("downloadBinder为空", "onClick: ");
            return;
        }
        switch (view.getId()){
            case R.id.IV_tab02Returnview:
                break;
            case R.id.LL_tab02FileDown:
                String url = DTCSUrl+"Public/files/"+fileName;
                //String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downloadBinder.startDownload(url,fileName);
                break;
            case R.id.LL_tab02Filepause:
                downloadBinder.pauseDownload();
                break;
            case R.id.LL_tab02FileCancel:
                downloadBinder.cancelDownload();
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

}
