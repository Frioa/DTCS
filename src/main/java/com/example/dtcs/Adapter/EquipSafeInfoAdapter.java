package com.example.dtcs.Adapter;


import android.content.Context;
import java.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dtcs.R;
import com.example.dtcs.db.Chk;

import java.util.List;

/**
 * Created by q9163 on 26/12/2017.
 */

public class EquipSafeInfoAdapter extends ArrayAdapter<Chk> {
    private int resourceID;
    private Context context;

    public EquipSafeInfoAdapter(Context context, int textviewresourceId, List<Chk> objects) {
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
            viewHoldel.equipSafeName = (TextView) view.findViewById(R.id.item_equipsafeName);
            viewHoldel.equipSafe1 = (TextView) view.findViewById(R.id.item_equipsafe1);
            viewHoldel.equipSafe0 = (TextView) view.findViewById(R.id.item_equipsafe0);
            viewHoldel.equipSafeDate = (TextView) view.findViewById(R.id.item_equipsafeDate);
            view.setTag(viewHoldel);
        }else{
            view = convertView;
            viewHoldel = (ViewHoldel) view.getTag();
        }

        Chk chk = getItem(position);

        viewHoldel.equipSafeName.setText(chk.getName());



        if (chk.getChka()==1 && chk.getChkb()==1 && chk.getChkc()==1 && chk.getChkd()==1 && chk.getChke()==1 && chk.getChkf()==1 && chk.getChkg()==1){
            Log.d("true", "getView: ");
            viewHoldel.equipSafe1.setText(" 合格 ");
            viewHoldel.equipSafe0.setText("");
        }else {
            Log.d("false", "getView: ");
            viewHoldel.equipSafe1.setText("");
            viewHoldel.equipSafe0.setText(" 不合格 ");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(chk.getCdate());

        viewHoldel.equipSafeDate.setText("检查时间:"+date);

        return view;
    }
    class ViewHoldel{//ListView 缓存
        TextView equipSafeName ;
        TextView equipSafeDate ;
        TextView equipSafe1;
        TextView equipSafe0 ;

    }

}
