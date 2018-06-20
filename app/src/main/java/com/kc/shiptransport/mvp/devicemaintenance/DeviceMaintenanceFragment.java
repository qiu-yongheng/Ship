package com.kc.shiptransport.mvp.devicemaintenance;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.device.maintenance.DeviceMaintenanceBean;
import com.kc.shiptransport.data.bean.device.maintenance.DeviceMaintenanceListBean;
import com.kc.shiptransport.data.bean.device.repair.DeviceShipBean;
import com.kc.shiptransport.data.bean.device.repair.DeviceShipListBean;
import com.kc.shiptransport.mvp.basemvp.BaseListFragment;
import com.kc.shiptransport.mvp.devicerepairdetail.DeviceRepairDetailActivity;
import com.kc.shiptransport.util.SettingUtil;
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
 * @time 2018/6/20  22:06
 * @desc 维护保养
 */

public class DeviceMaintenanceFragment extends BaseListFragment<DeviceMaintenanceActivity, DeviceMaintenanceBean, DeviceShipBean> {

    private String shipAccount;

    @Override
    public void onAddSelected() {

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
    public Observable<List<DeviceMaintenanceBean>> getDataList(int pageSize, int pageCount, String startDate, String endDate, boolean isShow) {
        return null;
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
    public CommonAdapter<DeviceMaintenanceBean> getAdapter(List<DeviceMaintenanceBean> list) {
        return new CommonAdapter<DeviceMaintenanceBean>(getContext(), R.layout.item_device_maintenance, list) {
            @Override
            protected void convert(ViewHolder holder, DeviceMaintenanceBean bean, int position) {
                holder.setText(R.id.tv_ship_name, bean.getShipName())
                        .setText(R.id.tv_creator, bean.getCreator())
                        .setText(R.id.tv_main_project, bean.getMaintenanceProgram())
                        .setText(R.id.tv_main_type, bean.getMaintenanceType())
                        .setText(R.id.tv_total_time, bean.getTotalOperationTime() + "")
                        .setText(R.id.tv_last_time, bean.getLastMaintenanceTime())
                        .setText(R.id.tv_next_time, bean.getNextMaintenanceTime())
                        .setText(R.id.tv_start_time, bean.getStartTime())
                        .setText(R.id.tv_end_time, bean.getEndTime())
                        .setText(R.id.tv_remark, "")
                        .setOnClickListener(R.id.btn_show, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setOnClickListener(R.id.btn_update, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
    public String getDefaultSelect2Text() {
        return "全部施工船舶";
    }

    @Override
    public String getSelect2ItemTitle(DeviceShipBean bean) {
        return bean.getShipName();
    }

    @Override
    public void onSelect2itemClick(ViewHolder holder, DeviceShipBean bean, int position) {
        shipAccount = bean.getShipAccount();
    }

    @Override
    public void onSelect2HeadClick(View view, RecyclerView.ViewHolder holder, int position) {
        shipAccount = "";
    }

    @Override
    public int setTitle() {
        return R.string.title_device_maintenance;
    }
}
