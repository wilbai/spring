package com.wil.hibernate;

import com.wil.pojo.Student;
import com.wil.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.util.List;

/**
 * Created by wil on 2017/11/27.
 */
public class HelloWorld {


    @Test
    public void save() {
        //1.读取classpath中名称为hibernate.cfg.xml的配置文件
        Configuration configuration = new Configuration().configure();
        //2.创建SessionFactory
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //3.创建session
        Session session = sessionFactory.getCurrentSession();
        //4.创建事务
        Transaction transaction = session.beginTransaction();
        //5.执行操作
        Student student = new Student();
        student.setName("麦嘉");
        student.setClassId(6);

        session.save(student);

        //6.提交事务
        transaction.commit();
        //7.释放资源
        //session.close();
    }

    @Test
    public void findById() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class, 3);
        System.out.println(student);
        session.getTransaction().commit();
    }

    @Test
    public void delete() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class, 22);
        session.delete(student);
        session.getTransaction().commit();
    }

    @Test
    public void update() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class, 12);
        student.setClassId(6);
        session.getTransaction().commit();
    }

    @Test
    public void findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        String hql = "from Student";
        Query query = session.createQuery(hql);
        List<Student> students = query.list();
        for(Student student : students) {
            System.out.println(student);
        }
        session.getTransaction().commit();
    }


}
