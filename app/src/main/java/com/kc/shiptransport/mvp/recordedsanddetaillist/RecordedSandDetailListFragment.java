package com.kc.shiptransport.mvp.recordedsanddetaillist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.recordedsanddetail.RecordedSandDetailActivity;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/20  13:19
 * @desc ${TODD}
 */

public class RecordedSandDetailListFragment extends Fragment implements RecordedSandDetailListContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.btn_return)
    Button btnReturn;
    private RecordedSandDetailListContract.Presenter presenter;
    private RecordedSandDetailListActivity activity;
    private RecordedSandDetailListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordedsand_detail_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO 获取过砂记录列表
        presenter.getRecordedList(activity.itemID);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (RecordedSandDetailListActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_recorded_sand_detail_list);

        /** 初始化recycler_view */
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /** 判断是否能添加过砂记录 */
        // TODO: 2017/9/27需求, 以后过砂记录独立出来, 不参与供砂流程, 只提供查看功能
        if (activity.isReadOnly || true) {
            // 只读
            btnReturn.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
        } else {
            // 编辑
            btnReturn.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
        /** 添加过砂记录 */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到填写界面
                RecordedSandDetailActivity.startActivity(getActivity(), activity.itemID, SettingUtil.TYPE_NEW_RECORDED, 0, false);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(RecordedSandDetailListContract.Presenter presenter) {
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
    public void showRecordedList(List<RecordedSandShowList> lists) {
        if (adapter == null) {
            adapter = new RecordedSandDetailListAdapter(getContext(), lists);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    RecordedSandShowList sandShowList = adapter.list.get(position);

                    /** 跳转到编辑界面(修改) */
//                    RecordedSandDetailActivity.startActivity(getActivity(), activity.itemID, SettingUtil.TYPE_UPDATE_RECORDED, sandShowList.getItemID(), activity.isReadOnly);
                    RecordedSandDetailActivity.startActivity(getActivity(), activity.itemID, SettingUtil.TYPE_UPDATE_RECORDED, sandShowList.getItemID(), true);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                    if (activity.isReadOnly) {
                        ToastUtil.tip(getContext(), "已完成过砂, 不能删除数据");
                    } else {
                        final RecordedSandShowList sandShowList = adapter.list.get(position);
                        activity.showDailog("施工船: " + sandShowList.getConstructionShipName(), "是否删除此过砂记录?", "取消", "删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /** 删除 */
                                //presenter.deleteRecorded(activity.itemID, sandShowList.getItemID());
                            }
                        });
                    }

                }
            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDates(lists);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            presenter.getRecordedList(activity.itemID);
        }
    }
}
