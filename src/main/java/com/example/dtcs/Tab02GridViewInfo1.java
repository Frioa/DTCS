package com.example.dtcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcs.Adapter.UserAdapter;
import com.example.dtcs.db.ExperimentUser;
import com.example.dtcs.util.HttpUtil;
import com.example.dtcs.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.dtcs.Enum.Major.DTCSUrl;
/**
 * Created by q9163 on 28/12/2017.
 */

public class Tab02GridViewInfo1 extends AppCompatActivity implements View.OnClickListener,Tab02ReFlashListView.IReflashListener {
    private TextView TV_Inset,TV_delete,TV_select;
    private LinearLayout LL_Insert,LL_Delete,LL_Select;
    private ImageView IV_Return;

    private View laout_pull;
    private ProgressBar PB_pullRefresh;
    private Tab02ReFlashListView LV_administrationListView;

    private List<ExperimentUser> users = new ArrayList<>() ;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02_listviewadministrator01);
        init();
        //initUser();
    }

    private void init() {
        LitePal.getDatabase();
      /*  TV_Inset = (TextView) findViewById(R.id.TV_tab02Inset01);
        TV_select = (TextView) findViewById(R.id.TV_tab02Select1);
        TV_delete = (TextView) findViewById(R.id.TV_tab02Delete1);*/
        IV_Return = (ImageView) findViewById(R.id.IV_tab02Return1);

        LL_Insert = (LinearLayout) findViewById(R.id.LL_tab02Inset1);
        LL_Select = (LinearLayout) findViewById(R.id.LL_tab02Select1);
        LL_Delete = (LinearLayout) findViewById(R.id.LL_tab02Delete1);

        laout_pull = findViewById(R.id.pull_include);
        PB_pullRefresh = (ProgressBar) laout_pull.findViewById(R.id.PB_pull_refresh);

        IV_Return.setOnClickListener(this);
        LL_Insert.setOnClickListener(this);
        LL_Select.setOnClickListener(this);
        LL_Delete.setOnClickListener(this);
        //DataSupport.deleteAll(ExperimentUser.class);
        //数据来源
        showList(users);
        Log.d("数据库中的数据", "init: "+users.toString());
    }
    void showList(List<ExperimentUser> apk_list){
        if (adapter==null){
            LV_administrationListView = (Tab02ReFlashListView) findViewById(R.id.LV_administratorListView);
            LV_administrationListView.setInterface(this);//
            apk_list = DataSupport.where("rank=?","1").find(ExperimentUser.class);
            adapter = new UserAdapter(Tab02GridViewInfo1.this,R.layout.item_user,apk_list);
            LV_administrationListView.setAdapter(adapter);

        }else {
            //adapter.notifyDataSetChanged();
            LV_administrationListView.setInterface(this);//
            adapter.clear();
            adapter = new UserAdapter(Tab02GridViewInfo1.this,R.layout.item_user,apk_list);
            Log.d("刷新的数据", "onReflash: "+apk_list.toString());
            //adapter.onDateChange(apk_list);
            LV_administrationListView.setAdapter(adapter);
        }
        if (apk_list.size()>0){
            //关闭include
            pullRefresh(false);
        }else{
            pullRefresh(true);
        }
    }

    void loadUserData(){
        String URl = DTCSUrl + "Personmge/Experiment/AdministratorInterface.html";
        queryFromServer(URl,"Administrator");
    }

    //与服务器相链接
    public boolean queryFromServer(String URL,final  String type){
        Log.d("下载实验员数据","onResponse()");
        HttpUtil.sendOKHttpRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Tab02GridViewInfo1.this,"管理员数据获得失败（没有打开网络链接）",Toast.LENGTH_SHORT).show();
                        LV_administrationListView.refilashComplete();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                //刷新
                if ("Administrator".equals(type)) {
                    if (responseText.length() > 2) {
                        //获取数据成功
                        Log.d("管理员数据","onResponse()" +responseText);
                        DataSupport.deleteAll(ExperimentUser.class,"rank = ?","1");    //获得数据成功删除原来数据
                        Utility.ExperimentData(responseText);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Tab02GridViewInfo1.this,"管理员数据获得成功 ",Toast.LENGTH_SHORT).show();
                                users.clear();
                                users = (DataSupport.where("rank=?","1").find(ExperimentUser.class));
                                //adapter.notifyDataSetChanged();
                                showList(users);
                                LV_administrationListView.refilashComplete();
                            }
                        });
                    } else {
                        //刷新失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Tab02GridViewInfo1.this,"管理员数据获得失败（网络错误或服务端断开链接）",Toast.LENGTH_SHORT).show();
                                LV_administrationListView.refilashComplete();

                            }
                        });
                    }
                }
            }//发送请求
        });
        return true;
    }
    public void pullRefresh(boolean bool){
        if (bool){
            laout_pull.setVisibility(View.VISIBLE);

        }else {
            laout_pull.setVisibility(View.GONE);
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.IV_tab02Return1:
                finish();
                break;
            case R.id.LL_tab02Inset1:
                Toast.makeText(Tab02GridViewInfo1.this,"点击增加",Toast.LENGTH_SHORT).show();
                break;
            case R.id.LL_tab02Select1:
                Toast.makeText(Tab02GridViewInfo1.this,"点击查找",Toast.LENGTH_SHORT).show();
                break;
            case R.id.LL_tab02Delete1:
                Toast.makeText(Tab02GridViewInfo1.this,"点击删除",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onReflash() {
        loadUserData();
    }
}
