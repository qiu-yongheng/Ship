package com.kc.shiptransport.mvp.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:37
 * @desc ${TODO}
 */

public class MineFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }


}
