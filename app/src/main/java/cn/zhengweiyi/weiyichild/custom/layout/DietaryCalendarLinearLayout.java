/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.haibin.calendarview.CalendarLayout;

import cn.zhengweiyi.weiyichild.TabAdapter;
import cn.zhengweiyi.weiyichild.fragment.DietaryFragment;

public class DietaryCalendarLinearLayout extends LinearLayout implements CalendarLayout.CalendarScrollView {

    private TabAdapter mAdapter;

    public DietaryCalendarLinearLayout(Context context) {
        super(context);
    }

    public DietaryCalendarLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 如果你想让下拉无效，return false
     *
     * @return isScrollToTop
     */
    @Override
    public boolean isScrollToTop() {
        if (mAdapter == null) {
            if (getChildCount() > 1 && getChildAt(1) instanceof ViewPager) {
                ViewPager viewPager = (ViewPager) getChildAt(1);
                mAdapter = (TabAdapter) viewPager.getAdapter();
            }
        }
        return mAdapter != null && ((DietaryFragment) mAdapter.getCurFragment()).isScrollTop();
    }

}
