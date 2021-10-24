package kimming.domain;

import kimmking.SpringbootJpaApplication;
import kimmking.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

public class test {
    @RunWith(SpringRunner.class)
    @SpringBootTest(classes = SpringbootJpaApplication.class)
    public class JpaTest {

        @Autowired
        private Student student;

        @Test
        public void test(){
            List<Student> all = student.findAll();
            System.out.println(all);

        }

    }

}
