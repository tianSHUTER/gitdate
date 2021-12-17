package dubbo_hmily.smb_userservice.impl;


import dubbo_hmily.smb_api.Admintor;

import dubbo_hmily.smb_api.service.UserService;
import dubbo_hmily.smb_api.service.admintorService;
import dubbo_hmily.smb_dao.mapper.AdmintorMapper;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@DubboService(version = "1.0.0", tag = "red", weight = 100)
@Service
public class AdmintorServiceImpl implements admintorService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    AdmintorMapper admintorMapper; //DAO  // Repository

    // 开启spring cache
    @Cacheable(key="findAll",value="admintor")
    public List<Admintor> findAll() {
        return admintorMapper.findAll();
    }

    public Admintor login(String name, int age) {
        return admintorMapper.login(name, age);
    }

    @CachePut(value ="admintor", key="#admintor.id")
    public void addAdmintor(Admintor admintor) {
        admintorMapper.addAdmintor(admintor);
    }

    @CacheEvict(value ="admintor",key = "#id")
    public void deleteAdmintor(int id) {
        admintorMapper.deleteAdmintor(id);
    }

    @Cacheable(value ="admintor",key="#id",sync = true)
    public Admintor findAdmintorById(int id) {
        System.out.println(" ==> find " + id);
        return admintorMapper.findAdmintorById(id);
    }

    @CachePut(value ="admintor",key="#admintor.id")
    public Admintor updateAdmintor(Admintor admintor) {

        admintorMapper.updateAdmintor(admintor);
        return admintorMapper.findAdmintorById(admintor.getId());
    }

    @Override
    public String findManageList(String  id) {
        return admintorMapper.findManageList(id);
    }


}
