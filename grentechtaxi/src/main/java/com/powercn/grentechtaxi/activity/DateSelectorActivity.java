package com.powercn.grentechtaxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.unit.DateUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.view.NumberPickerExt;

import java.util.Calendar;
import java.util.Date;

import static android.R.attr.value;
import static android.R.id.list;
import static android.os.Build.VERSION_CODES.N;
import static com.powercn.grentechtaxi.R.id.bt_selecttitme_cancle;
import static com.powercn.grentechtaxi.R.id.bt_selecttitme_sub;
import static com.powercn.grentechtaxi.R.id.form_login_form2;
import static com.powercn.grentechtaxi.R.id.numberPicker1;
import static com.powercn.grentechtaxi.common.unit.DateUnit.StringToTimeDate;

/**
 * Created by Administrator on 2017/6/13.
 */

public class DateSelectorActivity extends AbstractBasicActivity implements NumberPicker.OnValueChangeListener {
    private Button btOk;
    Button btCancel;
    NumberPickerExt numberPicker0;
    NumberPickerExt numberPicker1;
    NumberPickerExt numberPicker2;
    TextView tvTimeFormat;
    String[] day = {"今天", "明天", "后天"};
    String[] initHours = {"0点", "1点", "2点", "3点", "4点", "5点", "6点", "7点", "8点", "9点", "10点", "11点", "12点", "13点",
            "14点", "15点", "16点", "17点", "18点", "19点", "20点", "21点", "22点", "23点"};
    String[] initMinutes = {"00分", "10分", "20分", "30分", "40分", "50分"};
    String[] hours;
    String[] minutes;
    String time0, time1, time2;
    String now = "现在";
    int delay = 15;
    final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_datetime);

    }

    @Override
    protected void initView() {
        btOk = (Button) findViewById(bt_selecttitme_sub);
        btCancel = (Button) findViewById(bt_selecttitme_cancle);
        numberPicker0 = (NumberPickerExt) findViewById(R.id.numberPicker0);
        numberPicker1 = (NumberPickerExt) findViewById(R.id.numberPicker1);
        numberPicker2 = (NumberPickerExt) findViewById(R.id.numberPicker2);
        tvTimeFormat = (TextView) findViewById(R.id.tvTimeFormat);

    }

    @Override
    protected void bindListener() {
        btOk.setOnClickListener(this);
        btCancel.setOnClickListener(this);


        numberPicker0.setOnValueChangedListener(this);
        numberPicker1.setOnValueChangedListener(this);
        numberPicker2.setOnValueChangedListener(this);
    }

    @Override
    protected void initData() {
        tvTimeFormat.setVisibility(View.GONE);
        bulidToday();
        initDate();
    }

    private void initDate() {
        Bundle bundle = ViewUnit.getIntent(this);
        String time = bundle.getString("selecttime");
        if (!time.equals(now)) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            Date date = DateUnit.StringToTimeDate(time, dateFormat);
            calendar = ViewUnit.getCalendar(date);
            int dayselect = calendar.get(Calendar.DAY_OF_MONTH);
            int hourselect = calendar.get(Calendar.HOUR_OF_DAY);
            int minuteselect = calendar.get(Calendar.MINUTE);
            if (dayselect < day) return;
            if (dayselect == day) {
                onValueChange(numberPicker0, 0, 0);
                initDateNoNow(0,hourselect,minuteselect);
            } else if (dayselect == day + 1) {
                onValueChange(numberPicker0, 0, 1);
                initDateNoNow(1,hourselect,minuteselect);
            } else if (dayselect == day + 2) {
                onValueChange(numberPicker0, 0, 2);
                initDateNoNow(2,hourselect,minuteselect);
            }
        }
    }

    private void initDateNoNow(int dayindex,int hour,int mintue)
    {
        numberPicker0.setValue(dayindex);
        int h=getIndex(hours,StringUnit.supplement(true, String.valueOf(hour), "0", 2)+"点" );
        numberPicker1.setValue(h);
        int m=getIndex(minutes,StringUnit.supplement(true, String.valueOf(mintue), "0", 2)+"分" );
        numberPicker2.setValue(m);
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (picker.getId()) {
            case R.id.numberPicker0:
                if (newVal == 0) {
                    bulidToday();
                } else if (newVal == 1) {
                    bulidTomorrow();
                } else {
                    bulidAfterTomorrow();
                }
                break;
            case R.id.numberPicker1:
                String display = hours[numberPicker1.getValue()];
                if (display.equals(now)) {
                    minutes = bulidEmpty();
                    numberPicker2.setValues(minutes);

                } else {
                    int i = numberPicker1.getValue();
                    display = hours[0];
                    if (display.equals(now) && i == 1) {
                        minutes = bulidBorderMintue(minute);
                        numberPicker2.setValues(minutes);

                    } else {
                        minutes = initMinutes;
                        numberPicker2.setValues(minutes);

                    }
                }
                break;
            case R.id.numberPicker2:
                break;
        }

    }

    private void bulidToday() {

        numberPicker0.setValues(day);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        hours = bulidBorderHour(hour, minute);
        minutes = bulidBorderMintue(minute);

        numberPicker1.setValues(hours);

        if (numberPicker1.getCurDisplay().equals(now)) {
            minutes = bulidEmpty();
            numberPicker2.setValues(minutes);
        } else {
            minutes = bulidBorderMintue(minute);
            int i = numberPicker1.getValue();
            String display = hours[0];
            if (display.equals(now) && i == 1) {
                minutes = bulidBorderMintue(minute);
                numberPicker2.setValues(minutes);
            } else {
                minutes = initMinutes;
                numberPicker2.setValues(minutes);
            }
        }
        setPicker();
    }

    private void setPicker() {
        numberPicker1.setWrapSelectorWheel(false);
        numberPicker2.setWrapSelectorWheel(false);
    }

    private void bulidTomorrow() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 23 && minute > 35) {
            hours = initHours;
            minutes = bulidBorderMintue(minute);
            numberPicker1.setValues(hours);
            numberPicker2.setValues(minutes);
            return;
        }
        hours = initHours;
        minutes = initMinutes;
        numberPicker1.setValues(hours);
        numberPicker2.setValues(minutes);
        setPicker();
    }

    private void bulidAfterTomorrow() {
        hours = initHours;
        minutes = initMinutes;
        numberPicker1.setValues(hours);
        numberPicker2.setValues(minutes);
        setPicker();
    }

    private String[] bulidBorderHour(int hour, int minute) {
//    {"0点", "1点", "2点", "3点", "4点", "5点", "6点", "7点", "8点", "9点", "10点", "11点", "12点", "13点",
//                "14点", "15点", "16点", "17点", "18点", "19点", "20点", "21点", "22点", "23点"};
        if (hour == 23 && minute > 35) {
            String[] hou = new String[1];
            hou[0] = now;
            return hou;
        }

        if (minute > 35) {
            String[] hou = new String[25 - hour - 1];
            hou[0] = now;
            for (int i = 1; i < hou.length; i++) {
                hou[i] = initHours[hour + i];
            }
            return hou;
        } else {
            String[] hou = new String[25 - hour];
            hou[0] = now;
            for (int i = 1; i < hou.length; i++) {
                hou[i] = initHours[hour + i - 1];
            }
            return hou;
        }
    }

    private String[] bulidEmpty() {
        String[] vs = new String[1];
        vs[0] = "  ";
        return vs;
    }

    private String[] bulidBorderMintue(int minute) {
        if (minute >= 0 && minute <= 5 || minute > 55) {
            String[] min = {"20分", "30分", "40分", "50分"};
            return min;
        } else if (minute > 5 && minute <= 15) {
            String[] min = {"30分", "40分", "50分"};
            return min;
        } else if (minute > 15 && minute <= 25) {
            String[] min = {"40分", "50分"};
            return min;
        } else if (minute > 25 && minute <= 35) {
            String[] min = {"50分"};
            return min;
        } else if (minute > 35 && minute <= 45) {
            String[] min = {"00分", "10分", "20分", "30分", "40分", "50分"};
            return min;
        } else {
            String[] min = {"10分", "20分", "30分", "40分", "50分"};
            return min;
        }
    }

    @Override
    public void onClick(View v) {
        String stime = now;
        switch (v.getId()) {
            case R.id.bt_selecttitme_sub:
                if (numberPicker1.getCurDisplay().replace("小时", "").equals(now)) {

                    stime = "现在";
                } else {
                    long time = getTime(numberPicker0.getValue());
                    stime = DateUnit.formatDate(time, "yyyy-MM-dd HH:mm:ss");
                }
                Intent intent = this.getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("selecttime", stime);
                intent.putExtras(bundle);
                this.setResult(MainActivity.selectTime, intent);
                finish();
                break;
            case R.id.bt_selecttitme_cancle:
                finish();
                break;
        }
    }

    private long getTime(int selectday) {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(numberPicker1.getCurDisplay().replace("点", ""));
        String minu = String.valueOf(numberPicker2.getCurDisplay().replace("分", ""));
        String time = StringUnit.supplement(true, year, "0", 4) + "-" + StringUnit.supplement(true, month, "0", 2) + "-" + StringUnit.supplement(true, day, "0", 2)
                + " " + StringUnit.supplement(true, hour, "0", 2) + ":" + StringUnit.supplement(true, minu, "0", 2) + ":" + "00";
        Date selecttime = StringToTimeDate(time, dateFormat);
        long i = selecttime.getTime() + addDay(selectday);

        return i;
    }

    private long addDay(int day) {
        long one = 24 * 60 * 60 * 1000;
        return one * day;
    }

    private int getIndex(String[] list,String v)
    {
        int index=0;
        for(int i=0;i<list.length;i++)
        {
            String line=list[i];
            if(!StringUnit.isEmpty(line))
            {
                if(line.equals(v))
                {
                    index=i;
                    return index;
                }
            }
        }
        return index;
    }
}
