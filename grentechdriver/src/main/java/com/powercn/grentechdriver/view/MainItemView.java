package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;

import lombok.Getter;

/**
 * Created by Administrator on 2017/8/3.
 */

public class MainItemView extends AbstractChlidView {
    private ImageView ivIcon;
    @Getter
    private ImageView ivDetail;
    private TextView tvTitle;
    public MainItemView(Activity activity, int res) {
        super(activity, res);
        ivIcon=(ImageView)findViewById(R.id.iv_itemview_line1);
        ivDetail=(ImageView)findViewById(R.id.iv_itemview_line2);
        tvTitle=(TextView)findViewById(R.id.tv_item_title);
    }
    public void initTitle(String content)
    {
        tvTitle.setText(content);
    }
    public void initIcon(int resId)
    {
        ivIcon.setImageResource(resId);
    }
}
