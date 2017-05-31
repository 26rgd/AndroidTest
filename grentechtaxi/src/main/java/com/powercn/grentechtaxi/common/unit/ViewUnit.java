package com.powercn.grentechtaxi.common.unit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.powercn.grentechtaxi.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.powercn.grentechtaxi.R.id.tv_mainmap_cattype_netword;
import static com.powercn.grentechtaxi.R.id.tv_mainmap_cattype_taxi;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ViewUnit {

    public static int getDisplayWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;

    }

    public static int getDisplayHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;

    }

    public static String getResString(Context context,int resId)
    {
        Resources resources=context.getResources();
        return resources.getString(resId);

    }
    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getColor(activity, colorResId));
            }
        } catch (Exception e) {

        }
    }

    public static int getColor(Context context, int colorResId) {
        Resources resources = context.getResources();
        int color = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            color = resources.getColor(colorResId, null);
        else
            color = resources.getColor(colorResId);
        return color;
    }

    public static Drawable getDrawableId(Context context, int drawableId) {
        Resources resources = context.getResources();
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            drawable = resources.getDrawable(drawableId, null);
        else
            drawable = resources.getDrawable(drawableId);
        return drawable;
    }

    public static Drawable getDrawable(Context context, int bitMapResId) {
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, bitMapResId);
        Drawable drawable = new BitmapDrawable(resources, bitmap);
        return drawable;
    }

    public static Drawable getDrawable(Context context, String bitMapPaht) {
        try {
            File file = new File(bitMapPaht);
            InputStream inputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeFile(bitMapPaht);
            Resources resources = context.getResources();
            Drawable drawable = new BitmapDrawable(resources, bitmap);
//            Drawable drawable = BitmapDrawable.createFromPath(bitMapPaht);
            return drawable;
        } catch (Exception e) {
            return getDrawable(context, R.drawable.icon_head_mine);
        }

    }

    public static void setBackgroundDrawable(Context context, View view, int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ViewUnit.getDrawableId(context, drawableId));
        } else {
            view.setBackgroundDrawable(ViewUnit.getDrawableId(context, drawableId));
        }
    }

    public static void callPhone(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSms(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"+number));
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
