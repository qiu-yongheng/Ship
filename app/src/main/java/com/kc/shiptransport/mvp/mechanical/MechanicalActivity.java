package com.kc.shiptransport.mvp.mechanical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

public class MechanicalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        MechanicalFragment mechanicalFragment = (MechanicalFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (mechanicalFragment == null) {
            mechanicalFragment = new MechanicalFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mechanicalFragment, R.id.fragment_repository);
        }

        new MechanicalPresenter(this, mechanicalFragment, new DataRepository());
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MechanicalActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
