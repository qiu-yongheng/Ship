package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/17  15:13
 * @desc ${TODD}
 */

public class SampleDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<String> list;
    private OnRecyclerviewItemClickListener listener;

    public SampleDetailAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 从集合中获取图片显示, 如果没有图片, 点击后跳转到图片选择界面, 如果有图片, 跳转到预览界面
        // 单选图片后, 保存地址到集合中, 保存到数据库, 记录position
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final EditText mEditText;
        private final ImageButton mBtnImage1;
        private final ImageButton mBtnImage2;

        public NormalHolder(View itemView) {
            super(itemView);
            mEditText = (EditText) itemView.findViewById(R.id.et_sample_num);
            mBtnImage1 = (ImageButton) itemView.findViewById(R.id.btn_image_1);
            mBtnImage2 = (ImageButton) itemView.findViewById(R.id.btn_image_2);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void addData(int pos){
        list.add(pos,"insert one");
        notifyItemInserted(pos);

    }

    public void delete(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }

}
