package com.wil.hibernate;

import com.wil.pojo.Card;
import com.wil.pojo.Person;
import com.wil.pojo.Post;
import com.wil.pojo.PostContent;
import com.wil.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wil on 2017/11/28.
 */
public class OneToOneTest {

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
    public void save() {
        Person person = new Person();
        person.setName("简三");

        Card card = new Card();
        card.setCardName("123456789");

        card.setPerson(person);

        //主键绑定 主键生成策略：外键 为了范式
        //主键不自动增长的（依赖别的表的主键）表是从表
        //从表的对象维护关系，将主表达的对象添加进自己的属性中
        //先保存主表，再保存从表

        session.save(person);
        session.save(card);
    }

    @Test
    public void find() {
        Person person = (Person) session.get(Person.class, 1);
        System.out.println(person.getName());
        System.out.println(person.getCard().getCardName());
    }

    @Test
    public void delete() {
        Person person = (Person) session.get(Person.class, 3);
        session.delete(person);
    }

    @Test
    public void savePost() {
        Post post = new Post();
        post.setTitle("黑美人");

        PostContent postContent = new PostContent();
        postContent.setPost(post);
        postContent.setContent("哈哈哈哈哈哈哈哈哈哈哈哈哈");

        post.setPostContent(postContent);

        //一对一 为了懒加载 分别存入对方的主键作为外键
        //双向添加
        session.save(post);
        session.save(postContent);

    }

    @Test
    public void findByPost() {
        Post post = (Post) session.get(Post.class, 1);
        System.out.println(post.getTitle());
        System.out.println(post.getPostContent().getContent());
    }




}
