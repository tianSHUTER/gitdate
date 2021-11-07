package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @Company http://www.ithiema.com
 *
 * 测试mybatis的crud操作
 */
public class MybatisTest {

    private InputStream in;
    private IUserDao userDao;
    private SqlSession session ;

    @Before//用于在测试方法执行之前执行
    public void init()throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
         session = factory.openSession();
        //3.使用工厂对象，创建dao对象
        userDao = session.getMapper(IUserDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destroy()throws Exception{
        session.commit();
        //6.释放资源
        in.close();
    }


    /**
     * 测试保存操作
     */
    @Test
    public void testSave() throws Exception {


        long start = System.currentTimeMillis();

        List<User> list = makeList(User.class, 1000 * 1000);
        userDao.insert(list);

        long end = System.currentTimeMillis();

        System.out.println(start-end);
    }

    public List makeList(Class c, long total) throws Exception {
//        获得所用传入类的属性
        Field[] fields = c.getDeclaredFields();
//        生成返回对象的空数组
        List list = new ArrayList();

        for (int i = 0; i < total; i++) {
//                    创建形参对象
            Object obj = c.getConstructor().newInstance();
            for (Field f : fields) {
                //暴力反射，可以操作受保护属性，不建议用破坏封装性
                f.setAccessible(true);

//                    判断属性是否为int并赋值
                if (f.getType() == int.class){
                    f.set(obj,(int)(Math.round(Math.random()*2147483647)));
                }
//                    判断是否字符串类型,生成长度不超过10的随机汉字字符串
                else if (f.getType() == String.class) {
                    StringBuilder zh_cn = new StringBuilder();
                    String str ="";
                    // Unicode中汉字所占区域\u4e00-\u9fa5,将4e00和9fa5转为10进制
                    int start = Integer.parseInt("4e00", 16);
                    int end = Integer.parseInt("9fa5", 16);
                    for(int ic=0;ic<Math.round(Math.random()*10);ic++){
                        // 随机值
                        int code =(new Random()).nextInt(end - start + 1) + start;
                        // 转字符
                        str = new String(new char[] { (char) code });
                        zh_cn.append(str);
                    }
                    f.set(obj,zh_cn.toString());
                }

            }
            list.add(obj);
        }
        return list;
    }
}


