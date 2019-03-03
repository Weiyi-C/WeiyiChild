package cn.zhengweiyi.weiyichild;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhengweiyi.weiyichild.fragment.MainFragment;
import cn.zhengweiyi.weiyichild.fragment.MyFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化界面
        initViews();
        /* 设置fragment适配器TabAdapter */
        fragmentList =new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new MyFragment());
        pager.setAdapter(new TabAdapter(getSupportFragmentManager(),fragmentList));

        /* Tab与ViewPager绑定 */
        tab.setupWithViewPager(pager);

    }

    /* 初始化界面 */
    private void initViews() {
        this.pager = findViewById(R.id.viewPager);
        this.tab = findViewById(R.id.tab);
    }

}
