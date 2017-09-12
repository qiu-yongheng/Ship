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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.InputActivity;

import java.text.ParseException;

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
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    private VoyageDetailActivity activity;
    private VoyageDetailContract.Presenter presenter;
    private VoyageDetailAdapter adapter;
    private boolean save = true;
    private boolean isChange = true;
    private VoyageDetailBean bean;

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

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

//    /**
//     * 加载menu菜单
//     *
//     * @param menu
//     * @param inflater
//     */
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.voyage_detail_menu, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveData();
                break;
            case R.id.menu_view_comments:
                String remark = "";
                if (bean != null && activity != null) {
                    remark = TextUtils.isEmpty(bean.getPreAcceptanceEvaluationRemark()) ? "暂无退回意见" : bean.getPreAcceptanceEvaluationRemark();
                }

                activity.showDailog("退回意见", remark, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存数据
     */
    private void saveData() {
        if (save) {
            // 已保存
            getActivity().onBackPressed();
        } else {
            // 未保存, 提示保存
            activity.showDailog("保存数据", "数据还未保存, 是否离开此页面?", "离开不保存", "保存数据", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // cancel
                    getActivity().onBackPressed();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // ok
                    if (adapter != null && adapter.bean != null) {
                        save = true;
                        adapter.bean.setIsSumbitted("0");
                        presenter.commit(adapter.bean);
                    }
                }
            });
        }
    }


    @Override
    public void initListener() {
        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showDailog("提交", "提交后, 不能再修改数据, 是否提交?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for (VoyageDetailBean.ColumnsBean columnsBean : adapter.list) {
                            if (TextUtils.isEmpty(columnsBean.getValue())) {
                                if ("ReadOnly".equals(columnsBean.getControlType())) {
                                    continue;
                                }

                                ToastUtil.tip(getContext(), "还有数据未填写, 不能提交!");
                                return;
                            }
                        }

                        adapter.bean.setIsSumbitted("1");
                        presenter.commit(adapter.bean);
                    }
                });
            }
        });

        // 保存数据到缓存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter != null && adapter.bean != null) {
                    save = true;
                    adapter.bean.setIsSumbitted("0");
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

        // 查看退回意见
        tvOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remark = "";
                if (bean != null && activity != null) {
                    remark = TextUtils.isEmpty(bean.getPreAcceptanceEvaluationRemark()) ? "暂无退回意见" : bean.getPreAcceptanceEvaluationRemark();
                }

                activity.showDailog("退回意见", remark, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
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
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private void getFocus() {
        if (getView() != null) {
            getView().setFocusable(true);
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                        // 监听到返回按钮点击事件
                        saveData();
                        return true;// 未处理
                    }
                    return false;
                }
            });
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
        if (bean == null) {
            ToastUtil.tip(getContext(), "获取数据失败");
            return;
        }

        this.bean = bean;

        if (bean.getPreAcceptanceEvaluationStatus() == -1) {
            tvOpinion.setVisibility(View.VISIBLE);
        } else {
            tvOpinion.setVisibility(View.GONE);
        }




        // 1代表已提交，0代表保存
        String isSumbitted = bean.getIsSumbitted();
        try {
            if (Integer.valueOf(isSumbitted) == 1 || activity.type == 1) {
                // 已提交 或 查看模式
                btnReturn.setVisibility(View.VISIBLE);
                llBtn.setVisibility(View.GONE);
                isChange = false;
            } else if (Integer.valueOf(isSumbitted) == 0 || activity.type == 2) {
                // 保存
                btnReturn.setVisibility(View.GONE);
                llBtn.setVisibility(View.VISIBLE);
                isChange = true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            if (activity.type == 1) {
                btnReturn.setVisibility(View.VISIBLE);
                llBtn.setVisibility(View.GONE);
                isChange = false;
            } else {
                // 默认保存
                btnReturn.setVisibility(View.GONE);
                llBtn.setVisibility(View.VISIBLE);
                isChange = true;
            }
        }


        if (adapter == null) {
            adapter = new VoyageDetailAdapter(getContext(), bean);

            // 判断是否可以进行修改
            if (isChange) {
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
                                            save = false;
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
            }


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
        // 这里有时会报空指针
        if (adapter == null || adapter.list == null) {
            return;
        }
        VoyageDetailBean.ColumnsBean columnsBean = adapter.list.get(requestCode);

        if (data != null) {
            save = false;
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
