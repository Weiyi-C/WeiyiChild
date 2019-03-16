/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;
import cn.zhengweiyi.weiyichild.greenDao.DietaryLab;

public class DietaryActivity extends AppCompatActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnMonthChangeListener, View.OnClickListener {

    TextView mTextMonth;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    LinearLayout mLinearLayout;
    private int mYear;
    CalendarLayout mCalendarLayout;

    DietaryLab dietaryLab;
    List mDietaryList = new ArrayList();

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

        View back = findViewById(R.id.ic_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() throws ParseException {
        MyApplication app = (MyApplication) getApplication();
        app.initData();
        dietaryLab = new DietaryLab(app.getDaoSession().getDietaryDao());
        Log.d("读取数据库", "dietaryDao[1]：" + dietaryLab.getDietaryById(1L));
    }

    private void initView() {
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
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()) + " \u25bc");
        mYear = mCalendarView.getCurYear();
        mTextMonth.setText(mCalendarView.getCurMonth() + "月");  //  + mCalendarView.getCurDay() + "日"
        // mCalendarView.scrollToCurrent();                         // 默认选中“今天”
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
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
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonth.setText(calendar.getMonth() + "月");  // + calendar.getDay() + "日"
        mTextYear.setText(String.valueOf(calendar.getYear()) + " \u25bc");
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

        String selectDate = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
        mDietaryList = dietaryLab.getDietaryByDate(selectDate);

        Log.i("onDateSelected", calendar.getYear() +
                "-" + calendar.getMonth() + "-" + calendar.getDay() +
                " -- 点击：" + isClick + " -- 事件：" + calendar.getScheme() +
                " -- 食谱大小：" + mDietaryList.size());
    }

    @Override
    public void onYearChange(int year) {
        Toast.makeText(this, "您已经切换到了 "
                + String.valueOf(year) + " 年", Toast.LENGTH_SHORT).show();
        mTextMonth.setText(String.valueOf(year));
    }

    @Override
    public void onMonthChange(int year, int month) {
        Toast.makeText(this, "您已经切换到了 "
                + String.valueOf(month) + " 月", Toast.LENGTH_SHORT).show();
        mTextMonth.setText(String.valueOf(month));
        mTextYear.setText(String.valueOf(year));
    }
}
