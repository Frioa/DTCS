package com.example.dtcs.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.dtcs.R;
import com.example.dtcs.db.ExperimentUser;

import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.dtcs.Enum.Major.DTCSUrl;

/**
 * Created by q9163 on 26/12/2017.
 */

public class UserAdapter extends ArrayAdapter<ExperimentUser> {
    private int resourceID;
    private Context context;
    private List<ExperimentUser> apk_list;


    private SharedPreferences prf;

    public UserAdapter(Context context, int textviewresourceId, List<ExperimentUser> objects) {
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
            viewHoldel.userHead = (ImageView) view.findViewById(R.id.IV_itemHead0);
            viewHoldel.userIcon = (ImageView) view.findViewById(R.id.IV_itemIcon0);
            viewHoldel.userName = (TextView) view.findViewById(R.id.TV_itemUserName0);
            viewHoldel.userPrank = (TextView) view.findViewById(R.id.TV_itemPrank0);
            viewHoldel.userNumber = (TextView) view.findViewById(R.id.TV_itemNumber0);
            viewHoldel.userYear = (TextView) view.findViewById(R.id.TV_itemYear0);
            viewHoldel.userRemark = (TextView) view.findViewById(R.id.TV_itemRemark0);
            viewHoldel.userDepary = (TextView) view.findViewById(R.id.TV_itemDepart0);
            view.setTag(viewHoldel);
        }else{
            view = convertView;
            viewHoldel = (ViewHoldel) view.getTag();
        }

        ExperimentUser user = getItem(position);
        final String headPortraitURL=DTCSUrl+"Public/img/usrimg/"+user.getUser_id()+".jpg";//头像

        viewHoldel.userName.setText(user.getName());
        viewHoldel.userNumber.setText(user.getUser_id());
        viewHoldel.userPrank.setText("  研究员  ");
        viewHoldel.userIcon.setImageResource(R.mipmap.function_s);
        if (user.getRank()==2){
            viewHoldel.userPrank.setText("  实验员  ");
            viewHoldel.userIcon.setImageResource(R.mipmap.function_r);
        }else if (user.getRank()==1){
            viewHoldel.userPrank.setText("  管理员  ");
            viewHoldel.userIcon.setImageResource(R.mipmap.function_aa);
        }
        viewHoldel.userYear.setText(user.getGrade()+"");

        String sMajor = user.getMajornum();

        if (!sMajor.equals("")){
            for(  com.example.dtcs.Enum.Major major : com.example.dtcs.Enum.Major.values()){
                if (sMajor.equals(major.getName()) ){
                    sMajor = major.getDepart();
                    break;
                }
            }
        }

        viewHoldel.userDepary.setText(sMajor);
        //设置状态
        if (user.getRemark().equals("null") || user.getRemark().equals("")){
            //TV03_PRemark.setBackgroundColor(0xff000000);
            viewHoldel.userRemark.setBackground(context.getResources().getDrawable(R.drawable.button_dayborde));
        }else{
            viewHoldel.userRemark.setText("  "+user.getRemark()+"  ");
            viewHoldel.userRemark.setBackground(context.getResources().getDrawable(R.drawable.textview_tab03borde));

        }//R.drawable.textview_tab03borde

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("headPortrait"+user.getUser_id(),headPortraitURL);
        editor.apply();
        prf = PreferenceManager.getDefaultSharedPreferences(context);

        //自动保存图片
        if (prf.getString("headPortrait"+user.getUser_id(),null) != null){
            Glide.with(context).load(prf.getString("headPortrait"+user.getUser_id(),null)).into(viewHoldel.userHead);    //头像
            Log.d("实验员显示头像", "本地头像 "+user.getUser_id());
        }else{
            String userId = user.getUser_id();
            Glide.with(context).load(headPortraitURL).into(viewHoldel.userHead);
            Log.d("显示头像", "网络下载头像 ");
        }
        Glide.with(context).load(headPortraitURL).into(viewHoldel.userHead);


        return view;
    }
    class ViewHoldel{//ListView 缓存
        ImageView userHead ;
        ImageView userIcon ;
        TextView userName ;
        TextView userPrank ;
        TextView userRemark ;
        TextView userNumber ;
        TextView userYear ;
        TextView userDepary;
    }

    public void onDateChange(List<ExperimentUser> users){
        this.apk_list=users;
        Log.d("刷新的数据", "onReflash: "+apk_list.toString());
        Log.d("数据加载", "onDateChange: ");
        this.notifyDataSetChanged();
    }


}
