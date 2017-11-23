package com.wil.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wil.weixin.exception.WeiXinException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wil on 2017/11/20.
 */
@Component
public class WeiXinUtil {

    public final static String ACCESS_TOKEN_TYPE_NORMAL = "normal";
    public final static String ACCESS_TOKEN_TYPE_CONTACT = "contact";


    private final static String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    private final static String POST_NEW_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";

    private final static String GET_DELETE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=%s&id=%s";

    private final static String POST_UPDATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=%s";

    private final static String POST_NEW_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";

    private final static String GET_DELETE_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=%s&userid=%s";

    private final static String POST_SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
    @Value("${weixin.corpID}")
    private String corpID;
    @Value("${weixin.secret}")
    private String secret;
    @Value("${weixin.contact.secret}")
    private String contactSecret;
    @Value("${weixin.crm.agentId}")
    private Integer agentId;

    private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String type) throws Exception {
                    String url;
                    if(ACCESS_TOKEN_TYPE_NORMAL.equals(type)) {
                        url = String.format(GET_ACCESS_TOKEN_URL, corpID, secret);
                    } else {
                        url = String.format(GET_ACCESS_TOKEN_URL, corpID, contactSecret);
                    }
                    String resultJson = sendHttpGetRequest(url);
                    Map<String, Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errcode").equals(0)) {
                        return (String) map.get("access_token");
                    }
                    throw  new WeiXinException();
                }
            });

    public String getAccessToken(String type) {
        try {
            return accessTokenCache.get(type);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("获取AccessToken异常",e);
        }
    }

    /**
     * 创建部门
     * @param id
     * @param parentId
     * @param name
     */
    public void createDept(Integer id, Integer parentId, String name) {

        String url = String.format(POST_NEW_DEPT_URL,getAccessToken(ACCESS_TOKEN_TYPE_CONTACT));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("parentid", parentId);
        map.put("name", name);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(map));
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
          throw new WeiXinException("创建部门失败: " + resultJson);
        }
    }

    /**
     * 根据id删除部门
     * @param id
     */
    public void deleteDept(Integer id) {
        String url = String.format(GET_DELETE_DEPT_URL, getAccessToken(ACCESS_TOKEN_TYPE_CONTACT), id);
        String resultJson = sendHttpGetRequest(url);
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeiXinException("删除部门失败: " + resultJson);
        }
    }

    /**
     * 更新部门
     * @param id
     * @param parentId
     * @param name
     */
    public void updateDept(Integer id, Integer parentId, String name) {

        String url = String.format(POST_UPDATE_DEPT_URL,getAccessToken(ACCESS_TOKEN_TYPE_CONTACT));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("parentid", parentId);
        map.put("name", name);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(map));
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeiXinException("更新部门失败: " + resultJson);
        }
    }

    /**
     * 创建员工
     * @param id
     * @param name
     * @param deptIdList 所属部门列表
     * @param mobile
     */
    public void createAccount(Integer id, String name, List<Integer> deptIdList, String mobile) {
        String url = String.format(POST_NEW_ACCOUNT_URL, getAccessToken(ACCESS_TOKEN_TYPE_CONTACT));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid", id);
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("department", deptIdList);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(map));
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeiXinException("新增员工失败: " + resultJson);
        }
    }

    /**
     * 根据id删除员工
     * @param id
     */
    public void deleteAccount(Integer id) {
        String url = String.format(GET_DELETE_ACCOUNT_URL, getAccessToken(ACCESS_TOKEN_TYPE_CONTACT), id);
        String resultJson = sendHttpGetRequest(url);
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeiXinException("删除员工失败: " + resultJson);
        }
    }

    /**
     * 发送文本消息
     * @param accountIdList
     * @param content
     */
    public void sendTextMessageToAccount(List<Integer> accountIdList, String content) {
        String url = String.format(POST_SEND_MESSAGE_URL,getAccessToken(ACCESS_TOKEN_TYPE_NORMAL));
        StringBuilder builder = new StringBuilder();
        for(Integer id : accountIdList) {
            builder.append(id).append("|");
        }
        String idString = builder.toString();
        idString = idString.substring(0, idString.lastIndexOf("|"));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("touser", "WuWanYang");
        map.put("msgtype", "text");
        map.put("agentid", agentId);
        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("content", content);
        map.put("text", contentMap);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(map));
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeiXinException("发送文本消失失败: " + resultJson);
        }


    }






    /**
     * get请求
     * @param url
     * @return
     */
    private String sendHttpGetRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            throw new WeiXinException("Http请求异常",e);
        }
    }

    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    private String sendHttpPostRequest(String url, String json) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Http请求异常",e);
        }
    }




}
