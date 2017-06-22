package cn.com.grentech.specialcar.entity;

/**
 * Created by Administrator on 2017/6/15.
 */

public enum DriverInfo {
    fname("用户姓名", ""),
    phone("手机号码", ""),
    address("联系地址", ""),
    licenseNo("驾照编号", ""),
    carNo("车牌号码", ""),
    carType("车辆类型", ""),
    carRow("车辆坐位", "");
    private String name;
    private String value;

    DriverInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
