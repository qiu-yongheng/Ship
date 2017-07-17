package com.kc.shiptransport.mvp.partition;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/7  10:20
 * @desc ${TODD}
 */

public class PartitionFragment extends Fragment implements PartitionContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    @BindView(R.id.btn_return)
    Button btnReturn;
    private PartitionActivity activity;
    private PartitionAdapter adapter;
    private PartitionContract.Presenter presenter;
    private ConstructionBoat boat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partition, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // 设置当前施工船舶
        List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
        int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
        boat = all.get(position - 1);

        presenter.getList(boat.getShipNum());
        return view;
    }

    @Override
    public void initViews(View view) {
        // 设置标题
        tvTitle.setText(R.string.title_partition);

        activity = (PartitionActivity) getActivity();


        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {
        /** 新增分区 */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.add(adapter.list.size());
                recyclerview.smoothScrollToPosition(adapter.list.size());
            }
        });

        /** 返回 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void setPresenter(PartitionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
                @Override
                public void onCancle(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showList(List<PartitionNum> list) {
        if (adapter == null) {
            adapter = new PartitionAdapter(getActivity(), list);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {

                }

                @Override
                public void onItemLongClick(View view, final int position) {
                    activity.showDailog("删除", "是否删除该抛砂分区", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 删除数据
                            adapter.delete(position);
                        }
                    });
                }
            });

            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }
}