package com.example.dtcs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import static com.example.dtcs.Enum.Major.DTCSUrl;

/**
 * Created by q9163 on 28/02/2018.
 */

public class Tab03DTCSWebView extends AppCompatActivity implements View.OnClickListener{
    private ImageView IV_return;
    private WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab03dtcs);
        init();
    }

    private void init() {
        IV_return = (ImageView) findViewById(R.id.IV_tab02ReturnWeb);
        webView = (WebView) findViewById(R.id.WV_dtvsWeb);
        IV_return.setOnClickListener(this);
        Intent intent = new Intent();
        Intent Webintent = getIntent();
        String userID= Webintent.getStringExtra("WebuserID");
        String password= Webintent.getStringExtra("Webpassword");
        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        String htmlUrl = DTCSUrl+"Home/Index/Signinterface/userID/"+userID+"/password/"+password+".html";
        Log.d("URL", "init: "+htmlUrl);
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(htmlUrl);
        intent.setData(content_url);
        startActivity(intent);

       /* WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        // 打开本包内asset目录下的index.html文件
        webView.loadUrl("file:///android_asset/index.html");
        // 打开本地sd卡内的index.html文件
        webView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");


        // 打开指定URL的html文件
        //
        String htmlUrl = "";
        webView.loadUrl(htmlUrl);
        */
       // webView.loadData(c+htmlUrlontent, "text/html", "UTF-8"); // 加载定义的代码，并设定编码格式和字符集。

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_tab02ReturnWeb:
                finish();
                break;

        }
    }
}
