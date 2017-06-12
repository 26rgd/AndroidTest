package com.powercn.grentechdriver.respone;

import com.powercn.grentechdriver.common.http.ResponeInfo;

/**
 * Created by Administrator on 2017/5/11.
 */

public abstract class AbstractRespone {

    public abstract AbstractRespone bulider();
    public abstract  void CreateRespone(ResponeInfo responeInfo);
}
