package com.powercn.grentechdriver.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/5/17.
 */
@Data
public class CallOrder implements Serializable {
    private static final long serialVersionUID = -7859225787596495758L;
    private String phone;
    private String from;
    private Double fromLat;
    private Double fromLng;
    private String to;
    private Double toLat;
    private Double toLng;
    private Integer ordertype;
    private String scheduledtime;

}
