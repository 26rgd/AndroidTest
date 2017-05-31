package com.powercn.grentechtaxi.entity;

/**
 * 订单类型
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CallOrderTypeEnum implements GenericEnum{
    //需跟协议一致
    REAL_TIME(0, "实时"),
    SCHEDULER(1, "预约"),
    ASSIGN(2, "指派")
    ;

    private int code;
    private String name;

    CallOrderTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    //@JsonValue
    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static CallOrderTypeEnum valueOfEnum(int code) {
        CallOrderTypeEnum[] iss = values();
        for (CallOrderTypeEnum cs : iss) {
            if (cs.getCode() == code) {
                return cs;
            }
        }
        return null;
    }

}