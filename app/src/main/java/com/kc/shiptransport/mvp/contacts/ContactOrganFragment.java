package com.kc.shiptransport.mvp.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.mvp.contactsdetail.ContactDetailActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/8/14  10:19
 * @desc ${TODD}
 */

public class ContactOrganFragment extends Fragment {
    @BindView(R.id.expand_list_view)
    ExpandableListView expandListView;
    Unbinder unbinder;

    // 组数据
    private List<String> gData;
    // 二级列表数据
    private List<List<Contacts>> iData;
    private OrganExpandListAdapter expandListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_organ, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /** 1. 对联系人进行分类 */
                List<Contacts> contactsList = DataSupport.order("Department asc").find(Contacts.class);
                iData = new ArrayList<>();

                Set set = new HashSet();
                for (Contacts bean : contactsList) {
                    String department = bean.getDepartment();
                    if (set.contains(department)) {
                        // 已经包含此分类, 不做处理
                    } else {
                        set.add(department);
                        iData.add(DataSupport.where("Department = ?", department).find(Contacts.class));
                    }
                }

                /** 2. 初始化组数据与二级列表数据 */
                gData = new ArrayList<>();
                for (int i = 0; i < iData.size(); i++) {
                    gData.add(iData.get(i).get(0).getDepartment());
                }

                e.onNext(true);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        expandListAdapter = new OrganExpandListAdapter(getContext(), gData, iData);
                        expandListView.setAdapter(expandListAdapter);

                        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                                ContactDetailActivity.startActivity(getContext(), (Contacts) expandListAdapter.getChild(groupPosition, childPosition));
                                return true;
                            }
                        });
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
