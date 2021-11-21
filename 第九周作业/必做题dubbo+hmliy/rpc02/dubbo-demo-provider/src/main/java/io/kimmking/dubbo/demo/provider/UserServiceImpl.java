package io.kimmking.dubbo.demo.provider;

import io.kimmking.dubbo.demo.api.User;
import io.kimmking.dubbo.demo.api.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {


    @Override
    public User exchange2doller(int rmb) {
        User user = new User();
        Double money_rmb = user.getMoney_rmb();
       user.setMoney_doller(user.getMoney_doller()+rmb/7);
       user.setMoney_rmb(money_rmb-rmb);
        return user;
    }

    @Override
    public User exchange2rmb(int doller) {

        User user = new User();
        Double money_doller = user.getMoney_doller();
        user.setMoney_rmb(user.getMoney_rmb()+doller*7);
        user.setMoney_doller(money_doller-doller);
        return user;
    }
}
