package com.kc.shiptransport.mvp.partition;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/7  10:20
 * @desc 施工分区
 */

public class PartitionFragment extends Fragment implements PartitionContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_prefix)
    EditText etPrefix;
    @BindView(R.id.et_start_num)
    EditText etStartNum;
    @BindView(R.id.et_end_num)
    EditText etEndNum;
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

        presenter.getList(boat.getShipNum());
        return view;
    }

    @Override
    public void initViews(View view) {
        // 设置标题
        setHasOptionsMenu(true);
        activity = (PartitionActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_partition);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        String prefix = SharePreferenceUtil.getString(getContext(), SettingUtil.SP_KEY_PREFIX, "");
        if (!TextUtils.isEmpty(prefix)) {
            etPrefix.setText(prefix);
        }

        // 设置当前施工船舶
        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
            int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
            boat = all.get(position - 1);
        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String shipAccount = lists.get(0).getShipAccount();

                List<ConstructionBoat> boats = DataSupport.where("ShipNum = ?", shipAccount).find(ConstructionBoat.class);
                if (!boats.isEmpty()) {
                    boat = boats.get(0);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.partition, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_delete_all:
                activity.showDailog("删除所有分区", "确定要删除所有分区吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 删除全部分区
                        DataSupport.deleteAll(PartitionNum.class, "userAccount = ?", boat.getShipNum());
                        if (adapter != null) {
                            adapter.setDates(new ArrayList<PartitionNum>());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
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

        /** 生成 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String prefix = etPrefix.getText().toString().trim();
                final String startNum = etStartNum.getText().toString().trim();
                final String endNum = etEndNum.getText().toString().trim();

                if (TextUtils.isEmpty(prefix)) {
                    ToastUtil.tip(getContext(), "请填写分区前缀");
                } else if (TextUtils.isEmpty(startNum)) {
                    ToastUtil.tip(getContext(), "请填写开始数");
                } else if (TextUtils.isEmpty(endNum)) {
                    ToastUtil.tip(getContext(), "请填写结束数");
                } else {
                    activity.showDailog("施工分区生成", "是否一键生成施工分区", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                Integer start = Integer.valueOf(startNum);
                                Integer end = Integer.valueOf(endNum);

                                // 缓存分区前缀, 开始数, 结束数
                                SharePreferenceUtil.saveString(getContext(), SettingUtil.SP_KEY_PREFIX, prefix);

                                if (start > end) {
                                    ToastUtil.tip(getContext(), "开始数不能大于结束数");
                                    return;
                                }

                                for (int j = start; j <= end; j++) {
                                    // 创建一个新的数据, 保存用户名
                                    PartitionNum num = new PartitionNum();
                                    num.setUserAccount(boat.getShipNum());
                                    String format = String.format("%03d", j);
                                    num.setNum(prefix + format);
                                    num.save();
                                }

                                List<PartitionNum> numList = DataSupport.where("userAccount = ? and num is not null and num != ?", boat.getShipNum(), "").find(PartitionNum.class);
                                adapter.setDates(numList);
                                adapter.notifyDataSetChanged();

                                // 如果输入法在窗口上已经显示，则隐藏，反之则显示
                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                ToastUtil.tip(getContext(), "开始数与结束数必须输入数字");
                            }


                        }
                    });
                }

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
            adapter = new PartitionAdapter(getActivity(), list, boat.getShipNum());
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
