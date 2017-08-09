package com.powercn.grentechdriver.view;

import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.powercn.grentechdriver.activity.MainActivity;
import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractDialogView;
import com.powercn.grentechdriver.common.unit.DateUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

/**
 * Created by Administrator on 2017/5/12.
 */
@Getter
public class DeteTimeView extends AbstractDialogView implements NumberPicker.Formatter, NumberPicker.OnValueChangeListener {
    private Button bt_selecttitme_sub;
    Button bt_selecttitme_cancle;
    NumberPicker numberPicker0;
    NumberPicker numberPicker1;
    NumberPicker numberPicker2;
    String[] day = {"今天", "明天", "后天"};

    int hour;
    int minute;

    public DeteTimeView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        bt_selecttitme_sub = (Button) findViewById(R.id.bt_selecttitme_sub);
        bt_selecttitme_cancle = (Button) findViewById(R.id.bt_selecttitme_cancle);
        numberPicker0 = (NumberPicker) findViewById(R.id.numberPicker0);
        numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker1);
        numberPicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
    }

    @Override
    protected void bindListener() {
        bt_selecttitme_sub.setOnClickListener(this);
        bt_selecttitme_cancle.setOnClickListener(this);
        ;
        numberPicker1.setFormatter(this);
        numberPicker2.setFormatter(this);

        numberPicker0.setOnValueChangedListener(this);
    }

    @Override
    protected void initData() {

    }




    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    @Override
    public void setVisibility(int visibility) {
        String time = "现在";
        if (time.contains("现在")) {
            bulidToday();
        } else {
        }
        super.setVisibility(visibility);
    }

    private void bulidToday() {
        numberPicker0.setDisplayedValues(day);
        numberPicker0.setMinValue(0);
        numberPicker0.setMaxValue(day.length - 1);
        numberPicker0.setValue(0);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minu = calendar.get(Calendar.MINUTE);

        if (minu + 10 <= 59) {
            numberPicker1.setMaxValue(23);
            numberPicker1.setMinValue(hour);
            numberPicker1.setValue(hour);
            numberPicker2.setMaxValue(59);
            numberPicker2.setMinValue(minu + 10);
            numberPicker2.setMinValue(minu + 10);
            numberPicker1.setWrapSelectorWheel(false);
            numberPicker2.setWrapSelectorWheel(false);
        } else {
            if(hour + 1<23)
            {
                numberPicker1.setMaxValue(23);
                numberPicker1.setMinValue(hour + 1);
                numberPicker1.setValue(hour + 1);
                numberPicker2.setMaxValue(59);
                numberPicker2.setMinValue(minu + 10 - 59);
                numberPicker2.setMinValue(minu + 10 - 59);
                numberPicker1.setWrapSelectorWheel(false);
                numberPicker2.setWrapSelectorWheel(false);
            }
            else
            {
                numberPicker0.setValue(1);
            }
        }
    }

    private void bulidNotToday() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minu = calendar.get(Calendar.MINUTE);

        numberPicker1.setMaxValue(23);
        numberPicker1.setMinValue(0);
        numberPicker1.setValue(12);


        numberPicker2.setMaxValue(59);
        numberPicker2.setMinValue(0);
        numberPicker2.setValue(30);

        numberPicker1.setWrapSelectorWheel(true);
        numberPicker2.setWrapSelectorWheel(true);

    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.numberPicker0:
                if (newVal == 0) {
                    bulidToday();
                } else {
                    bulidNotToday();
                }
                break;
            case R.id.numberPicker1:
                break;
            case R.id.numberPicker2:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_selecttitme_sub:
                long time=getTime(numberPicker0.getValue());
                String stime=DateUnit.formatDate(time,"yyyy-MM-dd HH:mm:ss");
                //activity.callActionView.getTv_call_time().setText(stime);
               // activity.callCarView.getTvShowTime().setText(stime);
                setVisibility(View.GONE);
                break;
            case R.id.bt_selecttitme_cancle:
                setVisibility(View.GONE);
                break;
        }
    }

    private long getTime(int selectday)
    {
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour=String.valueOf(numberPicker1.getValue());
        String minu=String.valueOf(numberPicker2.getValue());
        String time= StringUnit.supplement(true,year,"0",4)+"-"+StringUnit.supplement(true,month,"0",2) +"-"+StringUnit.supplement(true,day,"0",2)
                +" "+StringUnit.supplement(true,hour,"0",2)+":"+StringUnit.supplement(true,minu,"0",2)+":"+"00";
        Date selecttime=DateUnit.StringToTimeDate(time,"yyyy-MM-dd HH:mm:ss");
        long i=selecttime.getTime()+addDay(selectday);

        return i;
    }
    private long addDay(int day)
    {  long one=24*60*60*1000;
        return one*day;
    }

}
