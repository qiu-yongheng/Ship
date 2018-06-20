package com.kc.shiptransport.mvp.training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

public class TrainingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, TrainingActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
