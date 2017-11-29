package com.wil.hibernate;

import com.wil.pojo.Movie;
import com.wil.pojo.Post;
import com.wil.pojo.User;
import com.wil.util.HibernateUtil;
import org.hibernate.Cache;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by wil on 2017/11/29.
 */
public class CacheTest {

    @Test
    public void level1() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Post post = (Post) session.get(Post.class, 1);
        session.clear();
        System.out.println(session.contains(post));
        Post post2 = (Post) session.get(Post.class, 1);
        session.evict(post2);
        System.out.println(session.contains(post2));
        Post post3 = (Post) session.get(Post.class, 1);


        session.getTransaction().commit();
    }

    @Test
    public void level2() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Post post = (Post) session.get(Post.class, 1);
        session.getTransaction().commit();

        Cache cache = HibernateUtil.getSessionFactory().getCache();
        cache.evictEntityRegion(Post.class);

        Session session2 = HibernateUtil.getSession();
        session2.beginTransaction();

        Post post2 = (Post) session2.get(Post.class, 1);
        session2.getTransaction().commit();
       // Post post3 = (Post) session.get(Post.class, 1);

    }

    @Test
    public void optimisticLock() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Movie movie = new Movie();
        movie.setName("穆赫兰道");
        session.save(movie);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Session session = HibernateUtil.getSession();
                session.beginTransaction();

                Movie movie = new Movie();
                movie.setName("这个杀手不太冷");
                session.save(movie);
                session.getTransaction().commit();
            }
        });
        thread.start();

        session.getTransaction().commit();


    }
    @Test
    public void optimisticLock2() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Movie movie = (Movie) session.get(Movie.class,"4028d4816005e140016005e1457f0000");

        movie.setName("爆裂鼓手");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Session session = HibernateUtil.getSession();
                session.beginTransaction();

                Movie movie = (Movie) session.get(Movie.class,"4028d4816005e140016005e1457f0000");

                movie.setName("爆裂鼓手-2");

                session.getTransaction().commit();
            }
        });
        thread.start();

        session.getTransaction().commit();


    }

    @Test
    public void pessimisticLock() throws InterruptedException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        User user = (User) session.get(User.class, 8, LockOptions.UPGRADE);
        user.setName("洛可");

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Session session2 = HibernateUtil.getSession();
                session2.beginTransaction();
                User user = (User) session2.get(User.class, 8);
                user.setName("洛可-2");

                session2.getTransaction().commit();
                System.out.println("session2 end...");
            }
        });
        thread.start();*/
        Thread.sleep(10000);

        session.getTransaction().commit();
        System.out.println("session end...");
    }



}
