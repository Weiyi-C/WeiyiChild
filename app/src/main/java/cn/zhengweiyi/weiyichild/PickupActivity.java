/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;
import java.util.Objects;

import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;

public class PickupActivity extends AppCompatActivity {

    private int REQUEST_CODE_SCAN = 111;

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
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layoutButtonPickup:
                    // 权限管理，判断是否有相机和读取文件存储权限，没有则申请权限
                    AndPermission.with(PickupActivity.this)
                            .runtime()
                            .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                            .onGranted(new Action<List<String>>() {
                                @Override
                                public void onAction(List<String> permissions) {
                                    Intent intent = new Intent(PickupActivity.this, CaptureActivity.class);
                                    ZxingConfig config = new ZxingConfig();
                                    // config.setPlayBeep(false);//是否播放扫描声音 默认为true
                                    // config.setShake(false);//是否震动  默认为true
                                    // config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                                    config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                                    // config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                                    config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                                    config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                    startActivityForResult(intent, REQUEST_CODE_SCAN);
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
                    break;
                case R.id.layoutButtonSend:
                    break;
            }
        }
    };
}
