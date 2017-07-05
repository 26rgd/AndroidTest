package cn.com.grentech.specialcar.common.unit;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Administrator on 2017/7/3.
 */

public class WakeLockUnit {
   private static String tag=WakeLockUnit.class.getName();
    public static PowerManager.WakeLock wakeLock = null;

    public static void acquireWakeLock(Context context)
    {
//        if (null == wakeLock)
//        {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //wakeLock = pm.newWakeLock(PowerManager.ON_AFTER_RELEASE | PowerManager.PARTIAL_WAKE_LOCK, "wakeLockUtil");
            wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "wakeLockUtil");
            // PARTIAL_WAKE_LOCK:保持CPU 运转，屏幕和键盘灯有可能是关闭的 -- 最常用,保持CPU运转
            // SCREEN_DIM_WAKE_LOCK：保持CPU 运转，允许保持屏幕显示但有可能是灰的，允许关闭键盘灯
            // SCREEN_BRIGHT_WAKE_LOCK：保持CPU 运转，允许保持屏幕高亮显示，允许关闭键盘灯
            // FULL_WAKE_LOCK：保持CPU 运转，保持屏幕高亮显示，键盘灯也保持亮度
            // ACQUIRE_CAUSES_WAKEUP：强制使屏幕亮起，这种锁主要针对一些必须通知用户的操作.
            // ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间

//        }
        if (null != wakeLock)
        {
            StringUnit.println(tag,"立即获取电源锁");
            wakeLock.acquire(1000); // 立即获取电源锁
            // wakeLock.acquire(2000); // 2秒后释放电源锁
        }
    }

    public static void releaseWakeLock()
    {
//        if (null != wakeLock)
//        {
//            wakeLock.release();
//            wakeLock = null;
//            StringUnit.println(tag,"释放电源锁");
//        }
    }

    public static void onPower(Context context)
    {
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.PARTIAL_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();
        mWakelock.release();
    }

}
