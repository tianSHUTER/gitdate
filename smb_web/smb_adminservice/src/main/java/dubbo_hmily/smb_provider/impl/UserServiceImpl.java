package dubbo_hmily.smb_provider.impl;


import dubbo_hmily.smb_api.User;
import dubbo_hmily.smb_api.service.UserService;
import dubbo_hmily.smb_dao.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DubboService(version = "1.0.0", tag = "red", weight = 100)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    UserMapper userMapper; //DAO  // Repository

    public void comfirmSuc(){
        System.out.println("提交成功");
    }


    public void CancelRoll(){
        System.out.println("调用日志记录  拆分日志语句  重新执行日志sql");
        System.out.println("回滚业务");
    }



    // 开启spring cache
    @Cacheable(key="findAll",value="user")
    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User login(String name,int age) {
        return userMapper.findUserByUsernameAndAge(name, age);
    }

    @HmilyTCC(confirmMethod ="comfirmSuc",cancelMethod="CancelRoll")
    @CachePut(value ="user", key="#user.id")
    public void addUser(User user) {
        userMapper.add(user);
    }

    @HmilyTCC(confirmMethod ="comfirmSuc",cancelMethod="CancelRoll")
    @CacheEvict(value ="user",key = "#id")
    public void deleteUser(int id) {
        userMapper.delete(id);
    }

    @Cacheable(value ="user",key="#id",sync = true)
    public User findUserById(int id) {
        System.out.println(" ==> find " + id);
        return userMapper.findById(id);
    }

    @CachePut(value ="user",key="#user.id")
    public User updateUser(User user) {

        userMapper.update(user);
        return userMapper.findById(user.getId());
    }

    public void delSelectedUser(int[] ids) {
        for (Integer i =0;i<ids.length;i++){
            deleteUser(ids[i]);
        }
    }

    @CachePut(value ="user",key ="#userList.user.id")
    public void insertGenerous(List<User> userList) {
        userMapper.insertGenerous(userList);
    }

    //实现锁数据库存删减
    public String reduce(int id) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String lockkey="product lock";
        Integer ProductCacheid =Integer.valueOf(valueOperations.get("ProductCache::" + id)) ;

        try {
            //加锁，setnx
            Boolean ifAbsent = valueOperations.setIfAbsent(lockkey, "1");
            redisTemplate.expire(lockkey,10, TimeUnit.SECONDS);

            //判断加锁是否成功
            if (null==ifAbsent||ifAbsent){
                System.out.println("服务器繁忙  请多试几次");
                return "error";
            }

            //获取redis中的键
            if (ProductCacheid > 0){

                //执行业务逻辑
                int newProduct = ProductCacheid - 1;
                valueOperations.set("ProductCache::" + id, newProduct + "");
                return newProduct + "";
            }
        }

        finally
        { redisTemplate.delete(lockkey); }
        if  (ProductCacheid==null){

            userMapper.delete(id);
            return "删除成功";

        }
        else return "错误";
    }
}
