package com.kc.shiptransport.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.user.User;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/22  11:06
 * @desc ${TODD}
 */

public abstract class BaseFragment<V extends AppCompatActivity> extends Fragment{
    public V activity;
    Toolbar toolbar;
    ViewFlipper layoutContainer;
    public String creator;
    public int pageSize = 20;
    public int pageCount = 1;
    public String creatorName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        layoutContainer = (ViewFlipper) view.findViewById(R.id.layout_container);
        setContentView();
        return view;
    }

    public void setContentView() {
        View view = LayoutInflater.from(getContext()).inflate(setView(), null, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutContainer.addView(view, lp);
        initViews();
    }

    public abstract int setView();

    public void initViews() {
        setHasOptionsMenu(true);
        activity = (V) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(setTitle());

        List<User> users = DataSupport.findAll(User.class);
        creator = users.isEmpty() ? "" : users.get(0).getUserID();
        creatorName = users.isEmpty() ? "" : users.get(0).getUserName();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract int setTitle();
}
