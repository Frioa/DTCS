package com.example.dtcs.Adapter;


import android.content.Context;
import java.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dtcs.R;
import com.example.dtcs.db.Lesson;

import java.util.List;

/**
 * Created by q9163 on 26/12/2017.
 */

public class LessonAdapter extends ArrayAdapter<Lesson> {
    private int resourceID;
    private Context context;

    public LessonAdapter(Context context, int textviewresourceId, List<Lesson> objects) {
        super(context, textviewresourceId, objects);
        resourceID = textviewresourceId;
        this.context = context;
    }

    @NonNull
    @Override//ListView之文艺式啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHoldel viewHoldel;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            viewHoldel = new ViewHoldel();
            viewHoldel.lessonID = (TextView) view.findViewById(R.id.TV_lessonitemID);
            viewHoldel.lessonName = (TextView) view.findViewById(R.id.TV_lessonitemName);
            viewHoldel.lessonLongs = (TextView) view.findViewById(R.id.TV_lessonitemLongs);
            viewHoldel.lessonSdate = (TextView) view.findViewById(R.id.TV_lessonitemDate);
            viewHoldel.lessonType = (TextView) view.findViewById(R.id.TV_lessonitemType);
            viewHoldel.lessonStatus0 = (TextView) view.findViewById(R.id.TV_lessontitemStaus0);
            viewHoldel.lessonStatus1 = (TextView) view.findViewById(R.id.TV_lessontitemStaus1);
            viewHoldel.lessonteach = (TextView) view.findViewById(R.id.TV_lessontitemteach);
            viewHoldel.lessonpoint = (TextView) view.findViewById(R.id.TV_lessonitempoint);

            view.setTag(viewHoldel);
        }else{
            view = convertView;
            viewHoldel = (ViewHoldel) view.getTag();
        }

        Lesson lesson = getItem(position);

        viewHoldel.lessonName.setText(lesson.getName());
        viewHoldel.lessonID.setText("ID:"+lesson.getLessonID());
        viewHoldel.lessonLongs.setText("课时："+lesson.getLongs());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(lesson.getSdata());
        viewHoldel.lessonSdate.setText("开课时间:"+date);

        if (lesson.getStatus()==1){
            viewHoldel.lessonStatus1.setText(" 开课 ");
            viewHoldel.lessonStatus0.setText("");
            viewHoldel.lessonType.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            viewHoldel.lessonStatus1.setText("");
            viewHoldel.lessonStatus0.setText(" 结课 ");
            viewHoldel.lessonType.setTextColor(context.getResources().getColor(R.color.red));
        }
        viewHoldel.lessonteach.setText(lesson.getTaech());
        viewHoldel.lessonpoint.setText( "绩点"+lesson.getPoint());

        switch (lesson.getType()){
            case 1:
                viewHoldel.lessonType.setText("硬件课");
                break;
            case 2:
                viewHoldel.lessonType.setText("软件课");
                break;
            case 3:
                viewHoldel.lessonType.setText("实践课");
                break;
            case 4:
                viewHoldel.lessonType.setText("辅助课");
                break;
        }


        return view;
    }
    class ViewHoldel{//ListView 缓存
        TextView lessonID ;
        TextView lessonName ;
        TextView lessonLongs ;
        TextView lessonSdate ;
        TextView lessonType ;
        TextView lessonStatus0;
        TextView lessonStatus1;
        TextView lessonteach ;
        TextView lessonpoint ;
    }

}
