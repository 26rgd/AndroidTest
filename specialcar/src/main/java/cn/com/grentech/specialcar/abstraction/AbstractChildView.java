package cn.com.grentech.specialcar.abstraction;

import android.view.View;

/**
 * Created by Administrator on 2017/6/9.
 */

public abstract class AbstractChildView {
    protected View view;
    public abstract View findViewById(int resId);
    public abstract View findIncludeViewById(int resId);

    public View getView() {
        return view;
    }
}
