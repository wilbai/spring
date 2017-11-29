package com.wil.hibernate;

import com.wil.pojo.Student;
import com.wil.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by wil on 2017/11/27.
 */
public class HqlTest {

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
    public void findAll() {
        String hql = "from Student";
        Query query = session.createQuery(hql);
        List<Student> students = query.list();
        showList(students);

    }

    @Test
    public void findById() {
        String hql = "from Student where id = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0, 14);
        Student student = (Student) query.uniqueResult();
        System.out.println(student.getName());

    }

    @Test
    public void findByName() {
        String hql = "from Student where name = :name";
        Query query = session.createQuery(hql);
        //query.setParameter("name", "白白");
        query.setString("name", "白白");
        //Student student = (Student) query.uniqueResult();
        List<Student> studentList = query.list();
        showList(studentList);
    }

    @Test
    public void findSingleNameColumn() {
        String hql = "select name from Student";
        Query query = session.createQuery(hql);
        List<String> nameList = query.list();
        for(String name : nameList) {
            System.out.println(name);
        }
    }

    @Test
    public void findIdAndNameColumn() {
        String hql = "select id,name,classId from Student";
        Query query = session.createQuery(hql);
        List<Object[]> dataList = query.list();
        for(Object[] array : dataList) {
            System.out.println(array[0] + "->" + array[1] + "->" + array[2]);
        }
    }

    @Test
    public void count() {
        String hql = "select count(*),max(classId) from Student";
        Query query = session.createQuery(hql);
        Object[] data = (Object[]) query.uniqueResult();
        System.out.println(data[0] +"->"+ data[1]);
    }

    @Test
    public void page() {
        String hql = "from Student order by id desc";
        Query query = session.createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(5);
        List<Student> studentList = query.list();
        showList(studentList);
    }

    private void showList(List<Student> studentList) {
        for(Student student : studentList) {
            System.out.println(student.getId() + "->" + student.getName());
        }
    }

}
