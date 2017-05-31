package com.powercn.grentechtaxi.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.view.UserDialog;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CancelOrderDialog extends UserDialog implements View.OnClickListener {
    private TextView tvSub;
    private TextView tvContact;
    public CancelOrderDialog(Context context, int dialogres, String title) {
        super(context, dialogres, title);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        tvSub=(TextView)findViewById(R.id.tv_order_sub);
        tvContact=(TextView)findViewById(R.id.tv_order_contact);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void iniPositiveButton() {

    }

    public void setOnCliclByContact(View.OnClickListener onClickListener)
    {
        this.tvContact.setOnClickListener(onClickListener);
    }

    public void setOnCliclBySub(View.OnClickListener onClickListener)
    {
        this.tvSub.setOnClickListener(onClickListener);
    }
}
