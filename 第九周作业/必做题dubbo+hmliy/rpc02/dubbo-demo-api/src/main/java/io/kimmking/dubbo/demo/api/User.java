package io.kimmking.dubbo.demo.api;

import lombok.Data;

@Data
public class User implements java.io.Serializable {

    public User(){}

    private int id;
    private Double money_doller;
    private Double money_rmb;

}
