package com.wil.weixin.mq;

import com.alibaba.fastjson.JSON;
import com.wil.weixin.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wil on 2017/11/22.
 */
@Component
public class WeiXinConsumer {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @JmsListener(destination = "weixinMessage-queue")
    public void sendMessageToAccount(String json) {
        Map<String, Object> map = JSON.parseObject(json, HashMap.class);
        String id = (String) map.get("accountId");
        weiXinUtil.sendTextMessageToAccount(Arrays.asList(Integer.parseInt(id)), map.get("message").toString());

    }


}
