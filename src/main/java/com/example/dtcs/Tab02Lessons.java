package com.example.dtcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dtcs.Adapter.LessonAdapter;
import com.example.dtcs.db.Lesson;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by q9163 on 13/02/2018.
 */

public class Tab02Lessons extends AppCompatActivity implements View.OnClickListener {
    private ImageView BackArrow;
    private List<Lesson> lessons1,lessons0;
    private ListView listview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab02lessons);
        init();
    }

    private void init() {
        BackArrow = (ImageView) findViewById(R.id.IV_tab02Return3s);
        listview = (ListView) findViewById(R.id.LV_LessonsListView);
        lessons1 = DataSupport.where("status = ?","1").find(Lesson.class);
        lessons0 = DataSupport.where("status = ?","0").find(Lesson.class);
        for (int i = 0 ;i<lessons0.size();i++){
            lessons1.add(lessons0.get(i));
        }
        Log.d("上课列表", "init: "+lessons1.toString());
        Log.d("结课列表", "init: "+lessons0.toString());
        LessonAdapter lessonAdapter = new LessonAdapter(Tab02Lessons.this,R.layout.item_lesson,lessons1);
        listview.setAdapter(lessonAdapter);
        BackArrow.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.IV_tab02Return3s:
                finish();
                break;
            case R.id.LL_EquipmentInformation:

        }
    }
}
