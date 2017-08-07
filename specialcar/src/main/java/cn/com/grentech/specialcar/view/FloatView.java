package cn.com.grentech.specialcar.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import cn.com.grentech.specialcar.SysApplication;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/7/28.
 */

public class FloatView {
    private static String tag = FloatView.class.getName();
    private Context context;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View view;
    private int resLayout;
    private static FloatView handle;

    private FloatView(Context context, int resLayout) {
        this.context = context;
        this.resLayout = resLayout;
    }

    public static FloatView Create(Context context, int resLayout) {
        if (handle == null) {
            StringUnit.println(tag, "第一次创建悬浮窗");
            handle = new FloatView(context, resLayout);
            handle.init();
        }

        return handle;
    }

    public static FloatView getInstance() {
        return handle;
    }

    private void init() {
        try {
            View view = LayoutInflater.from(context).inflate(resLayout, null);
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            view.setLayoutParams(layoutParams);
            windowManager.addView(view, layoutParams);
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }


    }
}
