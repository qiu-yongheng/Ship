package com.kc.shiptransport.mvp.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.adapter.ContactAdapter;
import com.kc.shiptransport.db.contacts.Contacts;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/8/14  10:18
 * @desc ${TODD}
 */

public class ContactsListFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        List<Contacts> contactses = DataSupport.where("LoginName is not null and LoginName != ?", "").find(Contacts.class);

        ContactAdapter contactAdapter = new ContactAdapter(getContext(), contactses);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
