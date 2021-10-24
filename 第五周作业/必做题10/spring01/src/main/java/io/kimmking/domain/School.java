package io.kimmking.domain;


import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


public class School implements ISchool {

    //引用klass
 @Autowired
  private Klass class1;

 //引用applicationContext中的student100 值定义
 @Resource(name = "s101")
 Student s101;
    @Override
    public void ding(){
    
        System.out.println("Class1 have " +
                this.class1.getStudents().size() +
                " students and one is " + this.s101);
        
    }
    
}
