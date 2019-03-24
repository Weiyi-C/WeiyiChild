package cn.zhengweiyi.weiyichild.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bravin.btoast.BToast;

import java.util.Objects;

import cn.zhengweiyi.weiyichild.AboutActivity;
import cn.zhengweiyi.weiyichild.MainActivity;
import cn.zhengweiyi.weiyichild.MyApplication;
import cn.zhengweiyi.weiyichild.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("");
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initView(View view) {
        // 获取View
        View userInfo = view.findViewById(R.id.layoutUserInfo);
        View verify = view.findViewById(R.id.layoutListVerify);
        View myChild = view.findViewById(R.id.layoutListMyChild);
        View phone = view.findViewById(R.id.layoutListPhone);
        View setting = view.findViewById(R.id.layoutListSetting);
        View about = view.findViewById(R.id.layoutListAbout);
        // 设置点击监听
        userInfo.setOnClickListener(onClickListener);
        verify.setOnClickListener(onClickListener);
        myChild.setOnClickListener(onClickListener);
        phone.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        about.setOnClickListener(onClickListener);
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layoutUserInfo:
                    BToast.error(view.getContext())
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                            .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM)
                            .text(R.string.user_name)
                            .show();
                    break;
                case R.id.layoutListVerify:
                    BToast.normal(view.getContext())
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                            .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                            .text(getResources().getString(R.string.click) + " " + getResources().getString(R.string.verify))
                            .show();
                    break;
                case R.id.layoutListMyChild:
                    BToast.normal(view.getContext())
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                            .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                            .text(getResources().getString(R.string.click) + " " + getResources().getString(R.string.list_child))
                            .show();
                    break;
                case R.id.layoutListPhone:
                    BToast.normal(view.getContext())
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                            .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                            .text(getResources().getString(R.string.click) + " " + getResources().getString(R.string.list_phone))
                            .show();
                    break;
                case R.id.layoutListSetting:
                    BToast.normal(view.getContext())
                            .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                            .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                            .text(getResources().getString(R.string.click) + " " + getResources().getString(R.string.list_setting))
                            .show();
                    break;
                case R.id.layoutListAbout:
                    Intent intent = new Intent(getContext(), AboutActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
