package cn.com.grentech.specialcar.common.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/7/21.
 */

public class DoubleUnit {

    public static double keepDecimalUp(double d,int keepBit)
    {
        int bit=keepBit>=8?8:keepBit;
        String format="0.000000000000";
        format=format.substring(0,keepBit+2);
        DecimalFormat decimalFormat=new DecimalFormat(format);
        String result=decimalFormat.format(d);
        return Double.parseDouble(result);
    }

    public static double keepDecimal(double d,int keepBit)
    {
        int bit=keepBit>=8?8:keepBit;
        BigDecimal big=new BigDecimal(d).setScale(bit, RoundingMode.DOWN);
        return big.doubleValue();
    }
}
