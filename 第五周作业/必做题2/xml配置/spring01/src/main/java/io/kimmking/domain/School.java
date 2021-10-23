package io.kimmking.domain;


import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


public class School implements ISchool {

    //引用klass
 @Autowired
  private Klass class1;

 //引用applicationContext中的student100 值定义
 @Resource(name = "student100")
 Student student100;
    @Override
    public void ding(){
    
        System.out.println("Class1 have " +
                this.class1.getStudents().size() +
                " students and one is " + this.student100);
        
    }
    
}
