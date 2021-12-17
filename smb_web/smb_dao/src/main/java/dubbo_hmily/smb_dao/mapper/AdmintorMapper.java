package dubbo_hmily.smb_dao.mapper;

import dubbo_hmily.smb_api.Admintor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface AdmintorMapper {
    /**
     * 查询所有用户信息
     */
    List<Admintor> findAll();

    /**
     * 登录方法
     */
    Admintor login(String name, int id);

    /**
     * 保存admintor
     */
    void addAdmintor(Admintor admintor);

    /**
     * 根据id删除admintor
     */
    void deleteAdmintor(int id);

    /**
     * 根据id查询
     */
    Admintor findAdmintorById(int id);

    /**
     * 修改用户信息
     */
    Admintor updateAdmintor(Admintor admintor);

    /**
     * 根据管理员查找对应的管理表
     */
    String findManageList(String manageList);
}
