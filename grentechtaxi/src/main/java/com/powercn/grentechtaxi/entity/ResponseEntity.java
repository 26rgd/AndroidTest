package com.powercn.grentechtaxi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/5/23.
 */
@Getter
@Setter
public class ResponseEntity {
    //{"success":true,"nickName":null,"headImage":null,"message":"取数据成功"}
    private Boolean success;
    private  String message;
}
