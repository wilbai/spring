package com.wil.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by wil on 2017/11/24.
 */
public class ShiroTestCase {

    @Test
    public void helloWorldWihRealm() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("seber", "123");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        } catch (UnknownAccountException ex) {
            System.out.println("帐号不存在");
        } catch (LockedAccountException ex) {
            System.out.println("帐号被禁用");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("帐号或密码错误");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            System.out.println("权限不足");
        }
        subject.logout();
    }

    @Test
    public void helloWorld() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("seber", "123");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        } catch (UnknownAccountException ex) {
            System.out.println("帐号不存在");
        } catch (LockedAccountException ex) {
            System.out.println("帐号被禁用");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("帐号或密码错误");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            System.out.println("权限不足");
        }
        subject.logout();
    }

    @Test
    public void checkSubjectRole() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-roles.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("seber", "123");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");

            System.out.println("is admin?" + subject.hasRole("admin"));
            System.out.println("is HR?" + subject.hasRole("hr"));
            System.out.println("is admin and CEO?" + subject.hasAllRoles(Arrays.asList("admin", "CEO")));
            boolean[] resultArray = subject.hasRoles(Arrays.asList("admin", "CEO", "HR", "UFO"));
            for(boolean b : resultArray) {
                System.out.println(b);
            }
            System.out.println("华丽的分割线------------------------");

            System.out.println("create? " + subject.isPermitted("create"));
            subject.checkPermission("delete");

            //判断是否具有该角色，没有则异常
            subject.checkRole("admin");

        } catch (UnknownAccountException ex) {
            System.out.println("帐号不存在");
        } catch (LockedAccountException ex) {
            System.out.println("帐号被禁用");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("帐号或密码错误");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            System.out.println("权限不足");
        }
        subject.logout();
    }

}
