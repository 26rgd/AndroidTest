package cn.com.grentech.specialcar.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/6/15.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAll   extends User implements Serializable {
    private static final long serialVersionUID = -5261611617017333792L;
    private String email;
    private String department;
    private String licenseNo;
    private String address;
    private String carNo;
    private String carType;
    private int siteCount;
    private String location;
    private String controllers;
    private String oldPhone;
}
