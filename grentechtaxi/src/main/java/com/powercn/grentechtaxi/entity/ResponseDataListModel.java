package com.powercn.grentechtaxi.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Mike on 2016/11/18.
 * 定义controller中方法的返回对象
 */
@Data
public class ResponseDataListModel<T> {
    private boolean success = true;
    private List<T> list;
    private int count=0;
}
