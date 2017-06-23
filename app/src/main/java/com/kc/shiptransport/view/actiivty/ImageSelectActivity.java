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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerImagePathBean;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

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
    private ImageAdapter mAdapter;
    private int itemid;
    private ScannerImage scannerImage;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        requestcode = bundle.getInt("REQUESTCODE");
        title = bundle.getString("TITLE");
        itemid = bundle.getInt("ITEMID");

        // 获取itemID对应的数据库数据
        List<ScannerImage> scannerImages = DataSupport.where("ItemID = ?", String.valueOf(itemid)).find(ScannerImage.class);
        scannerImage = scannerImages.get(0);

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

        // 从数据库获取缓存数据
        switch (requestcode) {
            case SettingUtil.ID_STOWAGE:
                String stowage = scannerImage.getStowage();
                list = loadCache(list, stowage);
                break;
            case SettingUtil.ID_SHIP_BILL:
                list = loadCache(list, scannerImage.getShip_bill());
                break;
            case SettingUtil.ID_CONSIGNMENT_BILL:
                list = loadCache(list, scannerImage.getConsignment_bill());
                break;
            case SettingUtil.ID_GRAVEL:
                list = loadCache(list, scannerImage.getGravel());
                break;
            case SettingUtil.ID_STRIP_PLOT:
                list = loadCache(list, scannerImage.getStrip_plot_select());
                break;
            case SettingUtil.ID_STRIP_PLOT_PLAN:
                list = loadCache(list, scannerImage.getStrip_plot());
                break;
            case SettingUtil.ID_DELIVERY_BILL:
                list = loadCache(list, scannerImage.getDelivery_bill());
                break;
            case SettingUtil.ID_QC:
                list = loadCache(list, scannerImage.getQc());
                break;
            case SettingUtil.ID_CUSTOMS:
                list = loadCache(list, scannerImage.getCustoms());
                break;
            case SettingUtil.ID_CONTRAST:
                list = loadCache(list, scannerImage.getContrast());
                break;
            case SettingUtil.ID_CUSTOMS_BILL:
                list = loadCache(list, scannerImage.getCustoms_bill());
                break;
        }


        mAdapter = new ImageAdapter(this, list);
        recyclerview.setAdapter(mAdapter);
    }

    private List<ScannerImagePathBean> loadCache(List<ScannerImagePathBean> list, String stowage) {
        if (!TextUtils.isEmpty(stowage)) {
            list = gson.fromJson(stowage, new TypeToken<List<ScannerImagePathBean>>() {
            }.getType());
        }
        return list;
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

        // 设置点击事件
        mAdapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
            /**
             * 预览图片   修改图片
             * @param view
             * @param position
             * @param type
             */
            @Override
            public void onItemClick(View view, final int position, int... type) {
                if (type[0] == SettingUtil.ITEM_DELETE) {
                    // 删除
                    deleteImage(position);
                } else {
                    switch (requestcode) {
                        case SettingUtil.ID_STOWAGE:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_SHIP_BILL:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_CONSIGNMENT_BILL:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_GRAVEL:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_STRIP_PLOT:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_STRIP_PLOT_PLAN:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_DELIVERY_BILL:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_QC:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_CUSTOMS:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_CONTRAST:
                            updateImage(position, requestcode);
                            break;
                        case SettingUtil.ID_CUSTOMS_BILL:
                            updateImage(position, requestcode);
                            break;
                    }
                }

            }

            /**
             * 添加图片
             * @param view
             * @param position
             */
            @Override
            public void onItemLongClick(View view, int position) {
                int size = mAdapter.list.size();
                // 弹出图片选择器
                switch (requestcode) {
                    case SettingUtil.ID_STOWAGE:
                        addImage(size, 3, requestcode);
                        break;
                    case SettingUtil.ID_SHIP_BILL:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_CONSIGNMENT_BILL:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_GRAVEL:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_STRIP_PLOT:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_STRIP_PLOT_PLAN:
                        addImage(size, 2, requestcode);
                        break;
                    case SettingUtil.ID_DELIVERY_BILL:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_QC:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_CUSTOMS:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_CONTRAST:
                        addImage(size, 1, requestcode);
                        break;
                    case SettingUtil.ID_CUSTOMS_BILL:
                        addImage(size, 1, requestcode);
                        break;
                }
            }
        });
    }

    /**
     * 删除图片
     *
     * @param position
     */
    private void deleteImage(int position) {
        mAdapter.delete(position);
        saveToDB(requestcode);
    }

    /**
     * 修改图片
     *
     * @param i
     * @param position
     */
    private void updateImage(final int position, final int i) {
        RxGalleryUtil.getImagRadio(ImageSelectActivity.this, new OnRxGalleryRadioListener() {
            @Override
            public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                mAdapter.update(position, imageMultipleResultEvent.getResult().get(0).getOriginalPath());

                saveToDB(i);
            }
        });
    }

    /**
     * 添加图片, 并保存到数据库
     *
     * @param size
     * @param num
     * @param id
     */
    private void addImage(int size, int num, final int id) {
        if (size < num) {
            // 弹出图片选择器
            RxGalleryUtil.getImagRadio(ImageSelectActivity.this, new OnRxGalleryRadioListener() {
                @Override
                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                    mAdapter.add(imageMultipleResultEvent.getResult().get(0).getOriginalPath());

                    // 保存数据到数据库
                    saveToDB(id);
                }
            });
        } else {
            tip("最多只能选择" + size + "张图片");
        }
    }

    /**
     * 保存数据到数据库
     *
     * @param id
     */
    private void saveToDB(int id) {
        switch (id) {
            case SettingUtil.ID_STOWAGE:
                scannerImage.setStowage(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_SHIP_BILL:
                scannerImage.setShip_bill(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_CONSIGNMENT_BILL:
                scannerImage.setConsignment_bill(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_GRAVEL:
                scannerImage.setGravel(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_STRIP_PLOT:
                scannerImage.setStrip_plot_select(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_STRIP_PLOT_PLAN:
                scannerImage.setStrip_plot(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_DELIVERY_BILL:
                scannerImage.setDelivery_bill(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_QC:
                scannerImage.setQc(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_CUSTOMS:
                scannerImage.setCustoms(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_CONTRAST:
                scannerImage.setContrast(mAdapter.tojson());
                scannerImage.save();
                break;
            case SettingUtil.ID_CUSTOMS_BILL:
                scannerImage.setConsignment_bill(mAdapter.tojson());
                scannerImage.save();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, String title, int itemID, int requestCode) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putInt("ITEMID", itemID);
        bundle.putInt("REQUESTCODE", requestCode);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }


    /**
     * 内部类
     */
    class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context context;
        public final List<ScannerImagePathBean> list;
        private final LayoutInflater inflater;
        /**
         * item类型: 文字 + 图片
         */
        private static final int TYPE_NORMAL = 0;
        /**
         * item类型: footer，加载更多
         */
        private static final int TYPE_ADD = 1;
        private OnRecyclerviewItemClickListener listener;

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
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof NormalHolder) {
                // 获取图片地址, 显示
                ScannerImagePathBean scannerImagePathBean = list.get(position);
                RxGalleryUtil.showImage(context, scannerImagePathBean.getImage_path(), null, null, ((NormalHolder) holder).mIvNormal);

                // 压缩图片
                //Luban.with(context).load(new File(scannerImagePathBean.getImage_path()))

                // 点击图片, 预览图片
                ((NormalHolder) holder).mIvNormal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(holder.itemView, holder.getLayoutPosition(), 0);
                    }
                });

                // 删除
                ((NormalHolder) holder).mIvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.ITEM_DELETE);
                    }
                });


            } else if (holder instanceof AddHolder) {
                ((AddHolder) holder).mBtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 弹出图片选择器
                        listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    }
                });
            }
        }

        /**
         * 显示图片
         */
        class NormalHolder extends RecyclerView.ViewHolder {

            private final ImageView mIvNormal;
            private final ImageView mIvDelete;

            public NormalHolder(View itemView) {
                super(itemView);
                mIvNormal = (ImageView) itemView.findViewById(R.id.iv_normal);
                mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            }
        }

        /**
         * 添加图片
         */
        class AddHolder extends RecyclerView.ViewHolder {

            private final ImageButton mBtnAdd;

            public AddHolder(View itemView) {
                super(itemView);
                mBtnAdd = (ImageButton) itemView.findViewById(R.id.btn_add);
            }
        }

        /**
         * 获取item类型
         *
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

        /**
         * 设置点击事件
         */
        public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
            this.listener = listener;
        }

        public void add(String path) {
            ScannerImagePathBean bean = new ScannerImagePathBean();
            bean.setImage_path(path);
            list.add(bean);
            notifyDataSetChanged();
        }

        public void delete(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }

        public void update(int position, String path) {
            ScannerImagePathBean bean = list.get(position);
            bean.setImage_path(path);
            notifyDataSetChanged();
        }

        public String tojson() {
            return new Gson().toJson(list);
        }
    }
}
