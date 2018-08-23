package com.example.dtcs.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dtcs.R;
import com.example.dtcs.db.Competition;


import java.util.List;

/**
 * Created by q9163 on 26/12/2017.
 */

public class CompetAdapter extends ArrayAdapter<Competition> {
    private int resourceID;
    private Context context;

    public CompetAdapter(Context context, int textviewresourceId, List<Competition> objects) {
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
            viewHoldel.competID = (TextView) view.findViewById(R.id.TV_competitemID);
            viewHoldel.competName = (TextView) view.findViewById(R.id.TV_competitemName);
            viewHoldel.competDepartment = (TextView) view.findViewById(R.id.TV_competitemDepartment);
            viewHoldel.competSymbol = (TextView) view.findViewById(R.id.TV_competitemSymbol);
            viewHoldel.competGrade = (TextView) view.findViewById(R.id.TV_competitemGrade);
            viewHoldel.competMajor = (TextView) view.findViewById(R.id.TV_competitemMajor);
            view.setTag(viewHoldel);
        }else{
            view = convertView;
            viewHoldel = (ViewHoldel) view.getTag();
        }
        Competition competition = getItem(position);
        viewHoldel.competID.setText("("+competition.getUserid()+") ");
        viewHoldel.competName.setText(competition.getName());
        viewHoldel.competSymbol.setText("批次:"+competition.getSymbol());
        viewHoldel.competMajor.setText(competition.getMajor()+ " " + competition.getClass_()+"班");
        viewHoldel.competGrade.setText("  成绩:"+competition.getGrade()+"  ");

        return view;
    }
    class ViewHoldel{//ListView 缓存
        TextView competName ;
        TextView competID ;
        TextView competDepartment ;
        TextView competMajor ;
        TextView competGrade;
        TextView competSymbol;
    }

}
