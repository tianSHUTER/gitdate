package dubbo_hmily.smb_dao.mapper;



import dubbo_hmily.smb_api.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserMapper {


    List<User> findAll();

    User findUserByUsernameAndAge(String name, int age);

    void add(User user);

    void delete(int id);

    User findById(int id);

    void update(User user);

    void insertGenerous(List<User> userList);
}
