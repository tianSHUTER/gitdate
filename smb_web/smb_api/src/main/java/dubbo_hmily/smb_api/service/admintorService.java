package dubbo_hmily.smb_api.service;

import dubbo_hmily.smb_api.Admintor;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

@CacheConfig(cacheNames = "Admintor")
public interface admintorService {
    /**
     * 查询所有用户信息
     */@Hmily
    public List<Admintor> findAll();

    /**
     * 登录方法
     */@Hmily
    Admintor login(String adminname, int id);

    /**
     * 保存admintor
     */@Hmily
    void addAdmintor(Admintor admintor);

    /**
     * 根据id删除admintor
     */@Hmily
    void deleteAdmintor(int id);

    /**
     * 根据id查询
     */@Hmily
    Admintor findAdmintorById(int id);

    /**
     * 修改用户信息
     */@Hmily
    Admintor updateAdmintor(Admintor admintor);

    /**
     * 根据管理员查找对应的管理表
     */@Hmily
    String findManageList(String manageList);
}
