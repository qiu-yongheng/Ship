package com.kc.shiptransport.mvp.voyagedetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qiuyongheng
 * @time 2017/7/4  15:46
 * @desc ${TODD}
 */

public class VoyageListActivity extends BaseActivity {
    public static final String TAG = "TITLE";
    public static final String NUM = "NUM";
    public static final String NAME = "NAME";
    private static final String ARR = "ARR";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private DatesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        String string = bundle.getString(TAG);
        List<VoyageDetailBean.ColumnsBean.ArrBean> list = new Gson().fromJson(string, new TypeToken<List<VoyageDetailBean.ColumnsBean.ArrBean>>() {
        }.getType());

        showList(list);
    }

    private void showList(List<VoyageDetailBean.ColumnsBean.ArrBean> list) {
        if (adapter == null) {
            adapter = new DatesListAdapter(VoyageListActivity.this, list);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    VoyageDetailBean.ColumnsBean.ArrBean bean = adapter.list.get(position);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(NUM, bean.getItemID());
                    bundle.putString(NAME, bean.getName());
                    intent.putExtras(bundle);
                    setResult(SettingUtil.TYPE_ARRAY, intent);
                    finish();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivityForResult(Activity activity, String arr, int requestCode) {
        Intent intent = new Intent(activity, VoyageListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TAG, arr);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode, bundle);
    }




















    class DatesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final Context context;
        public List<VoyageDetailBean.ColumnsBean.ArrBean> list;
        private final LayoutInflater inflate;
        private OnRecyclerviewItemClickListener listener;

        public DatesListAdapter(Context context, List<VoyageDetailBean.ColumnsBean.ArrBean> list) {
            this.context = context;
            this.list = list;
            this.inflate = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalHolder(inflate.inflate(R.layout.item_data_list, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            VoyageDetailBean.ColumnsBean.ArrBean bean = list.get(position);
            ((NormalHolder)holder).mTvShipName.setText(bean.getName());

            ((NormalHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        public void setDates(List<VoyageDetailBean.ColumnsBean.ArrBean> list) {
            this.list = list;
        }


        class NormalHolder extends RecyclerView.ViewHolder {

            private final TextView mTvShipName;

            public NormalHolder(View itemView) {
                super(itemView);
                mTvShipName = (TextView) itemView.findViewById(R.id.tv_ship_name);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
            this.listener = listener;
        }
    }
}
