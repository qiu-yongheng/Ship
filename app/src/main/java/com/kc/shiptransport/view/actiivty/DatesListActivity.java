package com.kc.shiptransport.view.actiivty;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/4  15:46
 * @desc ${TODD}
 */

public class DatesListActivity extends BaseActivity {
    public static final String TAG = "TITLE";
    public static final String NUM = "NUM";
    public static final String NAME = "NAME";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    private DatesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        Observable.create(new ObservableOnSubscribe<List<ConstructionBoat>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ConstructionBoat>> e) throws Exception {
                List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
                if (all.isEmpty()) {
                    String result = new RemoteDataSource().GetConstructionBoat();
                    List<ConstructionBoat> list = new Gson().fromJson(result, new TypeToken<List<ConstructionBoat>>() {
                    }.getType());

                    if (!list.isEmpty()) {
                        DataSupport.deleteAll(ConstructionBoat.class);
                        // 保存到数据库
                        DataSupport.saveAll(list);
                    }

                    e.onNext(list);
                } else {
                    e.onNext(all);
                }

                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ConstructionBoat>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<ConstructionBoat> constructionBoats) {
                        showList(constructionBoats);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        initListener();

    }

    private void initListener() {
        /** 返回 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /** 取消选择 */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(NUM, "");
                bundle.putString(NAME, "");
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
            }
        });
    }

    private void showList(List<ConstructionBoat> list) {
        if (adapter == null) {
            adapter = new DatesListAdapter(DatesListActivity.this, list);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    ConstructionBoat boat = adapter.list.get(position);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(NUM, boat.getShipNum());
                    bundle.putString(NAME, boat.getShipName());
                    intent.putExtras(bundle);
                    setResult(0, intent);
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
    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, DatesListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode, bundle);
    }


    class DatesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final Context context;
        public List<ConstructionBoat> list;
        private final LayoutInflater inflate;
        private OnRecyclerviewItemClickListener listener;

        public DatesListAdapter(Context context, List<ConstructionBoat> list) {
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
            ConstructionBoat boat = list.get(position);
            ((NormalHolder) holder).mTvShipName.setText(boat.getShipName());

            ((NormalHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        public void setDates(List<ConstructionBoat> list) {
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
