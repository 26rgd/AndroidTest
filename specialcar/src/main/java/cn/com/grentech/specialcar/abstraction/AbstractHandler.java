package cn.com.grentech.specialcar.abstraction;

import android.os.Handler;

import java.lang.ref.WeakReference;

import cn.com.grentech.specialcar.activity.LoginActivity;

/**
 * Created by Administrator on 2017/6/14.
 */

public class AbstractHandler extends Handler {
    protected String tag=this.getClass().getName();
    protected  WeakReference<AbstractBasicActivity> mActivity;

    public AbstractHandler(AbstractBasicActivity activity) {
        mActivity = new WeakReference(activity);
    }

}
