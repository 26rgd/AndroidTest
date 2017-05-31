package cn.com.grentech.www.androidtest.adapter.listview;

import android.app.Activity;
import android.icu.text.IDNA;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import cn.com.grentech.www.androidtest.R;
import cn.com.grentech.www.androidtest.activity.ListViewActivity;
import cn.com.grentech.www.androidtest.adapter.AbstractAdpter;

import static cn.com.grentech.www.androidtest.R.id.tv_listview_item_line1;

/**
 * Created by Administrator on 2017/4/21.
 */

public class ListViewAdpter<T> extends AbstractAdpter {
    public ListViewAdpter(Activity activity, List data, int itemres) {
        super(activity, data, itemres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ListViewActivity.Info info = (ListViewActivity.Info) getItem(position);
        View view = super.getView(position, convertView, parent);
        CheckBox cb_listview_item_select = (CheckBox) view.findViewById(R.id.cb_listview_item_select);
        TextView tv_listview_item_line1 = (TextView) view.findViewById(R.id.tv_listview_item_line1);
        TextView tv_listview_item_line2 = (TextView) view.findViewById(R.id.tv_listview_item_line2);
        TextView tv_listview_item_line3 = (TextView) view.findViewById(R.id.tv_listview_item_line3);

        tv_listview_item_line1.setText(info.line1);
        tv_listview_item_line2.setText(info.line2);
        tv_listview_item_line3.setText(info.line3);
        cb_listview_item_select.setTag(info);
        if (info.select)
            cb_listview_item_select.setChecked(true);
        else
            cb_listview_item_select.setChecked(false);

        cb_listview_item_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListViewActivity.Info info=(ListViewActivity.Info )buttonView.getTag();
                info.select=isChecked;
            }
        });
        return view;
    }
}
