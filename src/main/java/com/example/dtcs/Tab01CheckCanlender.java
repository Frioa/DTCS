package com.example.dtcs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.dtcs.db.SignLogin;
import com.test.sign_calender.DPCManager;
import com.test.sign_calender.DPDecor;
import com.test.sign_calender.DatePicker;
import com.test.sign_calender.DatePicker2;

import org.litepal.crud.DataSupport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by q9163 on 02/01/2018.
 */
@SuppressWarnings("ResourceType")
public class Tab01CheckCanlender extends AppCompatActivity implements View.OnClickListener{

    private View header;
    private TextView headerText ,TV_SignDate1;
    private ImageView BreakArrow;

    private List<SignLogin> signLoginLists;

    String userID = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab01checkcanlender);
        init();
    }

    void init() {
        Intent intent = getIntent();
        String SignDate = intent.getStringExtra("SignDate")+"";
        userID = intent.getStringExtra("userID")+"";

        header = findViewById(R.id.CheckCanlender_include);
        headerText = (TextView) header.findViewById(R.id.paging_headTextView);
        TV_SignDate1 = (TextView) findViewById(R.id.TV_SignDate1);
        BreakArrow = (ImageView) header.findViewById(R.id.paging_headReturn);

        headerText.setText("签到日历");
        TV_SignDate1.setText(SignDate);
        BreakArrow.setOnClickListener(this);

        myCalendar();
    }

    private void myCalendar() {
        Calendar c = Calendar.getInstance();//首先要获取日历对象
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        final int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        List<String> tmp = new ArrayList<>();

        signLoginLists= DataSupport.where("user_id=?",userID).find(SignLogin.class);
        //Log.d("签到表", "myCalendar: "+signLoginLists.toString());

        for (int i = 0;i<signLoginLists.size();i++){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
            String date = format.format(signLoginLists.get(i).getDate());
            Log.d(userID+"签到", "refilashComplete1: "+date);
            tmp.add(date);
        }


        Log.d("tmp", "myCalendar: "+tmp);
        tmp.add("2018-1-22");//今天
        tmp.add(mYear+"-"+mMonth+"-"+mDay);//今天
        DPCManager.getInstance().setDecorBG(tmp);

        DatePicker2 picker = (DatePicker2) findViewById(R.id.DatePicker1);

        picker.setFestivalDisplay(false); //是否显示节日
        picker.setHolidayDisplay(true); //是否显示假期
        picker.setDeferredDisplay(true); //是否显示补休

        picker.setDate(mYear, mMonth);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.BLUE);
                //                paint.setStyle(Paint.Style.STROKE);
                //                paint.setTextAlign(Paint.Align.CENTER);
                //                paint.setTextSize(16);
                paint.setAntiAlias(true);
                InputStream is = getResources().openRawResource(R.mipmap.circle_bule2);
                Bitmap mBitmap = BitmapFactory.decodeStream(is);
                Log.d("x y", "drawDecorBG: "+rect.centerX()+" "+rect.centerY());
                canvas.drawBitmap(mBitmap, rect.centerX() - mBitmap.getWidth() / 2f, rect.centerY() - mBitmap.getHeight() / 2f, paint);

            }
        });
        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                String result = "";
                Iterator iterator = date.iterator();
                while (iterator.hasNext()) {
                    result += iterator.next();
                    if (iterator.hasNext()) {
                        result += "\n";
                    }
                }
                Toast.makeText(Tab01CheckCanlender.this, result, Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.paging_headReturn:
                finish();
            break;
        }
    }
}
