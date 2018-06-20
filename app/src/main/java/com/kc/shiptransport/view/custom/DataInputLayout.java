package com.kc.shiptransport.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.kc.shiptransport.R;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/6/17  13:29
 * @desc
 */

public class DataInputLayout extends LinearLayout implements View.OnClickListener {
    public static int TYPE_READ_ONLY = 0;
    public static int TYPE_SELECT = 1;
    public static int TYPE_ENDIT = 2;
    private String tag = "";
    private int inputType;
    private TextView tagTextView;
    private TextView selectView;
    private EditText singleEditText;
    private OnClickListener listener;

    public DataInputLayout(Context context) {
        this(context, null);
    }

    public DataInputLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataInputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initView();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DataInputLayout);
        inputType = typedArray.getInt(R.styleable.DataInputLayout_input_type, 0);
        tag = typedArray.getString(R.styleable.DataInputLayout_tag);
        typedArray.recycle();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);


        /** 标题描述 */
        tagTextView = new TextView(getContext());
        tagTextView.setTextSize(15);
        tagTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_tag_gray));
        tagTextView.setText(tag);

        LinearLayout.LayoutParams tagLayoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tagLayoutParams.setMargins(SizeUtils.dp2px(14), 0, 0, 0);
        tagTextView.setLayoutParams(tagLayoutParams);

        /** 选择框 */
        selectView = new TextView(getContext());
        selectView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_tag_gray));
        selectView.setTextSize(15);
        if (inputType != 0) {
            selectView.setBackgroundResource(R.color.et_bg);
            selectView.setText(R.string.tv_hint);
        } else {
            selectView.setBackground(null);
        }
        selectView.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams selectLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(32));
        selectLayoutParams.setMargins(SizeUtils.dp2px(12), 0, SizeUtils.dp2px(14), 0);
        selectView.setLayoutParams(selectLayoutParams);

        selectView.setOnClickListener(this);

        /** 输入框 */
        singleEditText = new EditText(getContext());
        singleEditText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_tag_gray));
        singleEditText.setTextSize(15);
        singleEditText.setPadding(0, SizeUtils.dp2px(6), SizeUtils.dp2px(6), 0);
        singleEditText.setBackgroundResource(R.color.et_bg);
        singleEditText.setGravity(Gravity.CENTER);
        singleEditText.setHint(R.string.tv_input);

        LinearLayout.LayoutParams editLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(32));
        editLayoutParams.setMargins(SizeUtils.dp2px(12), 0, SizeUtils.dp2px(14), 0);
        singleEditText.setLayoutParams(editLayoutParams);


        // 添加view到root
        addView(tagTextView);

        switch (inputType) {
            case 0:
                addView(selectView);
                break;
            case 1:
                addView(selectView);
                break;
            case 2:
                addView(singleEditText);
                break;
        }
    }

    /**
     * 获取编辑框内的内容
     *
     * @return
     */
    public String getEditText() {
        return singleEditText.getText().toString().trim();
    }

    /**
     * TODO: 获取选择的内容
     *
     * @return
     */
    public String getSelectText() {
        return selectView.getText().toString().trim();
    }

    /**
     * 设置展示数据
     *
     * @param str
     */
    public void setValue(String str) {
        if (inputType == 0 || inputType == 1) {
            selectView.setText(str);
        } else if (inputType == 2) {
            singleEditText.setText(str);
        }
    }

    /**
     * 修改为只读
     */
    public void setReadOnly() {
        if (inputType == 1) {
            selectView.setBackground(null);
        } else if (inputType == 2) {
            singleEditText.setBackground(null);
            singleEditText.setFocusable(false);
            singleEditText.setFocusableInTouchMode(false);
        }
    }

    public View getInputView() {
        if (inputType == 2) {
            return singleEditText;
        } else {
            return selectView;
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


}
