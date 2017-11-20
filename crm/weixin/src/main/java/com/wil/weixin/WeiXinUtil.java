package com.wil.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wil.weixin.weixin.WeiXinException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wil on 2017/11/20.
 */
@Component
public class WeiXinUtil {

    private final static String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    @Value("${weixin.corpID}")
    private String corpID;
    @Value("${weixin.secret}")
    private String secret;

    private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    String url = String.format(GET_ACCESS_TOKEN_URL, corpID, secret);
                    String resultJson = sendHttpGetRequest(url);
                    Map<String, Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errocode").equals(0)) {
                        return (String) map.get("access_token");
                    }
                    throw  new WeiXinException();
                }
            });

    public String getAccessToken() {
        try {
            return accessTokenCache.get("");
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("获取AccessToken异常",e);
        }
    }


    private String sendHttpGetRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new WeiXinException("Http请求异常");
        }
    }




}
