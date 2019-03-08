package cn.zhengweiyi.weiyichild.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cn.zhengweiyi.weiyichild.GridAdapter;
import cn.zhengweiyi.weiyichild.GridBean;
import cn.zhengweiyi.weiyichild.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SCREEN_WIDTH = "screenWidth";
    private static final String ARG_SCREEN_DENSITY = "screenDensity";

    // TODO: Rename and change types of parameters
    private int mScreenWidth;   // 屏幕宽度
    private float mDensity;     // 屏幕密度
    private GridView gridView;
    private List<GridBean> gridDataList;
    private GridBean gridBean;
    private GridAdapter gridAdapter;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Log.d("getArguments()方法的值为", String.valueOf(getArguments()));
        if (getArguments() != null) {
            mScreenWidth = getArguments().getInt(ARG_SCREEN_WIDTH);
            mDensity = getArguments().getFloat(ARG_SCREEN_DENSITY);
            Log.d("获取到的屏幕宽度和密度分别是 ", "宽: " + String.valueOf(mScreenWidth) + "px, 密度：" + String.valueOf(mDensity));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* 设置功能按钮GridView */
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = view.findViewById(R.id.gridView);
        gridDataList = new ArrayList<>();
        // 初始化数据
        initData();
        // 实例化适配器
        gridAdapter = new GridAdapter(gridDataList, getContext());
        // 为GridView设置适配器
        gridView.setAdapter(gridAdapter);
        // Log.d("屏幕的宽度为", String.valueOf(mScreenWidth));
        Log.d("功能按钮列宽为：", String.valueOf((mScreenWidth - 40 * mDensity)/3));
        gridView.setColumnWidth((int) ((mScreenWidth - 40 * mDensity)/3));
        return view;
    }

    // TODO: 重命名方法、更新参数并将方法关联到UI事件
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // TODO 实现 OnFragmentInteractionListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initData() {
        // 图标
        int icon[] = {R.drawable.ic_function_anquan, R.drawable.ic_function_shipu,
                R.drawable.ic_function_manage_info, R.drawable.ic_function_manage_class,
                R.drawable.ic_function_other};
        // 功能名字
        String name[] = {"安全接送", "每日食谱", "信息管理", "班级管理", "其他功能"};
        for (int i = 0; i < icon.length; i++) {
            gridBean = new GridBean(icon[i], name[i]);
            gridDataList.add(gridBean);
        }
        Log.d("数组gridDataList的第一条数据", String.valueOf(gridDataList.get(0).getName()));
    }
}
