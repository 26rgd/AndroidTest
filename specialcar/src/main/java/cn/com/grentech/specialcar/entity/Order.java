package cn.com.grentech.specialcar.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/6/14.
 */

@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -3152911407043855198L;
    private int id;
    private String createrName;
    private String createrPhone;
    private String riderName;
    private String riderPhone;
    private String department;
    private int riderCount;
    private String driverName;
    private String driverPhone;
    private String carNo;
    private String from;
    private double fromLat;
    private double fromLng;
    private String to;
    private double toLat;
    private double toLng;
    private long startTime;
    private long endTime;
    private long createTime;
    private double mileage;
    private String bak;
    private int flag;


}