package com.powercn.grentechdriver.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.powercn.grentechdriver.R;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by Administrator on 2017/3/27.
 */
@Getter
@Setter
public abstract class UserDialog {
    public Context getContext() {
        return context;
    }

    private Context context;
    private View view;
    private Builder builder = null;
    private DialogClick dialogClick;
    private AlertDialog alertDialog;



    public UserDialog(Context context, int dialogres, String title) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (View) inflater.inflate(dialogres, null);
        if (builder == null)
            builder = new Builder(context,R.style.AlertDialog);
        view.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        builder.setView(view);
        builder.setTitle(title);
        this.iniPositiveButton();
        initView();
        bindListener();
        initData();

    }

    public View findViewById(@IdRes int id) {
        return view.findViewById(id);
    }
    public void iniPositiveButton() {
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (dialogClick != null)
                    dialogClick.onClickConfiger(dialog, which, null);
                dialog.cancel();
            }
        });

        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public void show() {
//         R.style.AlertDialog
//        new AlertDialog.Builder(this,R.style.AlertDialog);
        alertDialog=this.builder.create();

        alertDialog.show();

        Window window= alertDialog.getWindow();
        window.getDecorView().setPadding(20,0,20,0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void cancle()
    {
        alertDialog.cancel();;

    }
    public void dismiss()
    {
        alertDialog.dismiss();
    }
    protected abstract void initView();

    protected abstract void bindListener();

    protected abstract void initData();

    public interface DialogClick {
        public void onClickConfiger(DialogInterface dialog, int which, Object data);
    }

}
