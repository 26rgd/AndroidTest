package com.powercn.grentechtaxi.entity;

import lombok.Data;

/**
 * Created by Mike on 2016/11/18.
 * 定义controller中方法的返回对象
 */
@Data
public class ResponseDataModel<T> {
    private boolean success = true;
    private String message;
    private T entity;
}
