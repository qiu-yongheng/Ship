package com.kc.shiptransport.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScanCommitBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.mvp.attendance.AttendanceActivity;
import com.kc.shiptransport.mvp.main.MainActivity;
import com.kc.shiptransport.util.FileUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.view.pop.HomeItemPop;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.text_user)
    TextView textUser;
    //    private Integer[] icon = new Integer[]{ R.mipmap.plan, R.mipmap.log, R.mipmap.supply_sand, R.mipmap.save, R.mipmap.data, R.mipmap.msg, R.mipmap.ship_monitor, R.mipmap.acceptance, R.mipmap.plan , R.mipmap.map};
    //    private String[] tag = new String[]{"供砂管理", "施工记录", "施工照片", "安全管理", "数据分析"
    //            , "生产指令", "航线管理", "通讯录", "考勤管理", "电子地图"};
    private MainActivity activity;
    private DataRepository dataRepository;
    private HomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        dataRepository = new DataRepository();

        // TODO 获取图标列表 RX
        getAppList();

        return view;
    }

    /**
     * 获取要显示的图标列表
     * AppPID = "0", 排序
     */
    private void getAppList() {
        dataRepository
                .getAppList(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<AppList> appLists) {
                        showApplist(appLists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 创建adapter, 显示数据
     *
     * @param list
     */
    private void showApplist(List<AppList> list) {
        if (adapter == null) {
            adapter = new HomeAdapter(getContext(), list);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    switch (type[0]) {
                        case -1:
                            tip("功能待开发");
                            RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    ScanCommitBean bean = new ScanCommitBean();
                                    /**
                                     * ItemID :
                                     * SubcontractorInterimApproachPlanID : 415
                                     * SubcontractorPerfectBoatScannerAttachmentTypeID : 3
                                     * SubcontractorAccount : yflf
                                     * ConstructionBoatAccount : jx6
                                     * FileName : abc.jpg
                                     * SuffixName : jpg
                                     * Creator : yflf
                                     */
                                    bean.setItemID(0);
                                    bean.setSubcontractorInterimApproachPlanID(415);
                                    bean.setSubcontractorPerfectBoatScannerAttachmentTypeID(1);
                                    bean.setSubcontractorAccount("yflf");
                                    bean.setConstructionBoatAccount("jx6");

                                    // 图片名
                                    String title = imageMultipleResultEvent.getResult().get(0).getTitle();

                                    // 图片类型
                                    String mimeType = imageMultipleResultEvent.getResult().get(0).getMimeType();
                                    String[] split = mimeType.split("/");
                                    String suffixName = split[split.length - 1];

                                    bean.setFileName(title + "." + suffixName);
                                    bean.setSuffixName(suffixName);
                                    bean.setCreator("yflf");

                                    File file = new File(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                    byte[] bytes = FileUtil.File2byte(file);
                                    bean.setBase64img(new String(Base64.encode(bytes, Base64.DEFAULT)));

                                    DataRepository dataRepository = new DataRepository();

                                    dataRepository
                                            .InsertSubcontractorPerfectBoatScannerAttachment(bean)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<Boolean>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull Boolean aBoolean) {
                                                    Toast.makeText(getContext(), "提交结果: " + aBoolean, Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                            });
                            break;
                        case 1:
                            // 电子海图
                            break;
                        case 2:
                            // 供砂管理
                            HomeItemPop pop = HomeItemPop.getInstanst(getContext());
                            pop.onShow(view);
                            break;
                        case 3:
                            // 施工记录
                            break;
                        case 4:
                            // 施工照片
                            break;
                        case 5:
                            // 安全管理
                            break;
                        case 6:
                            // 数据分析
                            break;
                        case 7:
                            // 生产指令
                            break;
                        case 8:
                            // 航线管理
                            break;
                        case 18:
                            // 考勤管理
                            AttendanceActivity.startActivity(getContext());
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            rvHome.setAdapter(adapter);
        } else {
            adapter.setDates(list);
        }
    }

    private void initView(View view) {
        rvHome.setHasFixedSize(true);
        rvHome.setLayoutManager(new GridLayoutManager(getContext(), 3));

        activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbarHome);

        List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
        textUser.setText("登录用户: " + all.get(0).getSubcontractorName());
    }

    public void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void tip(String tip) {
        Toast.makeText(activity, tip, Toast.LENGTH_SHORT).show();
    }
}
