package com.itheima.dao.impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class UserDaoImpl implements IUserDao {

    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    @Override
    public void insert(List<User>  list) {
        SqlSession session = factory.openSession();
        session.insert("com.itheima.dao.IUserDao.insert",list);
        //3.提交事务
        session.commit();
        //4.释放资源
        session.close();
    }




}
