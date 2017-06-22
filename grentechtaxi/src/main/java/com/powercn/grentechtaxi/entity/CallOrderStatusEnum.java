package com.powercn.grentechtaxi.entity;




/**
 * 订单状态
 */
public enum CallOrderStatusEnum implements GenericEnum{
    RESVER(0, "保留"),
    NEW(1, "抢单中..."),
    PENDING(2, "抢单中.."),
    BOOKED(3, "已经抢单"),
    NOCHECK(4, "无人抢单"),
    NOTAXI(5, "无车辆"),
    FINISH(6, "订单完成"),
    CANCEL_ADMIN(7, "平台取消订单"),
    CANCEL_PASSENGER(8, "乘客取消订单"),
    CANCEL_DRIVER(9, "司机取消订单"),
    ASSIGN_CAR(10, "车辆指派"),
    RUNNING(11, "载客中")
    ;

    private int code;
    private String name;

    CallOrderStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static CallOrderStatusEnum valueOfEnum(int code) {
        CallOrderStatusEnum[] iss = values();
        for (CallOrderStatusEnum cs : iss) {
            if (cs.getCode() == code) {
                return cs;
            }
        }
        return null;
    }
}