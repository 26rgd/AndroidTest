package cn.com.grentech.specialcar.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/6/15.
 */

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 4020387224332289695L;
    private String phone;
    private String password;
    private String name;
    private String ruleName;
    private long createTime;
    private int flag;
}
