package cn.com.grentech.specialcar.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractAdapter;
import cn.com.grentech.specialcar.entity.DriverInfo;
import cn.com.grentech.specialcar.entity.UserAll;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/6/15.
 */

public class DriverAdapter extends AbstractAdapter implements TextWatcher , View.OnFocusChangeListener{
    @Getter
    @Setter
    private DriverInfo currentItem;
    public DriverAdapter(Context context, List data, int itemres) {
        super(context, data, itemres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView line1 = (TextView) view.findViewById(R.id.tv_userinfo_name);
        final EditText line2 = (EditText) view.findViewById(R.id.edt_content);
        final TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        final DriverInfo item = (DriverInfo) getItem(position);
        currentItem=item;
        line1.setText(item.getName());
        line2.setText(item.getValue());
        line2.setTag(item);
       // line2.addTextChangedListener(this);
        tv_content.setTag(item);
        line2.setOnClickListener(this);
        tv_content.setOnClickListener(this);

        line2.setOnClickListener(this);
        line2.setOnFocusChangeListener(this);
        return view;
    }

    public void update(UserAll info) {
        getData().clear();
        ;
        DriverInfo.fname.setValue(info.getName());
        getData().add(DriverInfo.fname);

        DriverInfo.phone.setValue(info.getPhone());
        getData().add(DriverInfo.phone);

        DriverInfo.address.setValue(info.getAddress());
        getData().add(DriverInfo.address);

        DriverInfo.licenseNo.setValue(info.getLicenseNo());
        getData().add(DriverInfo.licenseNo);

        DriverInfo.carNo.setValue(info.getCarNo());
        getData().add(DriverInfo.carNo);

        DriverInfo.carType.setValue(info.getCarType());
        getData().add(DriverInfo.carType);

        DriverInfo.carRow.setValue(String.valueOf(info.getSiteCount()));
        getData().add(DriverInfo.carRow);
        notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        DriverInfo userInfoItem = currentItem;
        userInfoItem.setValue(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (hasFocus) {
            DriverInfo item = (DriverInfo) v.getTag();
            setCurrentItem(item);
            editText.setSelection(editText.getText().toString().length());
            editText.addTextChangedListener(this);
        } else {
            editText.setSelection(editText.getText().toString().length());
            editText.removeTextChangedListener(this);
            editText.setSelection(editText.getText().toString().length());
        }
    }
}
