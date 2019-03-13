package cn.zhengweiyi.weiyichild;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {

    private String[] tabTitle;
    private List<Fragment> fragmentList;

    public TabAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] tabTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabTitle = tabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
