package com.powercn.grentechdriver.respone;

import com.powercn.grentechdriver.common.http.ResponeInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/5/11.
 */

@Getter
@Setter
public class LoginRespone extends AbstractRespone {
    private Boolean loginSuccess;


    @Override
    public AbstractRespone bulider() {
        return new LoginRespone();
    }

    @Override
    public void CreateRespone(ResponeInfo responeInfo) {

    }
}
