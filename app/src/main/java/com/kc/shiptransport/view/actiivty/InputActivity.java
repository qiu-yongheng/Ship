package com.kc.shiptransport.view.actiivty;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/13 22:08
 * @desc ${TODO}
 */

public class InputActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_input)
    EditText mEtInput;
    public static final String TAG = "TAG";
    public static final String TITLE = "TTITLE";
    public static final String HINT = "HINT";
    public static final int REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(TITLE);
        String hint = bundle.getString(HINT);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        if (!getResources().getString(R.string.text_unfilled).equals(hint)) {
            mEtInput.setText(hint);
        }

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
        intent.putExtra(TAG, trim);
        setResult(0, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        result();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivityForResult(Activity activity, String title, String hint, int requestCode) {
        Intent intent = new Intent(activity, InputActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(HINT, hint);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode, bundle);
    }
}
