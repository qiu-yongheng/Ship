package com.kc.shiptransport.mvp.contactsdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/8/16  10:49
 * @desc ${TODD}
 */

public class ContactDetailActivity extends BaseActivity{

    private static final String CONTACT = "CONTACT";
    private ContactDetailFragment fragment;
    public Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        contacts = (Contacts) bundle.getSerializable(CONTACT);

        if (savedInstanceState != null) {
            fragment = (ContactDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ContactDetailFragment");
        } else {
            fragment = new ContactDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ContactDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ContactDetailFragment", fragment);
        }
    }

    public static void startActivity(Context context, Contacts contacts) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONTACT, contacts);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
