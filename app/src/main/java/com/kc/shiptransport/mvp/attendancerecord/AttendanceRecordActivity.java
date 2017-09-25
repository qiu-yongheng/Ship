package com.kc.shiptransport.mvp.attendancerecord;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/6/28 21:11
 * @desc ${TODO}
 */

public class AttendanceRecordActivity extends BaseActivity{

    private AttendanceRecordFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        mFragment = new AttendanceRecordFragment();
        if (!mFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, mFragment)
                    .commit();
        }

        new AttendanceRecordPresenter(this, mFragment, new DataRepository());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AttendanceRecordActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
