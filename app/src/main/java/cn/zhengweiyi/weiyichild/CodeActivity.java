/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.Objects;

import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;

public class CodeActivity extends AppCompatActivity {

    private static final String CODE_KEY = "codeString";
    String codeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        StatusBarUtil.setStatusBarMode(this, false, R.color.themeDark);
        // 调整窗口屏幕亮度
        int brightness = 255;
        changeAppBrightness(brightness);

        // 获取传入数据
        getData();

        ImageView bitmap = findViewById(R.id.imageCode);

        /*
         * contentEtString：字符串内容
         * w：图片的宽
         * h：图片的高
         * logo：不需要logo的话直接传null
         * */
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap.setImageBitmap(CodeCreator.createQRCode(codeString, 618, 618, logo));
    }

    // 获取 Intent 传入的值
    private void getData() {
        Intent intent = getIntent();
        // 从 Intent 取出 bundle
        Bundle bundle = intent.getBundleExtra("Message");
        // 获取数据
        codeString = bundle.getString(CODE_KEY);
    }

    private void changeAppBrightness(int brightness) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }
}
