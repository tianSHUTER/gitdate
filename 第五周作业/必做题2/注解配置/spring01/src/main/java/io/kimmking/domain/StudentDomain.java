package io.kimmking.domain;

import io.kimmking.domain.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentDomain {
    @Autowired
    private StudentDao studentDao;

    public List<Student> findAll(){
        List<Student> all = studentDao.findAll();
        return all;
    }
}
