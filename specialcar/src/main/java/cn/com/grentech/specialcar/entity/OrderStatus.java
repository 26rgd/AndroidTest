package cn.com.grentech.specialcar.entity;

/**
 * Created by Administrator on 2017/6/15.
 */

public enum OrderStatus {
    Unknow("未知",0),
    NewOrder("新建",1),
    RunOrder("执行中",2),
    PauseOrder("暂停中",3),
    FinishOrder("结束",4),
    CancelOrder("取消",5)
    ;
    private String name;
    private Integer value;
    OrderStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public Integer getValue() {
        return value;
    }

}
