package com.example.dtcs.service;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dtcs.util.Utility;

/**
 * Created by q9163 on 29/12/2017.
 */

public class MyAsyncTack extends AsyncTask<String,Void,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String uerID = strings[1];
        String result = Utility.UserSignResponse(url,uerID);

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    /* //开启加载
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
    //*/
}
