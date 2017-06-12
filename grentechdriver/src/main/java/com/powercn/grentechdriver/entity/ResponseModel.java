package com.powercn.grentechdriver.entity;

import lombok.Data;

/**
 * Created by Mike on 2016/11/18.
 * 定义controller中方法的返回对象
 */
@Data
public class ResponseModel {
    private boolean success = true;
    private String message;
}
