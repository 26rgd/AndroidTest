package com.powercn.grentechdriver.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/4/11.
 */
public class RequestProgressDialog extends ProgressDialog {
    private String tag=this.getClass().getName();
    private boolean isRun=false;
    private int timeOut = 15 * 1000;

    public RequestProgressDialog(Context context) {
        super(context);
    }

    public RequestProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public  synchronized void show(String msg)
    {

        if(isRun==true)return;
        isRun=true;
        try {
            this.setMessage(msg);
            this.setCancelable(false);

            super.show();

            init();
        }catch (Exception e)
        {
            ErrorUnit.println(tag,e);
        }


    }

    public  synchronized void show(String msg,int timeOut)
    {
        if(isRun==true)return;
        isRun=true;
        try {
            this.timeOut=timeOut;
            this.setMessage(msg);
            this.setCancelable(false);
            this.show();
            init();
        }catch (Exception e)
        {
            ErrorUnit.println(tag,e);
        }


    }
    public synchronized void hide()
    {
        try {
            if(isRun==true)
            super.hide();

        }catch (Exception e)
        {
        }
        finally {
            isRun=false;
        }

    }
    public void init() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long st = System.currentTimeMillis();
                long ed = System.currentTimeMillis();
                try {
                    while ((ed - st) < timeOut && isRun==true) {
                        Thread.sleep(40);
                        ed = System.currentTimeMillis();
                    }
                } catch (Exception e) {
                }finally {
                    hide();
                    StringUnit.println(tag,"dialog thread end hide");
                }

            }
        });
        thread.start();
    }
}
