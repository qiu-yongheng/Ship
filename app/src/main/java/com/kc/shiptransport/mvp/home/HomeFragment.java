package com.kc.shiptransport.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.main.MainActivity;
import com.kc.shiptransport.view.pop.HomeItemPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:37
 * @desc ${TODO}
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.toolbar_home)
    Toolbar toolbarHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private Integer[] icon = new Integer[]{R.mipmap.map, R.mipmap.plan, R.mipmap.log, R.mipmap.supply_sand, R.mipmap.save, R.mipmap.data, R.mipmap.msg, R.mipmap.ship_monitor, R.mipmap.acceptance};
    private String[] tag = new String[]{"电子地图", "供砂管理", "施工记录", "施工照片", "安全管理", "数据分析"
            , "生产指令", "航线管理", "通讯录"};
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        rvHome.setHasFixedSize(true);
        rvHome.setLayoutManager(new GridLayoutManager(getContext(), 3));
        HomeAdapter adapter = new HomeAdapter(getContext(), icon, tag);
        adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int... type) {
                switch (position) {
                    case 0:
                        tip("功能待开发");
                        break;
                    case 1:
                        HomeItemPop pop = HomeItemPop.getInstanst(getContext());
                        pop.onShow(view);
                        break;
                    case 2:
                        tip("功能待开发");
                        break;
                    case 3:
                        tip("功能待开发");
                        break;
                    case 4:
                        tip("功能待开发");
                        break;
                    case 5:
                        tip("功能待开发");
                        break;
                    case 6:
                        tip("功能待开发");
                        break;
                    case 7:
                        tip("功能待开发");
                        break;
                    case 8:
                        tip("功能待开发");
                        break;

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rvHome.setAdapter(adapter);

        activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbarHome);



    }

    private void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void tip(String tip) {
        Toast.makeText(activity, tip, Toast.LENGTH_SHORT).show();
    }
}
