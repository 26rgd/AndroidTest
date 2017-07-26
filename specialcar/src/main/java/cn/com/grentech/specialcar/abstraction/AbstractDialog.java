package cn.com.grentech.specialcar.abstraction;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AbstractDialog extends AlertDialog {
    protected AbstractDialog(Context context) {
        super(context);
    }

    protected AbstractDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected AbstractDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setPostion(int x,int y)
    {
        Window window=this.getWindow();
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.x=x;
        params.y=y;
        this.setCanceledOnTouchOutside(true);
        window.setAttributes(params);
    }
}
