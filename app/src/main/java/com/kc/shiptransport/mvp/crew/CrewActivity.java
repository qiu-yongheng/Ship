package com.kc.shiptransport.mvp.crew;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

public class CrewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CrewActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
