package com.kc.shiptransport.mvp.shipselect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Ship;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/5/18  22:47
 * @desc ${TODD}
 */

public class ShipSelectFragment extends Fragment implements ShipSelectContract.View {
    @BindView(R.id.toolbar_ship_select)
    Toolbar toolbarShipSelect;
    @BindView(R.id.recyclerview_ship_select)
    RecyclerView recyclerviewShipSelect;
    @BindView(R.id.btn_ship_cancel)
    AppCompatButton btnShipCancel;
    @BindView(R.id.btn_ship_commit)
    AppCompatButton btnShipCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    Unbinder unbinder;
    private ShipSelectActivity activity;
    private ShipSelectContract.Presenter presenter;
    private ShipSelectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ship_select, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        presenter.getShip(activity.currentSelectShipType);
        return view;
    }

    private void initListener() {
        btnShipCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doCancle(activity.currentSelectShipType);
            }
        });

        btnShipCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doCommit();
            }
        });
    }

    @Override
    public void initViews(View view) {
        activity = (ShipSelectActivity)getActivity();
        setHasOptionsMenu(true);
        activity.setSupportActionBar(toolbarShipSelect);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("新增船舶");

        recyclerviewShipSelect.setLayoutManager(new GridLayoutManager(activity, 2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ShipSelectContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showShip(List<Ship> value) {
        if (adapter == null) {
            adapter = new ShipSelectAdapter(getActivity(), value);
            recyclerviewShipSelect.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancle() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void commit() {
        activity.onBackPressed();
    }
}
