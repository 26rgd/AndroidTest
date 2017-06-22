package cn.com.grentech.specialcar.entity;

/**
 * Created by Administrator on 2017/6/15.
 */

public enum OrderDetailInfo {
    createrName("下单用户"),
    createrPhone("下单电话"),
    riderName("乘客名称"),
    riderPhone("乘客电话"),
    riderCount("乘客人数"),
    fromAddr("上车地点"),
    toAddr("下车地点"),
    startTime("乘车时间"),
    mileage("行驶里程"),
    flag("订单状态"),;
    private String name;
    private String value;

    OrderDetailInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
