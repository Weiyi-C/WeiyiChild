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
    private GridView gridView;
    private List<GridBean> gridDataList;
    private GridBean gridBean;
    private GridAdapter gridAdapter;

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

        /* GridView设置 */
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.fragment_main, null);
        gridView = view.findViewById(R.id.gridView);
        Log.d("获取gridView", String.valueOf(gridView));
        gridDataList = new ArrayList<>();
        // 初始化数据
        initData();
        // 实例化适配器
        gridAdapter = new GridAdapter(gridDataList, this);
        // 设置适配器
        Log.d("对象gridAdapter的第一个view", String.valueOf(gridAdapter.getItem(0)));
        gridView.setAdapter(gridAdapter);

    }

    /* 初始化界面 */
    private void initViews() {
        this.pager = findViewById(R.id.viewPager);
        this.tab = findViewById(R.id.tab);
    }

    /* 初始化数据*/
    private void initData() {
        // 图标
        int icon[] = { R.drawable.ic_function_anquan, R.drawable.ic_function_shipu };
        // 功能名字
        String name[] = { "安全接送", "每日食谱" };
        for (int i = 0; i < icon.length; i++) {
            gridBean = new GridBean(icon[i], name[i]);
            gridDataList.add(gridBean);
        }
        Log.d("数组gridDataList的第一条数据", String.valueOf(gridDataList.get(0).getName()));
    }

}
