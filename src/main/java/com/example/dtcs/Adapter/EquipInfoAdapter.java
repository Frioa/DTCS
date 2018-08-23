package com.example.dtcs.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dtcs.R;
import com.example.dtcs.db.Equipment;

import java.util.List;

/**
 * Created by q9163 on 26/12/2017.
 */

public class EquipInfoAdapter extends ArrayAdapter<Equipment> {
    private int resourceID;
    private Context context;

    public EquipInfoAdapter(Context context, int textviewresourceId, List<Equipment> objects) {
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
            viewHoldel.equipName = (TextView) view.findViewById(R.id.TV_equititemName);
            //viewHoldel.equipType = (TextView) view.findViewById(R.id.TV_equititemDesript);
            viewHoldel.equipDate = (TextView) view.findViewById(R.id.TV_equititemDate);
            viewHoldel.equipStatus = (TextView) view.findViewById(R.id.TV_equititemDesript);
            viewHoldel.equipVersion = (TextView) view.findViewById(R.id.TV_equititemVersion);
            viewHoldel.equipNum = (TextView) view.findViewById(R.id.TV_equititemNUM);

            view.setTag(viewHoldel);
        }else{
            view = convertView;
            viewHoldel = (ViewHoldel) view.getTag();
        }

        Equipment Eq = getItem(position);

        viewHoldel.equipName.setText(Eq.getName());
        viewHoldel.equipNum.setText("数量:"+Eq.getCount());
        if (!Eq.getVersion().equals("0")){
            viewHoldel.equipVersion.setText("版本:"+Eq.getVersion());
        }else{
            viewHoldel.equipVersion.setText("");
        }
        if (Eq.getStatus()==0){
            viewHoldel.equipStatus.setText("损坏");
            viewHoldel.equipStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        if (Eq.getStatus()==1){
            viewHoldel.equipStatus.setText("正常");
            viewHoldel.equipStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
        if (Eq.getStatus()==2){
            viewHoldel.equipStatus.setText("故障");
            viewHoldel.equipStatus.setTextColor(context.getResources().getColor(R.color.colorGrayFonts));
        }

        return view;
    }
    class ViewHoldel{//ListView 缓存
        TextView equipName ;
        TextView equipType ;
        TextView equipVersion ;
        TextView equipDate;
        TextView equipStatus ;
        TextView equipNum ;
    }

}
