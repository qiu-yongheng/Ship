package com.kc.shiptransport.mvp.scannerimgselect;

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
 * @time 2017/7/1 11:16
 * @desc ${TODO}
 */

public class ScannerImgSelectActivity extends BaseActivity{

    private static final String TYPEID = "TYPEID";
    private static final String SUBID = "SUBID";
    private static final String TITLE = "TITLE";
    private static final String SHIPACCOUNT = "SHIPACCOUNT";
    private static final String ATTACHMENTCOUNT = "ATTACHMENTCOUNT";
    private static final String DEFALUTATTACHMENTCOUNT = "DEFALUTATTACHMENTCOUNT";
    private static final String P_TYPE = "P_TYPE";
    private static final String ISFINSHRECEPTIONSANDATTACHMENT = "ISFINSHRECEPTIONSANDATTACHMENT";

    private ScannerImgSelectFragment fragment;
    public int mTypeID;
    public int mSubID;
    public String mTitle;
    public String mShipAccount;
    public int mAttachmentCount;
    public int mDefaulAttachmentCount;
    public int p_type;
    public int isFinshReceptionSandAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        mTypeID = bundle.getInt(TYPEID);
        mSubID = bundle.getInt(SUBID);
        mTitle = bundle.getString(TITLE);
        mShipAccount = bundle.getString(SHIPACCOUNT);
        mAttachmentCount = bundle.getInt(ATTACHMENTCOUNT);
        mDefaulAttachmentCount = bundle.getInt(DEFALUTATTACHMENTCOUNT);
        p_type = bundle.getInt(P_TYPE);
        isFinshReceptionSandAttachment = bundle.getInt(ISFINSHRECEPTIONSANDATTACHMENT);

        if (savedInstanceState != null) {
            fragment = (ScannerImgSelectFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ScannerImgSelectFragment");
        } else {
            fragment = new ScannerImgSelectFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ScannerImgSelectPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ScannerImgSelectFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, int typeID, int subID, String title, String shipAccount, int AttachmentCount, int DefalutAttachmentCount, int p_type, int isFinshReceptionSandAttachment) {
        Intent intent = new Intent(context, ScannerImgSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPEID, typeID);
        bundle.putInt(SUBID, subID);
        bundle.putString(TITLE, title);
        bundle.putString(SHIPACCOUNT, shipAccount);
        bundle.putInt(ATTACHMENTCOUNT, AttachmentCount);
        bundle.putInt(DEFALUTATTACHMENTCOUNT, DefalutAttachmentCount);
        bundle.putInt(P_TYPE, p_type);
        bundle.putInt(ISFINSHRECEPTIONSANDATTACHMENT, isFinshReceptionSandAttachment);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
