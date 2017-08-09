package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;
import com.powercn.grentechdriver.activity.Register1Activity;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.common.unit.ViewUnit;

import lombok.Getter;

import static android.R.attr.password;
import static com.powercn.grentechdriver.R.id.tv_login_register;

/**
 * Created by Administrator on 2017/8/8.
 */

public class SelectItem  extends AbstractChlidView {
    private TextView underLine;
    @Getter
    private TextView  title;
    @Getter
    private Boolean isSelect;

    public SelectItem(Activity activity, int res) {
        super(activity, res);
        initView();
    }

    public SelectItem(View cview) {
        super(cview);
        initView();

    }

    private void initView() {
        underLine = (TextView) findViewById(R.id.tv_item_select_underline);
        title = (TextView) findViewById(R.id.tv_item_select_hint);
        isSelect=false;
    }


    public void doSelect()
    {
        isSelect=true;
        title.setTextColor(ViewUnit.getColor(view.getContext(),R.color.textSelectColor));
        underLine.setVisibility(View.VISIBLE);
    }
    public void unSelect()
    {
        isSelect=false;
        title.setTextColor(ViewUnit.getColor(view.getContext(),R.color.textColorSelectCarNoFocus));
        underLine.setVisibility(View.INVISIBLE);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_item_select_hint:
//                doSelect();
//                break;
//
//
//
//        }
//    }
}
