package com.example.dtcs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcs.Enum.Major;
import com.example.dtcs.VerificationCode.VerificationCode;
import com.example.dtcs.db.Setting;
import com.example.dtcs.db.User;
import com.example.dtcs.util.HttpUtil;
import com.example.dtcs.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.R.attr.action;
import static com.example.dtcs.Enum.Major.SIGNACTION;



public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    private TextView TV_DTCS;
    private TextView TV_Datatomato,TV_Apply;
    private TextView TV_ForgetWord;

    private EditText ET_account;
    private EditText ET_password;

    private Button Bt_Login;

    private ImageView IV_verification;
    private String realCode;

    //用户信息列表
    private List<User> userList;

    //加载（请求网络）
    private ProgressDialog progressDialog;

    //记住密码
    String Name_id,Password;
    int n=0;
    //SharedPreferences保存密码
    private SharedPreferences prf;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_login);

        Init_View();//初始化findview
        Inite_Font();//设置字体
        Init_Image();//初始化图标
        Setting_EditText();//设置EditText

        showPassword();//记住密码
    }

    //初始化
    void Init_View(){

         TV_DTCS = (TextView) findViewById(R.id.Login_DTCS);
         TV_Datatomato = (TextView) findViewById(R.id.Login_Datatomato);
         TV_ForgetWord = (TextView) findViewById(R.id.TV_Forgetword);
        TV_Apply = (TextView) findViewById(R.id.TV_Apply);
        TV_ForgetWord.setOnClickListener(this);
        TV_Apply.setOnClickListener(this);
         ET_account = (EditText) findViewById(R.id.Login_Account);
         ET_password = (EditText) findViewById(R.id.Login_Wrod);

        Bt_Login = (Button) findViewById(R.id.Bt_Login);
        Bt_Login.setOnClickListener(this);




    }
    //字体
    void Inite_Font(){
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ARLRDBD.TTF");
        TV_DTCS.setTypeface(face);
    }

    void Init_Image(){
        //DTCS Icon
        Drawable dtcs = getResources().getDrawable(R.mipmap.dtcs_icon);
        dtcs.setBounds(0,0,50,50);
        TV_Datatomato.setCompoundDrawables(dtcs,null,null,null);

    }

    void Setting_EditText(){
        ET_account.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        ET_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
    }

    //自定义对话框（验证码）
    private void showNormalDialog() {
        //标题XML文件
        LayoutInflater layoutInflater = LayoutInflater.from(Activity_Login.this);
        View mTitleView = layoutInflater.inflate(R.layout.dialogtitle, null);

        //内容XML文件
        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.verification_code, null);

        //按钮XML文件
        View view_button = inflater.inflate(R.layout.dialog_button, null);

        //设置标题
        builder.setCustomTitle(mTitleView);
        builder.setView(view);

        //findviewbyid
        IV_verification = (ImageView) view.findViewById(R.id.IV_verification);
        final EditText ET_verification = (EditText) view.findViewById(R.id.ET_Code);
        TextView TV_OK = (TextView) view.findViewById(R.id.TV_ok);
        TextView TV_Cancel = (TextView) view.findViewById(R.id.TV_cancel);

        ET_verification.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        //验证码刷新
        IV_verification.setImageBitmap(VerificationCode.getInstance().createBitmap());
        realCode = VerificationCode.getInstance().getCode().toLowerCase();
        IV_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("TAG","realCode"+realCode);
                IV_verification.setImageBitmap(VerificationCode.getInstance().createBitmap());
                realCode = VerificationCode.getInstance().getCode().toLowerCase();
                //verification_code();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        //改变大小
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        lp.width=(int) (d.getHeight() * 0.42);
        lp.height=(int) (d.getHeight() * 0.325);
        //设置
        dialogWindow.setAttributes(lp);

        //点击OK跳转
        TV_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneCode = ET_verification.getText().toString().toLowerCase();
                if (phoneCode.equals(realCode)){
                    HttpUtil_SaveData();
                    Toast.makeText(Activity_Login.this,"验证码正确",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Log.v("TAG","realCode"+realCode);
                    IV_verification.setImageBitmap(VerificationCode.getInstance().createBitmap());
                    realCode = VerificationCode.getInstance().getCode().toLowerCase();
                    Toast.makeText(Activity_Login.this,phoneCode+"验证码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //点击取消
        TV_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    //点击登陆
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Bt_Login:
                LitePal.getDatabase();
                if (n<3){
                    HttpUtil_SaveData();
                }else{
                    showNormalDialog();//验证码登陆
                }
                n++;
                break;
            case R.id.TV_Forgetword:
                    /*Intent intent1 =new Intent(Activity_Login.this,LoadingActivity.class);
                     startActivity(intent1);  //启动*/
                     Toast.makeText(Activity_Login.this,"如忘记密码请与管理员联系",Toast.LENGTH_SHORT).show();
                break;
            case R.id.TV_Apply:
                     Toast.makeText(Activity_Login.this,"评委你们好,此版本为“发现杯”测试版本，特地为大赛申请了一个账号：faxianbei  密码faxianbei",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //登陆
    private void HttpUtil_SaveData() {
        //读取文本框数据
        Name_id = ET_account.getText().toString().trim();
        Password = ET_password.getText().toString().trim();
        String savePassword = Password;//记录密码
        Password = getMD5(Password);//MD5加密
        //String GET_NEWS_URL="http://iec.d1.natapp.cc/DTCS/settings/index/verifyLabInterface/id/"+Name_id+".html";
        String GET_NEWS_URL= Major.DTCSUrl+"settings/index/verifyLabInterface/id/"+Name_id+".html";
       // String GET_NEWS_URL="http://localhost:81/DTCS/settings/index/verifyLabInterface/id/"+Name_id+".html";
        LitePal.getDatabase();
        //查找用户是否存在
        userList = DataSupport.where("user_id=?",Name_id).find(User.class);
        if (userList.size()>0){
            Log.d("从数据库找到用户", "HttpUtil_SaveData: "+userList.toString());
            User user;
            user = userList.get(0);
            if (user.getPassword().equals(Password)){
                Log.d("密码正确", "HttpUtil_SaveData: "+Password + "  "+user.getPassword());
                rememberPassword(savePassword);
                // 页面跳转
                Intent intent =new Intent(Activity_Login.this,Activity_SignActivity.class);
                intent.putExtra("userDate", Name_id);
                startActivity(intent);  //启动
                finish();
            }else{
                Log.d("密码错误", "HttpUtil_SaveData: "+Password + "  "+user.getPassword());
                Toast.makeText(Activity_Login.this,"密码错误",Toast.LENGTH_SHORT).show();
            }

        }else{
            Log.d("没有从数据库找到用户", "HttpUtil_SaveData: "+Name_id);
            queryFromServer(GET_NEWS_URL,"lognUser");//从网络添加用户
        }


    }
    //AutoLogn自动登陆
    private void autoLogn(){
        Bt_Login.performClick();
    }

    //从服务器查询数据
    private void queryFromServer(String address,final  String type){
        showProgressDialog();//开启加载
        HttpUtil.sendOKHttpRequest(address, new Callback() {//发送请求
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Activity_Login.this,"网络链接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;

                if ("lognUser".equals(type)){
                    if(responseText.length()>2){
                        //找到数据并，添加数据
                        result = Utility.handleUserResqponse(responseText);
                        Log.d("数据responseText", "onResponse: "+responseText);

                    }else{
                        Log.d("账号错误", "onResponse: ");
                       // Toast.makeText(Activity_Login.this,"账号错误",Toast.LENGTH_SHORT).show();
                    }

                }//else if

                if (result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            HttpUtil_SaveData();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            Toast.makeText(Activity_Login.this,"账号错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

        });

    }


    //MD5加密
    public String getMD5(String info) {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++)
            {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }
    //记住登陆成功后密码
    void rememberPassword(String savePassword){
        //登陆成功记录账号密码
        editor = prf.edit();
        editor.putString("account",Name_id);
        editor.putString("password",savePassword);
        editor.apply();
    }
    //打开APP显示密码（记住密码）
    void showPassword(){
        prf = PreferenceManager.getDefaultSharedPreferences(this);
        String account = prf.getString("account","");
        String password = prf.getString("password","");
        Intent intent = getIntent();
        String exit = intent.getStringExtra("onClickExit");

        if (exit!=null){//是否点退出
            ET_account.setText(account);
            ET_password.setText(password);
        }else{
            ET_account.setText(account);
            ET_password.setText(password);
            if (account.length()>1 && password.length()>1 ){
                autoLogn();//自动登陆
            }

        }

    }


    //开启加载
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在读取数据");
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



}

