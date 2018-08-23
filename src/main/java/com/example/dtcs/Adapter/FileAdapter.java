package com.example.dtcs.Adapter;

import android.content.Context;
import java.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dtcs.R;
import com.example.dtcs.db.File;

import java.util.List;

/**
 * Created by q9163 on 26/12/2017.
 */

public class FileAdapter extends ArrayAdapter<File> {
    private int resourceID;
    private Context context;


    public FileAdapter(Context context, int textviewresourceId, List<File> objects) {
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
            viewHoldel.fileHead = (ImageView) view.findViewById(R.id.IV_fileitemHead);
            viewHoldel.fileName = (TextView) view.findViewById(R.id.TV_fileitemUserName);
            viewHoldel.fileDescript = (TextView) view.findViewById(R.id.TV_fileitemDesript);
            viewHoldel.fileID = (TextView) view.findViewById(R.id.TV_fileitemID);
            viewHoldel.fileDate = (TextView) view.findViewById(R.id.TV_flieitemDate);
            viewHoldel.fileSize = (TextView) view.findViewById(R.id.TV_flieitemSize);

            view.setTag(viewHoldel);
        }else{
            view = convertView;
            viewHoldel = (ViewHoldel) view.getTag();
        }

        File file = getItem(position);
        viewHoldel.fileHead.setImageResource(R.mipmap.ic_launcher);

        viewHoldel.fileDescript.setText(file.getFdescript()+"");
       //Log.d("描述", "getView: "+file.getFdescript());
        viewHoldel.fileName.setText(file.getFname()+"");
        viewHoldel.fileID.setText("ID: "+file.getFid());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(file.getFdate());
        String Date = file.getPid() +" "  +" 上传";
       // String Date = "由 "+file.getPid() +" " + format.format(file.getFdate()) +" 上传";
        viewHoldel.fileDate.setText(Date);

        viewHoldel.fileSize.setText(String.format("%.2f", (file.getSize()/1024))+"MB");
        return view;
        }

    class ViewHoldel{//ListView 缓存
        ImageView fileHead ;
        TextView fileName ;
        TextView fileDescript;
        TextView fileID ;
        TextView fileDate ;
        TextView fileSize ;
    }


}
