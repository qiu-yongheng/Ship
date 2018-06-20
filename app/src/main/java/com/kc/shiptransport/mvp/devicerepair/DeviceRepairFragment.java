package com.kc.shiptransport.mvp.devicerepair;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.device.repair.DeviceRepairBean;
import com.kc.shiptransport.data.bean.device.repair.DeviceRepairDetailBean;
import com.kc.shiptransport.data.bean.device.repair.DeviceShipBean;
import com.kc.shiptransport.data.bean.device.repair.DeviceShipListBean;
import com.kc.shiptransport.mvp.basemvp.BaseListFragment;
import com.kc.shiptransport.mvp.devicerepairdetail.DeviceRepairDetailActivity;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.custom.DataInputLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author 邱永恒
 * @time 2018/6/16  12:18
 * @desc
 */

public class DeviceRepairFragment extends BaseListFragment<DeviceRepairActivity, DeviceRepairDetailBean, DeviceShipBean> {
    private String shipAccount;

    @Override
    public void onAddSelected() {
        DeviceRepairDetailActivity.startActivity(getContext(), null, SettingUtil.TYPE_DEVICE_REPAIR_ADD);
    }

    @Override
    public void initSelect1(TextView select1) {
        select1.setVisibility(View.GONE);
    }

    @Override
    public void initSelect2(TextView select2) {
        select2.setText(getDefaultSelect2Text());
    }

    @Override
    public void initSelect3(TextView select3) {
        select3.setVisibility(View.GONE);
    }

    @Override
    public void initSelect4(TextView select4) {
        select4.setVisibility(View.GONE);
    }

    @Override
    public String getDefaultSelect2Text() {
        return "全部施工船舶";
    }

    @Override
    public Observable<List<DeviceRepairDetailBean>> getDataList(int pageSize, int pageCount, String startDate, String endDate, boolean isShow) {
        return dataRepository.GetEquipmentRepairRecords(pageSize, pageCount, startDate, endDate, shipAccount).flatMap(new Function<DeviceRepairBean, ObservableSource<List<DeviceRepairDetailBean>>>() {
            @Override
            public ObservableSource<List<DeviceRepairDetailBean>> apply(final DeviceRepairBean deviceRepairBean) throws Exception {
                return Observable.create(new ObservableOnSubscribe<List<DeviceRepairDetailBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<DeviceRepairDetailBean>> e) throws Exception {
                        e.onNext(deviceRepairBean.getData());
                        e.onComplete();
                    }
                });
            }
        });
    }

    @Override
    public Observable<List<DeviceShipBean>> getShipList() {
        return dataRepository.GetManagementShip(1000, 1).flatMap(new Function<DeviceShipListBean, ObservableSource<List<DeviceShipBean>>>() {
            @Override
            public ObservableSource<List<DeviceShipBean>> apply(final DeviceShipListBean deviceShipListBean) throws Exception {
                return Observable.create(new ObservableOnSubscribe<List<DeviceShipBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<DeviceShipBean>> e) throws Exception {
                        e.onNext(deviceShipListBean.getData());
                        e.onComplete();
                    }
                });
            }
        });
    }

    @Override
    public List<DeviceShipBean> getShipListForDB() {
        return DataSupport.findAll(DeviceShipBean.class);
    }

    @Override
    public CommonAdapter<DeviceRepairDetailBean> getAdapter(List<DeviceRepairDetailBean> list) {
        return new CommonAdapter<DeviceRepairDetailBean>(getContext(), R.layout.item_device_repair, list) {
            @Override
            protected void convert(ViewHolder holder, final DeviceRepairDetailBean dataBean, int position) {
                holder.setText(R.id.tv_ship_name, dataBean.getShipName())
                        .setText(R.id.tv_repair_project, dataBean.getRepairProject())
                        .setText(R.id.tv_repair_progress, dataBean.getRepairProcess())
                        .setText(R.id.tv_repair_man, dataBean.getRepairman())
                        .setText(R.id.tv_creator, dataBean.getCreator())
                        .setText(R.id.tv_start_time, dataBean.getStartTime())
                        .setText(R.id.tv_end_time, dataBean.getEndTime())
                        .setText(R.id.tv_remark, dataBean.getRemark())
                        .setOnClickListener(R.id.btn_show, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DeviceRepairDetailActivity.startActivity(getContext(), dataBean, SettingUtil.TYPE_DEVICE_REPAIR_READ_ONLY);
                            }
                        })
                        .setOnClickListener(R.id.btn_update, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DeviceRepairDetailActivity.startActivity(getContext(), dataBean, SettingUtil.TYPE_DEVICE_REPAIR_UPDATE);
                            }
                        })
                        .setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
            }
        };
    }

    @Override
    public String getSelect2ItemTitle(DeviceShipBean deviceShipBean) {
        return deviceShipBean.getShipName();
    }

    @Override
    public void onSelect2itemClick(ViewHolder holder, DeviceShipBean deviceShipBean, int position) {
        // 更新查询的船舶账号
        shipAccount = deviceShipBean.getShipAccount();
    }

    @Override
    public void onSelect2HeadClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 查询所有
        shipAccount = "";
    }

    @Override
    public int setTitle() {
        return R.string.title_device_repair;
    }
}
