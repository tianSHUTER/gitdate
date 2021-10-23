package io.kimmking;

import io.kimmking.domain.Student;
import io.kimmking.domain.StudentDomain;
import io.kimmking.domain.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.List;

public class Jdbcofhomework_10 {
    @Autowired
    private StudentDomain studentDomain;

    public class Hikaliconn{
        public List<Student> findAllByHikali(){
            List<Student> all=studentDomain.findAll();
return all;
        }

    }

    public static void main(String[] args) throws SQLException {

//使用Hikali来替代传统jdbc放你发
        
    /*
    Hikaliconn.findAllByHikali();
     */


        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection connection = DriverManager.getConnection("localhost:169.0.0.3306", "root", "root");

        //使用prepareStament批量处理结果
//        PreparedStatement pepstam = connection.prepareStatement("SELECT * from user");
//        pepstam.addBatch("UPDATE user name ='bob'");
//        pepstam.addBatch("UPDATE user name ='tom'");
//        pepstam.addBatch("UPDATE user name ='que'");
//        pepstam.addBatch("UPDATE user name ='lel'");

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * from user");
        statement.executeUpdate("UPDATE user name ='bob'");
        while (rs.next()){
            System.out.print(rs.getInt("id")+",");
            System.out.print(rs.getString("name")+",");
            System.out.print(rs.getString("sex")+",");
            System.out.print(rs.getInt("age"));
            System.out.print("\n");
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
