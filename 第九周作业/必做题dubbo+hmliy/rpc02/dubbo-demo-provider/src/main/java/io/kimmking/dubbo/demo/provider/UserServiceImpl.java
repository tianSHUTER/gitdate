package io.kimmking.dubbo.demo.provider;

import io.kimmking.dubbo.demo.api.User;
import io.kimmking.dubbo.demo.api.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {


    @HmilyTCC(confirmMethod ="comfirmSuc",cancelMethod="CancelRoll")
    @Override
    public User exchange2doller(int rmb) {
        User user = new User();
        Double money_rmb = user.getMoney_rmb();
       user.setMoney_doller(user.getMoney_doller()+rmb/7);
       user.setMoney_rmb(money_rmb-rmb);
        return user;
    }
 public void comfirmSuc(){
     System.out.println("提交成功");
 }
    @HmilyTCC(confirmMethod ="comfirmSuc",cancelMethod="CancelRoll")
    @Override
    public User exchange2rmb(int doller) {

        User user = new User();
        Double money_doller = user.getMoney_doller();
        user.setMoney_rmb(user.getMoney_rmb()+doller*7);
        user.setMoney_doller(money_doller-doller);
        return user;
    }
    public void CancelRoll(){
        System.out.println("回滚业务");
    }

}
