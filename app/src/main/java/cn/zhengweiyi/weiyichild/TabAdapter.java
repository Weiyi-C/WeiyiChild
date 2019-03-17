package cn.zhengweiyi.weiyichild;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {

    private String[] tabTitle;
    private List<Fragment> fragmentList;
    private Fragment mCurFragment;

    public TabAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] tabTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabTitle = tabTitle;
    }

    public Fragment getCurFragment() {
        return mCurFragment;
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

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        if (object instanceof Fragment) {
            mCurFragment = (Fragment) object;
        }
    }
}
