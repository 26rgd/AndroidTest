package com.powercn.grentechtaxi.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class OrderInfo implements Serializable {
    private Long id;

    private String caller;

    private String passenger;

    private String gender;

    private String mobile;

    private String cartype;

    private String inAddress;

    private String inAddressGps;

    private String downAddress;

    private String downAddressGps;


    private Date scheduledTime;

    private String comment;

    private Integer ordersource;

    private String ordersourceName;

    //订单类型,0 实时，1 预约，2 指派
   // @JsonSerialize(using=CallOrderTypeEnumSerializer.class)
   // @JsonDeserialize(using=CallOrderTypeEnumDeserialize.class)
    private CallOrderTypeEnum ordertype;
  // private DefaultClass ordertype;

    private String ordertypeName;

    private Long jobId;

    //车辆选择方式,抢单/指派车辆,默认抢单
    private byte orderDispatchType=1;
    private String  orderDispatchTypeName;

    private Date dispatch_time;

    private Date order_check_time;

    private Date order_reply_time;

    private String isu;

    private Integer carid;

    private String carNo;

    private Integer driverid;

    private String driverName;

    private String driverPhone;

   private CallOrderStatusEnum status;

  //  private DefaultClass status;

    private String statusUI;

    private String statusUIClass;

    private String assign_reason;

    private String cancel_reason;

    private Date cancel_time;

    private int creator;

    private String creatorName;

    private Date createTime;

    private int updater;

    private Date updateTime;

    private byte deleteStatus;

    private Integer evaluateAnswer;
    private String  evaluateAnswerDescription;

    private Long takeOnTime;

    private Long takeOffTime;

    private Float taxiJFD;

    private Float emptyJFD;

    private Integer waitTime;

    private Float amount;

    public OrderInfo(long id, Date createTime,String downAddress,String inAddress,CallOrderStatusEnum status)
    {
        this.id=id;
        this.createTime=createTime;
        this.downAddress=downAddress;
                this.inAddress=inAddress;
        this.status=status;
        this.evaluateAnswer=3;
    }

}