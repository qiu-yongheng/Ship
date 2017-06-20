package com.kc.shiptransport.mvp.sampledetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.SampleRecordListBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.utils.ModelUtils;

/**
 * @author qiuyongheng
 * @time 2017/6/14  10:59
 * @desc ${TODD}
 */

public class SampleDetailFragment extends Fragment implements SampleDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.text_ship_name)
    TextView textShipName;
    @BindView(R.id.text_ship_num)
    TextView textShipNum;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    private SampleDetailContract.Presenter presenter;
    private SampleDetailActivity activity;
    private SampleDetailAdapter mAdapter;
    private int itemID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        showItemID(250);
        return view;
    }

    private void initListener() {
        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        // 添加取样
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                mAdapter.addData(mAdapter.list.size());
                recyclerview.smoothScrollToPosition(mAdapter.list.size());
            }
        });
    }

    @Override
    public void initViews(View view) {
        //手动打开日志。
        ModelUtils.setDebugModel(true);


        setHasOptionsMenu(true);
        // 初始化toolbar
        activity = (SampleDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(R.string.sampledetail_title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
    public void setPresenter(SampleDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void showShipName(String name) {
        textShipName.setText(name);
    }

    @Override
    public void showShipNumber(String num) {
        textShipNum.setText(num);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showItemID(int itemID) {
        this.itemID = itemID;
        if (mAdapter == null) {
            // 初始化recyclerview
            recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


            List<SampleRecordListBean> list = new ArrayList<>();
            // 判断该itemID是否有缓存
            String string = SharePreferenceUtil.getString(getContext(), String.valueOf(itemID), "");
            if (TextUtils.isEmpty(string)) {
                // 默认有3条记录
                for (int i = 0; i < 3; i++) {
                    SampleRecordListBean sampleRecordList = new SampleRecordListBean();
                    sampleRecordList.setItemID(itemID);
                    list.add(sampleRecordList);
                }
            } else {
                // 有缓存, 解析
                list = new Gson().fromJson(string, new TypeToken<List<SampleRecordListBean>>() {
                }.getType());
            }


            mAdapter = new SampleDetailAdapter(getContext(), list, itemID);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            mAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, final int position, int... type) {
                    switch (type[0]) {
                        case SettingUtil.HOLDER_IMAGE_1:
                            RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 保存图片地址
                                    SampleRecordListBean sampleRecordList = mAdapter.list.get(position);
                                    sampleRecordList.setImage_1(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        case SettingUtil.HOLDER_IMAGE_2:
                            RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 保存图片地址
                                    SampleRecordListBean sampleRecordList = mAdapter.list.get(position);
                                    sampleRecordList.setImage_2(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                            break;

                    }
                }

                @Override
                public void onItemLongClick(View view, final int position) {
                    // 长按删除
                    activity.showDailog(getResources().getString(R.string.dialog_delete),
                            getResources().getString(R.string.dialog_sample_delete_msg),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (mAdapter.list.size() < 3) {
                                        Toast.makeText(getContext(), "取样记录不能少于3条", Toast.LENGTH_SHORT).show();
                                    } else {
                                        mAdapter.delete(position);
                                    }
                                }
                            });
                }
            });
            recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当界面不显示时, 缓存数据到sp中
     */
    @Override
    public void onPause() {
        super.onPause();
        String json = new Gson().toJson(mAdapter.list);
        Log.d("==", json);
        SharePreferenceUtil.saveString(getContext(), String.valueOf(itemID), json);
    }
}
