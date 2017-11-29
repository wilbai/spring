package com.wil.hibernate;

import com.wil.pojo.Address;
import com.wil.pojo.User;
import com.wil.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wil on 2017/11/28.
 */
public class OneToManyTest {

    private Session session;

    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.getTransaction().begin();
    }

    //@After
    public void after() {
        session.getTransaction().commit();
    }

    @Test
    public void findUser() {

        User user = (User) session.get(User.class, 3);


        //Hibernate.initialize(user.getAddressSet());


        //System.out.println(user.getName());
        Set<Address> addressSet = user.getAddressSet();
        for(Address address : addressSet) {
            System.out.println(address.getId() + "->" + address.getName());
        }
        session.getTransaction().commit();
        /*Address address = (Address) session.get(Address.class, 6);
        address.setUser(user);*/

    }

    @Test
    public void saveAddress() {
        User user = new User();
        user.setName("简三");

        //User user = (User) session.get(User.class, 12);

        Address address = new Address();
        address.setName("大连");
        address.setUser(user);

        Address address2 = new Address();
        address2.setName("南京");
        address2.setUser(user);

        /*Set<Address> addressSet = new HashSet<>();
        addressSet.add(address);
        addressSet.add(address2);
        user.setAddressSet(addressSet);*/

        //先存1 再存多 一个地址只能有一个人 一个人可以有多个地址 关系由多的一方维护
        session.save(user);
        session.save(address);
        session.save(address2);

    }

    @Test
    public void delete() {
        Criteria criteria= session.createCriteria(Address.class);
        criteria.add(Restrictions.eq("user.id", 23));
        List<Address> addresses = criteria.list();
        for(Address address : addresses) {
            session.delete(address);
        }
        User user = (User) session.get(User.class, 23);
        session.delete(user);
    }

    @Test
    public void delete2() {
        User user = (User) session.get(User.class, 13);
        session.delete(user);
    }

}
