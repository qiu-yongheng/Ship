package com.kc.shiptransport.mvp.partition;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.db.threadsand.Layered;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnDialogOkClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.DBUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.PatternUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/7  10:20
 * @desc 施工分区
 */

public class PartitionFragment extends Fragment implements PartitionContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_prefix)
    EditText etPrefix;
    @BindView(R.id.et_start_num)
    EditText etStartNum;
    @BindView(R.id.et_end_num)
    EditText etEndNum;
    @BindView(R.id.tv_construction_stratification)
    TextView tvConstructionStratification;
    private PartitionActivity activity;
    private PartitionAdapter adapter;
    private PartitionContract.Presenter presenter;
    private ConstructionBoat boat;
    private int layoutID = 0;
    private String layoutName = "";
    private List<Layered> arr;
    private int usableHeightPrevious = 0;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean isPush = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partition, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        presenter.getList(boat.getShipNum());
        return view;
    }

    @Override
    public void initViews(final View view) {
        frameLayoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //possiblyResizeChildOfContent(view);
            }
        });


        // 设置标题
        setHasOptionsMenu(true);
        activity = (PartitionActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_partition);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        String prefix = SharePreferenceUtil.getString(getContext(), SettingUtil.SP_KEY_PREFIX, "");
        if (!TextUtils.isEmpty(prefix)) {
            etPrefix.setText(prefix);
        }

        // 设置当前施工船舶
        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
            int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
            boat = all.get(position - 1);
        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String shipAccount = lists.get(0).getShipAccount();

                List<ConstructionBoat> boats = DataSupport.where("ShipNum = ?", shipAccount).find(ConstructionBoat.class);
                if (!boats.isEmpty()) {
                    boat = boats.get(0);
                }
            }
        }
    }

    /**
     * 重新设置界面的可用高度
     *
     * @param view
     */
    private void possiblyResizeChildOfContent(View view) {
        int usableHeightNow = computeUsableHeight(view);
        LogUtil.d("界面高度: " + usableHeightNow);
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = view.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard + 60;
            }
            view.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 获取可以被开发者用以显示内容的高度
     *
     * @return
     */
    private int computeUsableHeight(View view) {
        Rect r = new Rect();
        view.getWindowVisibleDisplayFrame(r);

        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.partition, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_delete_all:
                activity.showDailog("删除所有分区", "确定要删除所有分区吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 删除全部分区
                        DataSupport.deleteAll(PartitionNum.class);
                        if (adapter != null) {
                            adapter.panelLength = 0;
                            adapter.setDates(new ArrayList<PartitionNum>());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
            case R.id.action_remark:
                activity.showDailog("使用示例", getResources().getString(R.string.desc_partition), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {

        /** 新增分区 */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutID != 0 && !TextUtils.isEmpty(layoutName)) {
                    adapter.add(adapter.list.size(), layoutID, layoutName);
                    recyclerview.smoothScrollToPosition(adapter.list.size());
                } else {
                    ToastUtil.tip(getContext(), "请先填写施工分层");
                }
            }
        });

        /** 施工分层 */
        tvConstructionStratification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arr == null || arr.isEmpty()) {
                    arr = DataSupport.order("SortNum asc").find(Layered.class);
                }
                String[] data = new String[arr.size()];
                for (int i = 0; i < arr.size(); i++) {
                    data[i] = arr.get(i).getLayerName();
                }

                activity.showSingleNoButtonDailog(data, "选择施工分层", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, new OnDialogOkClickListener() {
                    @Override
                    public void onOK(Object data) {
                        // 分层ID
                        layoutID = arr.get((int) data).getItemID();
                        layoutName = arr.get((int) data).getLayerName();
                        tvConstructionStratification.setText(layoutName);
                    }
                });
            }
        });

        /** 生成 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 输入的文字 */
                final String prefix = etPrefix.getText().toString().trim();
                final String startNum = etStartNum.getText().toString().trim();
                final String endNum = etEndNum.getText().toString().trim();
                Integer start = 0;
                Integer end = 0;
                // 标记是否是char
                boolean isChar = false;


                if (TextUtils.isEmpty(prefix)) {
                    ToastUtil.tip(getContext(), "请填写分区前缀");
                } else if (!prefix.contains("#")) {
                    ToastUtil.tip(getContext(), "分区前缀需包含至少一个 # 号");
                } else if (PatternUtil.appearNumber(prefix, "#") > 2) {
                    ToastUtil.tip(getContext(), "分区前缀至多只能输入两个 # 号");
                } else if (TextUtils.isEmpty(startNum)) {
                    ToastUtil.tip(getContext(), "请填写开始数");
                } else if (TextUtils.isEmpty(endNum)) {
                    ToastUtil.tip(getContext(), "请填写结束数");
                } else if (TextUtils.isEmpty(layoutName)) {
                    ToastUtil.tip(getContext(), "请选择施工分层");
                } else {

                    /** ---------------------------------------------------------------------------- */
                    if (PatternUtil.patternNumber(startNum) && PatternUtil.patternNumber(endNum)) {
                        /** 数字 */
                        start = Integer.valueOf(startNum);
                        end = Integer.valueOf(endNum);
                        if (start > end) {
                            ToastUtil.tip(getContext(), "开始数不能大于结束数");
                            return;
                        }
                    } else if (startNum.length() != 1 && endNum.length() != 1) {
                        /** 只能输入一个字符 */
                        ToastUtil.tip(getContext(), "开始数与结束数只能填写一个字符");
                        return;
                    } else if (PatternUtil.patternCharOne(startNum) && PatternUtil.patternCharOne(endNum) && PatternUtil.appearNumber(prefix, "#") != 1) {
                        ToastUtil.tip(getContext(), "开始数与结束数只能输入一个字符, 请修改分区前缀中的 # 个数为1个");
                        return;
                    } else if (PatternUtil.patternCharOne(startNum) && PatternUtil.patternCharOne(endNum) && PatternUtil.appearNumber(prefix, "#") == 1) {
                        isChar = true;
                        if ((int) startNum.toCharArray()[0] > (int) endNum.toCharArray()[0]) {
                            ToastUtil.tip(getContext(), "开始数不能大于结束数");
                            return;
                        }
                    } else {
                        ToastUtil.tip(getContext(), "开始数与结束数, 只能输入1-9, a-z之间的数");
                        return;
                    }


                    final boolean finalIsChar = isChar;
                    final Integer finalStart = start;
                    final Integer finalEnd = end;
                    try {
                        // 缓存分区前缀
                        SharePreferenceUtil.saveString(getContext(), SettingUtil.SP_KEY_PREFIX, prefix);

                        if (finalIsChar) {
                            /** 字符 */
                            char[] startChars = startNum.toCharArray();
                            int startChar = (int) startChars[0];
                            char[] endChars = endNum.toCharArray();
                            int endChar = (int) endChars[0];

                            for (int j = startChar; j <= endChar; j++) {
                                PartitionNum num = new PartitionNum();
                                num.setUserAccount(boat.getShipNum());
                                char c = (char) j;
                                num.setNum(prefix.replace('#', c).toLowerCase());
                                num.setLayoutID(layoutID);
                                num.setLayoutName(layoutName);
                                num.save();
                            }

                        } else {
                            /** 数字 */
                            for (int j = finalStart; j <= finalEnd; j++) {
                                // 创建一个新的数据, 保存用户名
                                PartitionNum num = new PartitionNum();
                                num.setUserAccount(boat.getShipNum());
                                String format = String.format("%0" + PatternUtil.appearNumber(prefix, "#") + "d", j);
                                StringBuffer sb = new StringBuffer();
                                for (int n = 0; n < PatternUtil.appearNumber(prefix, "#"); n++) {
                                    sb.append("#");
                                }
                                num.setNum(prefix.replaceAll(sb.toString(), format).toLowerCase());
                                num.setLayoutID(layoutID);
                                num.setLayoutName(layoutName);
                                num.save();
                            }
                        }

                        List<PartitionNum> numList = DataSupport.where("userAccount = ? and num is not null and num != ?", boat.getShipNum(), "").find(PartitionNum.class);
                        adapter.setDates(numList);
                        adapter.notifyDataSetChanged();

                        // 如果输入法在窗口上已经显示，则隐藏，反之则显示
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        ToastUtil.tip(getContext(), "开始数与结束数必须输入数字");
                    }


                }
            }
        });
    }

    @Override
    public void setPresenter(PartitionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示施工分区
     *
     * @param list
     */
    @Override
    public void showList(List<PartitionNum> list) {
        // 回显施工分层
        tvConstructionStratification.setText(list.get(0).getLayoutName());
        layoutID = list.get(0).getLayoutID();
        layoutName = list.get(0).getLayoutName();

        if (adapter == null) {
            adapter = new PartitionAdapter(getActivity(), list, boat.getShipNum());
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(final View view, final int position, int... type) {
                    switch (type[0]) {
                        case 0:
                            // 选择施工分层
                            selectBed((Button) view, position);
                            break;
                        case 1:
                            // 更新数据命名重复状态
                            isRepeat();
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, final int position) {
                    activity.showDailog("删除", "是否删除该抛砂分区", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 删除数据
                            adapter.delete(position);
                        }
                    });
                }
            });

            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 选择施工分层
     *
     * @param view
     * @param position
     */
    private void selectBed(final Button view, final int position) {
        String num = adapter.list.get(position).getNum();
        if (TextUtils.isEmpty(num)) {
            ToastUtil.tip(getContext(), "请先填写施工panel");
            return;
        }


        // 给panel选择施工分层
        if (arr == null || arr.isEmpty()) {
            arr = DataSupport.order("SortNum asc").find(Layered.class);
        }
        String[] data = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            data[i] = arr.get(i).getLayerName();
        }

        activity.showSingleNoButtonDailog(data, "选择施工分层", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, new OnDialogOkClickListener() {
            @Override
            public void onOK(Object data) {
                PartitionNum partitionNum = adapter.list.get(position);
                int layoutID = arr.get((int) data).getItemID();
                String layoutName = arr.get((int) data).getLayerName();

                partitionNum.setLayoutID(layoutID);
                partitionNum.setLayoutName(layoutName);
                partitionNum.save();

                Button btnBed = view;
                btnBed.setText(layoutName);
                btnBed.setBackgroundColor(getResources().getColor(R.color.et_bg));
            }
        });
    }

    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //        getFocus();
    //    }
    //
    //    private void getFocus() {
    //        if (getView() != null) {
    //            getView().setFocusable(true);
    //            getView().setFocusableInTouchMode(true);
    //            getView().requestFocus();
    //            getView().setOnKeyListener(new View.OnKeyListener() {
    //
    //                @Override
    //                public boolean onKey(View v, int keyCode, KeyEvent event) {
    //                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
    //                        // 监听到返回按钮点击事件
    //                        warn();
    //                        return true;// 未处理
    //                    }
    //                    return false;
    //                }
    //            });
    //        }
    //    }

    private boolean warn() {
        // 验证施工panel长度是否一致
        List<PartitionNum> list = DataSupport.where("tag = ? and num is not null and num != ? and userAccount = ?", "0", "", boat.getShipNum()).find(PartitionNum.class);

        // 验证施工panel是否都填写了施工分层
        List<PartitionNum> list1 = DataSupport.where("num is not null and num != ? and userAccount = ? and layoutID == ?", "", boat.getShipNum(), String.valueOf(0)).find(PartitionNum.class);

        /** 验证是否有重复命名数据 */
        List<Map> maps = isRepeat();

        if (!list.isEmpty()) {
            activity.showDailog("提示", "施工panel长度必须一致才能进行提交, 请修改红色标注的施工panel\n\n当前待修改数: " + list.size() + "个", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } else if (!list1.isEmpty()) {
            activity.showDailog("提示", "还有" + list1.size() + "个施工panel未填写施工分层", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } else if (!maps.isEmpty()) {
            activity.showDailog("提示", "有" + maps.size() + "个施工panel命名重复, 请修改", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } else {
            return true;
        }

        return false;
    }

    /**
     * 获取重复命名数据
     *
     * @return
     */
    @NonNull
    private List<Map> isRepeat() {
        ContentValues v = new ContentValues();
        v.put("nameTag", 0);
        int ii = DataSupport.updateAll(PartitionNum.class, v);

        LogUtil.d("还原panel状态数量: " + ii);


        // 验证是否有重命名的panel
        Cursor cursor = DataSupport.findBySQL("select * from PartitionNum where userAccount = " + "\"" + boat.getShipNum() + "\"" + " and num in (select num from PartitionNum group by num having count(num) > 1)");

        List<Map> maps = DBUtil.queryListMap(cursor);

        // 更新命名重合的tag
        StringBuffer stringBuffer = new StringBuffer();
        ContentValues values = new ContentValues();
        values.put("nameTag", 1);
        for (Map map : maps) {
            String num = (String) map.get("num");
            int i = DataSupport.updateAll(PartitionNum.class, values, "num = ? and userAccount = ?", num, boat.getShipNum());
            stringBuffer.append("命名重合panel: " + num + ", 条数: " + i + "\n");
        }
        LogUtil.d(stringBuffer.toString());

        // 更新显示
        List<PartitionNum> numList = DataSupport.where("userAccount = ? and num is not null and num != ?", boat.getShipNum(), "").find(PartitionNum.class);
        adapter.setDates(numList);
        adapter.notifyDataSetChanged();
        return maps;
    }

    public boolean onBackPressed() {
        return warn();
    }
}
