package dubbo_hmily.smb_api.service;

import dubbo_hmily.smb_api.User;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

@CacheConfig(cacheNames = "user")
public interface UserService {

    /**
     * 查询所有用户信息
     */
    @Hmily
    public List<User> findAll();

    /**
     * 登录方法
     */
    @Hmily
    User login(String name, int age);

    /**
     * 保存User
     */@Hmily
    void addUser(User user);

    /**
     * 根据id删除User
     */@Hmily
    void deleteUser(int id);

    /**
     * 根据id查询
     */@Hmily
    User findUserById(int id);

    /**
     * 修改用户信息
     */@Hmily
    User updateUser(User user);

    /**
     * 批量删除用户
     */@Hmily
    void delSelectedUser(int[] ids);
    @Hmily
    void insertGenerous(List<User> userList);
    @Hmily
    String reduce(int id);

}
