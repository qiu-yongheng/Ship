package com.kc.shiptransport.view.actiivty;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerImagePathBean;
import com.kc.shiptransport.mvp.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qiuyongheng
 * @time 2017/6/22  17:01
 * @desc ${TODD}
 */

public class ImageSelectActivity extends BaseActivity {

    public int requestcode;
    @BindView(R.id.btn_determine)
    Button btnDetermine;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        requestcode = bundle.getInt("REQUESTCODE");
        title = bundle.getString("TITLE");

        initView();
        initListener();
    }


    /**
     * 初始化
     */
    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        List<ScannerImagePathBean> list = new ArrayList<>();
        ImageAdapter adapter = new ImageAdapter(this, list);
        recyclerview.setAdapter(adapter);
    }

    /**
     * 初始化点击事件
     */
    private void initListener() {
        btnDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 保存图片地址到数据库, 或上传图片
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, String title, int requestCode) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putInt("REQUESTCODE", requestCode);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }


    /**
     * 内部类
     */
    class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context context;
        private final List<ScannerImagePathBean> list;
        private final LayoutInflater inflater;
        /**
         * item类型: 文字 + 图片
         */
        private static final int TYPE_NORMAL = 0;
        /**
         * item类型: footer，加载更多
         */
        private static final int TYPE_ADD = 1;

        public ImageAdapter(Context context, List<ScannerImagePathBean> list) {
            this.context = context;
            this.list = list;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_NORMAL:
                    return new NormalHolder(inflater.inflate(R.layout.item_normal, parent, false));
                case TYPE_ADD:
                    return new AddHolder(inflater.inflate(R.layout.item_add, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof NormalHolder) {

            } else if (holder instanceof AddHolder) {

            }
        }

        /**
         * 显示图片
         */
        class NormalHolder extends RecyclerView.ViewHolder {

            public NormalHolder(View itemView) {
                super(itemView);
            }
        }

        /**
         * 添加图片
         */
        class AddHolder extends RecyclerView.ViewHolder {

            public AddHolder(View itemView) {
                super(itemView);
            }
        }

        /**
         * 获取item类型
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {
            if (position == list.size()) {
                return TYPE_ADD;
            }
            return TYPE_NORMAL;
        }

        @Override
        public int getItemCount() {
            return list.size() + 1;
        }
    }
}
