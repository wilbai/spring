package com.wil.hibernate;

import com.wil.pojo.Student;
import com.wil.pojo.Teacher;
import com.wil.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wil on 2017/11/28.
 */
public class ManyToManyTest {

    private Session session;

    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }

    @After
    public void after() {
        session.getTransaction().commit();
    }

    @Test
    public void findStudent() {
        Student student = (Student) session.get(Student.class, 1);
        System.out.println(student.getName());

        Set<Teacher> teacherSet = student.getTeacherSet();
        for(Teacher teacher : teacherSet) {
            System.out.println(teacher.getName());
        }
    }

    @Test
    public void save() {

        Teacher teacher = new Teacher();
        teacher.setName("叶三");

        Teacher teacher2 = new Teacher();
        teacher.setName("叶四");

        Set<Teacher> teacherSet = new HashSet<>();
        teacherSet.add(teacher);
        teacherSet.add(teacher2);

        Student student = new Student();
        student.setName("诺承");
        student.setClassId(3);
        student.setTeacherSet(teacherSet);

        //多对多时 一方维护关系 先存不维护关系的一方 再存维护关系的一方
        session.save(teacher);
        session.saveOrUpdate(teacher2);
        session.saveOrUpdate(student);


    }


}
