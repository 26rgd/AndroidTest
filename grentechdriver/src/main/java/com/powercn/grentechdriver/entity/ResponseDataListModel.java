package com.powercn.grentechdriver.entity;

import java.util.List;

import lombok.Data;

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
