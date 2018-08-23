package com.example.dtcs.MyFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dtcs.R;

/**
 * Created by q9163 on 2017/8/16.
 */

public class MyFragment2 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_function = inflater.inflate(R.layout.tab02function,container,false);
        return view_function;
    }
}
