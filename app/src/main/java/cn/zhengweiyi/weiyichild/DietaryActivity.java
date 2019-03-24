/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bravin.btoast.BToast;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;
import cn.zhengweiyi.weiyichild.fragment.DietaryFragment;
import cn.zhengweiyi.weiyichild.greenDao.DietaryLab;

public class DietaryActivity extends AppCompatActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnMonthChangeListener,
        // DietaryFragment.OnSelectDateChangeListener,
        View.OnClickListener {

    TextView mTextMonth;                    // 顶部月份
    TextView mTextYear;                     // 顶部年份
    TextView mTextLunar;                    // 顶部阴历文本

    TextView mTextCurrentDay;

    CalendarView mCalendarView;             // 日历View

    RelativeLayout mRelativeTool;

    LinearLayout mLinearLayout;
    private int mYear;
    CalendarLayout mCalendarLayout;         // 日历布局

    private String selectDate;              // 当前选中日期

    DietaryLab dietaryLab;                  // 食谱数据库操作类

    private TabLayout tab;
    private String[] tabTitle;
    private ViewPager pager;
    private List<Fragment> fragmentList;
    private DietaryFragment dietaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietary);

        StatusBarUtil.setStatusBarMode(this, true, R.color.colorPrimary);

        try {
            initData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 绑定返回按钮事件
        View back = findViewById(R.id.ic_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 创建视图
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() throws ParseException {
        // 设置TabLayout标题
        int tab[] = {R.string.dietary_list};
        tabTitle = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            tabTitle[i] = getResources().getString(tab[i]);
        }

        // 实例化数据库操作类
        MyApplication app = (MyApplication) getApplication();
        // app.initData();
        dietaryLab = new DietaryLab(app.getDaoSession().getDietaryDao());
        Log.d("读取数据库", "dietaryDao[1]：" + dietaryLab.getDietaryById(1L));
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        // 绘制日历
        mTextMonth = findViewById(R.id.tv_month);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mLinearLayout = findViewById(R.id.tv_layout_year_lunar);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    // 提示点击进入年视图
                    BToast.normal(DietaryActivity.this)
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_LEFT)
                            .target(mLinearLayout).layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                            .text(R.string.click_calendar_month)
                            .show();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonth.setText(String.valueOf(mYear));
            }
        });
        mTextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    BToast.normal(DietaryActivity.this)
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_LEFT)
                            .target(mLinearLayout).layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                            .text(R.string.click_calendar_month)
                            .show();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonth.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()) + " \u25bc");
        mYear = mCalendarView.getCurYear();
        mTextMonth.setText(mCalendarView.getCurMonth() + "月");  //  + mCalendarView.getCurDay() + "日"
        // mCalendarView.scrollToCurrent();                         // 默认选中“今天”
        mTextLunar.setText(R.string.calendar_today);
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        this.pager = findViewById(R.id.viewPager);
        this.tab = findViewById(R.id.tabLayout);

        /* 设置fragment适配器TabAdapter */
        dietaryFragment = new DietaryFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(dietaryFragment);
        pager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragmentList, tabTitle));

        /* Tab与ViewPager绑定 */
        tab.setupWithViewPager(pager);
    }

    @Override
    public void onClick(View v) {
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    /**
     * 日历日期选择
     *
     * @param calendar calendar
     * @param isClick  是否被点击
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        // 格式化选择日期
        String selectMonth;
        String selectDay;
        if (calendar.getMonth() < 10) {
            selectMonth = "0" + calendar.getMonth();
        } else {
            selectMonth = String.valueOf(calendar.getMonth());
        }
        if (calendar.getDay() < 10) {
            selectDay = "0" + calendar.getDay();
        } else {
            selectDay = String.valueOf(calendar.getDay());
        }
        selectDate = calendar.getYear() + "-" + selectMonth + "-" + selectDay;

        // 设置头部日期显示
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonth.setText(calendar.getMonth() + "月");  // + calendar.getDay() + "日"
        mTextYear.setText(String.valueOf(calendar.getYear()) + " \u25bc");
        if (Objects.equals(selectDate, DateFormatUtil.DateToStr(new Date()))) {
            mTextLunar.setText(R.string.calendar_today);
        } else {
            mTextLunar.setText(calendar.getLunar());
        }
        mYear = calendar.getYear();

        dietaryFragment.changeDate(selectDate);

        Log.i("onDateSelected", selectDate +
                " -- 点击：" + isClick + " -- 事件：" + calendar.getScheme());
    }

    @Override
    public void onYearChange(int year) {
        mTextMonth.setText(String.valueOf(year));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month) {
        mTextMonth.setText(String.valueOf(month) + "月");
        mTextYear.setText(String.valueOf(year) + " \u25bc");
        mTextLunar.setText("");
    }
}
