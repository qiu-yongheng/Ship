package com.kc.shiptransport.view.actiivty;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.SettingUtil;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/13 22:08
 * @desc ${TODO}
 */

public class InputActivity extends BaseActivity {
    private static final String TYPE = "TYPE";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_input)
    EditText mEtInput;
    public static final String TAG = "TAG";
    public static final String TITLE = "TTITLE";
    public static final String HINT = "HINT";
    public static final int REQUEST = 1;
    @BindView(R.id.btn_determine)
    Button btnDetermine;
    @BindView(R.id.text_title)
    TextView textTitle;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(TITLE);
        String hint = bundle.getString(HINT);
        type = bundle.getInt(TYPE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        textTitle.setText(title);

        if (!getResources().getString(R.string.text_unfilled).equals(hint)) {
            mEtInput.setText(hint);
        }

        // 确定按钮的点击事件
        btnDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                result();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void result() {
        String trim = mEtInput.getText().toString().trim();
        Intent intent = new Intent();


        switch (type) {
            case SettingUtil.TYPE_TEXT:
                /** 文字 */
                intent.putExtra(TAG, trim);
                setResult(0, intent);
                finish();
                break;
            case SettingUtil.TYPE_NUMBER:
                /** 数字 */
                if (isNumeric(trim)) {
                    intent.putExtra(TAG, trim);
                    setResult(0, intent);
                    finish();
                } else {
                    Toast.makeText(this, "请填写数字", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 判断字符串是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("([1-9][0-9]*)+(.[0-9]{1,4})?");
        return pattern.matcher(str).matches();
    }

    @Override
    public void onBackPressed() {
        result();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivityForResult(Activity activity, String title, String hint, int type, int requestCode) {
        Intent intent = new Intent(activity, InputActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(HINT, hint);
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode, bundle);
    }
}
