package io.kimmking.domain.dao;


import io.kimmking.domain.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentDao
{
    @Select("Select * from user")
    public List<Student> findAll();

}
