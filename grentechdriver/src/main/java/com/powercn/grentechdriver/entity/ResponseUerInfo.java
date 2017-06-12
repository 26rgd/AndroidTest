package com.powercn.grentechdriver.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/5/23.
 */
@Getter
@Setter
public class ResponseUerInfo extends ResponseEntity {

    // {"success":true,"nickName":null,"sex":1,"headImage":null,"sign":"1234dfdf","industry":"1234qw","message":"取数据成功","age":null}
    private String nickName;
    private String headImage;
    private int sex;
    private int age;
    private String phone;
    private String sign;
    private String industry;

    public  enum SEX{
        WOMAN("女",0),MAN("男",1);
        private String name;
        private  int value;
        SEX(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}
