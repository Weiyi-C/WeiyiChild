/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bravin.btoast.BToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.zhengweiyi.weiyichild.bean.PickupHistory;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.custom.PickupHistoryRecyclerAdapter;
import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;
import cn.zhengweiyi.weiyichild.viewmodel.PickupViewModel;

public class PickupActivity extends AppCompatActivity implements PickupHistoryRecyclerAdapter.OnEmptyViewButtonClickListener {

    private int REQUEST_CODE_SCAN = 111;
    private static final String CODE_KEY = "codeString";

    MyApplication app;
    PickupViewModel pickupViewModel;
    PickupHistoryRecyclerAdapter recyclerAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

        Toolbar toolbar = findViewById(R.id.toolbar_pickup);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        StatusBarUtil.setStatusBarMode(this, false, R.color.themeDark);

        // 引用OnClick监听
        View pickup = findViewById(R.id.layoutButtonPickup);    // 接孩子按钮
        pickup.setOnClickListener(onClickListener);
        View send = findViewById(R.id.layoutButtonSend);        // 送孩子按钮
        send.setOnClickListener(onClickListener);

        // 获取 Application
        app = (MyApplication) this.getApplication();

        // 初始化 ViewModel
        pickupViewModel = new ViewModelProvider(this).get(PickupViewModel.class);

        // 设置 RecyclerView
        recyclerView = findViewById(R.id.pickupRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new PickupHistoryRecyclerAdapter(new ArrayList<>(), this);
        recyclerAdapter.setOnEmptyViewButtonClickListener(emptyViewButtonClickListener);
        recyclerView.setAdapter(recyclerAdapter);

        // 观察 LiveData
        pickupViewModel.getPickupHistoryLiveData().observe(this, pickupHistoryList -> {
            Log.d("PickupHistory", "获取到的接送记录" + pickupHistoryList);
            recyclerAdapter.refreshData(pickupHistoryList);
        });

        // 加载数据
        pickupViewModel.loadAll();
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.layoutButtonPickup) {
                // 权限管理，判断是否有相机和读取文件存储权限，没有则申请权限
                AndPermission.with(PickupActivity.this)
                        .runtime()
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intentScan = new Intent(PickupActivity.this, CaptureActivity.class);
                                ZxingConfig config = new ZxingConfig();
                                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intentScan.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intentScan, REQUEST_CODE_SCAN);
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Uri packageURI = Uri.parse("package:" + getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(PickupActivity.this, "没有权限无法扫描哟", Toast.LENGTH_SHORT).show();
                            }
                        }).start();
            } else if (id == R.id.layoutButtonSend) {
                // 实例化一个 Bundle
                Bundle bundleCode = new Bundle();
                Intent intentCode = new Intent(PickupActivity.this, CodeActivity.class);
                // 设置数据
                String codeString = app.ROOT_HOST + app.API_SEND_CHILD + "token=userToken"
                        + "&time=" + DateFormatUtil.DatetimeToStr(new Date());
                bundleCode.putString(CODE_KEY, codeString);
                // 把 Bundle 放入 intent
                intentCode.putExtra("Message", bundleCode);
                startActivity(intentCode);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                BToast.success(this)
                        .text(content)
                        .duration(BToast.DURATION_LONG)
                        .show();
            }
        }
    }

    public PickupHistoryRecyclerAdapter.OnEmptyViewButtonClickListener emptyViewButtonClickListener
            = new PickupHistoryRecyclerAdapter.OnEmptyViewButtonClickListener() {
        @Override
        public void onEmptyViewButtonClick() {
            pickupViewModel.initTestDataPickup();
        }
    };

    @Override
    public void onEmptyViewButtonClick() {
        Toast.makeText(this, "点击测试", Toast.LENGTH_SHORT).show();
    }
}
