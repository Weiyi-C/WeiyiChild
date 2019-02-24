package cn.zhengweiyi.weiyichild;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

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

        /*设置Adapter*/
        fragmentList =new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new MyFragment());
        pager.setAdapter(new TabAdapter(getSupportFragmentManager(),fragmentList));

        /*Tab与ViewPager绑定*/
        tab.setupWithViewPager(pager);





    }
}
