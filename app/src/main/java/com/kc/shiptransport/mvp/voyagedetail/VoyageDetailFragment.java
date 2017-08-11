package com.kc.shiptransport.mvp.voyagedetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.view.actiivty.InputActivity;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:14
 * @desc ${TODD}
 */

public class VoyageDetailFragment extends Fragment implements VoyageDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    private VoyageDetailActivity activity;
    private VoyageDetailContract.Presenter presenter;
    private VoyageDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voyage_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO 获取要显示的列表
        presenter.getVoyageDates(activity.mPosition, activity.type);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (VoyageDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_voyage);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        WeekTask weekTask = null;
        if (activity.type == 1) {
            // 查看数据
            List<WeekTask> list = DataSupport.where("ItemID = ?", String.valueOf(activity.mPosition)).find(WeekTask.class);
            if (!list.isEmpty()) {
                weekTask = list.get(0);
            }
        } else {
            // 编辑数据
            weekTask = DataSupport.where("position = ?", String.valueOf(activity.mPosition)).find(WeekTask.class).get(0);
        }

        // 根据type, 初始化不同的控件
        if (activity.type == 0 && weekTask != null && TextUtils.isEmpty(weekTask.getPreAcceptanceTime())) {
            // 提交数据 (编辑模式, 并且没有验砂)
            btnCommit.setVisibility(View.VISIBLE);
        } else if (activity.type == 1 || (weekTask != null && !TextUtils.isEmpty(weekTask.getPreAcceptanceTime()))) {
            // 查看数据, 不可修改 (只读模式, 或者已经验砂)
            btnCommit.setVisibility(View.GONE);
            btnReturn.setVisibility(View.VISIBLE);
        }
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
    public void initListener() {
        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                if (TextUtils.isEmpty(adapter.bean.getWashStoreAddress())) {
                    activity.showDailog("提交", "没有填写洗石场所在地, 是否提交?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.commit(adapter.bean);
                        }
                    });
                } else {
                    presenter.commit(adapter.bean);
                }
            }
        });

        // 返回
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void setPresenter(VoyageDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示获取的数据
     *
     * @param bean
     */
    @Override
    public void showVoyageDates(VoyageDetailBean bean) {
        if (adapter == null) {
            adapter = new VoyageDetailAdapter(getContext(), bean);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    final VoyageDetailBean.ColumnsBean columnsBean = adapter.list.get(position);
                    switch (type[0]) {
                        case SettingUtil.TYPE_TEXT:
                            // 文本
                            InputActivity.startActivityForResult(getActivity(), columnsBean.getLabel(), columnsBean.getValue(), SettingUtil.TYPE_TEXT, position);
                            break;
                        case SettingUtil.TYPE_DATA:
                            // 时间
                            try {
                                CalendarUtil.showTimePickerDialog(getContext(), new OnTimePickerSureClickListener() {
                                    @Override
                                    public void onSure(String str) {
                                        // 保存数据
                                        columnsBean.setValue(str);
                                        adapter.notifyDataSetChanged();
                                    }
                                }, false);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case SettingUtil.TYPE_ARRAY:
                            // 数组
                            String arr = new Gson().toJson(columnsBean.getArr());
                            VoyageListActivity.startActivityForResult(getActivity(), arr, position);
                            break;
                        case SettingUtil.TYPE_READ_ONLY:
                            // 只读
                            Toast.makeText(getContext(), "不可更改", Toast.LENGTH_SHORT).show();
                            break;
                        case SettingUtil.TYPE_NUMBER:
                            // 只能输入数字
                            InputActivity.startActivityForResult(getActivity(), columnsBean.getLabel(), columnsBean.getValue(), SettingUtil.TYPE_NUMBER, position);
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(bean);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VoyageDetailBean.ColumnsBean columnsBean = adapter.list.get(requestCode);

        if (data != null) {
            switch (resultCode) {
                case 0:
                    // 文本
                    String str = data.getStringExtra(InputActivity.TAG);
                    columnsBean.setValue(str);
                    break;
                case SettingUtil.TYPE_ARRAY:
                    Bundle bundle = data.getExtras();
                    // 集合
                    String itemID = bundle.getString(VoyageListActivity.NUM);
                    String name = bundle.getString(VoyageListActivity.NAME);

                    // 保存数据
                    columnsBean.setValue(name + ";" + itemID);
                    columnsBean.setData(itemID);
                    adapter.bean.setWashStoreAddress(name);
                    break;
            }

            // 刷新数据
            adapter.notifyDataSetChanged();
        }

    }
}
