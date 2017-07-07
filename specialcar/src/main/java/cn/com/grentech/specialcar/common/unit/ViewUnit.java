package cn.com.grentech.specialcar.common.unit;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.grentech.specialcar.R;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ViewUnit {
    private static String tag = ViewUnit.class.getName();
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

    public static String getResString(Context context, int resId) {
        Resources resources = context.getResources();
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
            return getDrawable(context, R.drawable.btn_back);
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
        if (number == null) number = "";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSms(Context context, String number) {
        if (number == null) number = "";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + number));

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            // intent.putExtra("sms_body","number");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSms(Context context, String number, String msg) {
        if (number == null) number = "";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + number));

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            intent.putExtra("sms_body", msg);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void systemShare(Context context, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.setPackage("com.tencent.mm");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    public static List<ResolveInfo> getShareTargets(Context activity) {
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm = activity.getPackageManager();
        return pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

    }

    public static void CreateShare(String title, String content, Activity activity, ResolveInfo appInfo) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName, appInfo.activityInfo.name));
            //这里就是组织内容了，
            // shareIntent.setType("text/plain");
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, content);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(shareIntent);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }


    public static Bundle getIntent(Activity activity)
    {
        Intent intent=activity.getIntent();
        Bundle bundle =intent.getExtras();
        return  bundle;
    }

    public static Calendar getCalendar(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getVersionCode(Context context)
    {
        try {
            PackageManager packageManager=context.getPackageManager();
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionCode;
        }catch (Exception e){ErrorUnit.println(tag,e);}

        return Integer.MAX_VALUE;
    }
}
