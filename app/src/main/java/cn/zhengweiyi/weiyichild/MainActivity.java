/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;
import cn.zhengweiyi.weiyichild.fragment.MainFragment;
import cn.zhengweiyi.weiyichild.fragment.MyFragment;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnFragmentInteractionListener,MyFragment.OnFragmentInteractionListener {

    private TabLayout tab;
    private ViewPager pager;
    private List<Fragment> fragmentList;
    private int REQUEST_CODE_SCAN = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StatusBarUtil.setStatusBarMode(this, true, R.color.colorPrimaryDark);

        // 初始化界面
        initViews();
        /* 设置fragment适配器TabAdapter */
        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new MyFragment());
        pager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragmentList));

        /* Tab与ViewPager绑定 */
        tab.setupWithViewPager(pager);

    }

    /**
     * 设置菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 创建菜单的点击事件
     *
     * @param item 点击的菜单
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scanner:
                // 权限管理，判断是否有相机和读取文件存储权限，没有则申请权限
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
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

                                Toast.makeText(MainActivity.this, "没有权限无法扫描哟", Toast.LENGTH_SHORT).show();
                            }
                        }).start();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(this, "扫描结果：" + content, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        this.pager = findViewById(R.id.viewPager);
        this.tab = findViewById(R.id.tab);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
