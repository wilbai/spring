package com.wil.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by wil on 2017/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")
public class WeiXinTestCase {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Test
    public void getAccessToken() {
        System.out.println(weiXinUtil.getAccessToken(WeiXinUtil.ACCESS_TOKEN_TYPE_CONTACT));
    }

    @Test
    public void createDept() {
        weiXinUtil.createDept(1001, 1, "市场部");
    }

    @Test
    public void deleteDept() {
        weiXinUtil.deleteDept(1001);
    }

    @Test
    public void updateDept() {
       weiXinUtil.updateDept(1001, 1,"企划部");
    }

    @Test
    public void createAccount() {
         weiXinUtil.createAccount(3, "Archer", Arrays.asList(2), "18755007722");
    }

    @Test
    public void deleteAccount() {
        weiXinUtil.deleteAccount(3);
    }

    @Test
    public void sendTextMessageToAccount() {
        weiXinUtil.sendTextMessageToAccount(Arrays.asList(2, 3), "明早十点五楼会议室开会,不要迟到");
    }
}
