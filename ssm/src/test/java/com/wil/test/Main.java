package com.wil.test;

import com.alibaba.fastjson.JSON;
import com.wil.entity.User;
import org.junit.Test;

import java.util.*;

/**
 * Created by wil on 2017/12/23.
 */
public class Main {


    @Test
    public void hello() {

        //json -> List<Object>
        String jsonStr = "[{'id':'36', 'name':'伤心的人别听慢歌', 'addressId':'2'}," +
                "{'id':'37', 'name':'夜访吸血鬼', 'addressId':'3'}]";
        List<User> userList = JSON.parseArray(jsonStr, User.class);
        System.out.println(userList);

        //json -> object
        String objStr = "{'id':'38', 'name':'成名在望', 'addressId':'2'}";
        User user = JSON.parseObject(objStr, User.class);
        System.out.println(user);

        //object -> json
        User user1 = new User(1, "好好", 2);
        String json = JSON.toJSONString(user1);
        System.out.println(json);

        //List<Object>/object数组 -> json
        User[] users = new User[2];
        users[0] = new User(2, "拥抱", 1);
        users[1] = new User(3, "温柔", 3);
        String jsonStr2 = JSON.toJSONString(userList);
        String jsonStr3 = JSON.toJSONString(users);
        System.out.println(jsonStr2);
        System.out.println(jsonStr3);


    }

    /**
     * 给出一个字符集字符串,求出现次数最多的字符;如果有次数相同的,取最先出现的字符
     */
    @Test
    public void StringTest() {
        String s = "abcabcdeaabbbcdwe";
        char[] chars = s.toCharArray();
        Set<Character> characterSet = new HashSet<>();
        Map<Character, Integer> map = new HashMap<>();
        List<Integer> valueList = new ArrayList<>();
        for(char c : chars) {
            map.put(c, 0);
        }
        characterSet = map.keySet();

        for(char c : chars) {
            if(map.containsKey(c)) {
                map.put(c, map.get(c)+1);
            }
        }
        System.out.println(map);

        for(Character ch : characterSet) {
            Integer value = map.get(ch);
            valueList.add(value);
        }
        Object max = getMax(valueList);

        for(Character ch : characterSet) {
            if(max == map.get(ch)) {
                System.out.println(ch);
                break;
            }
        }

        //System.out.println(map);
        System.out.println(characterSet);
    }

    public Object getMax(List<Integer> list) {
        Object[] array = list.toArray();
        Arrays.sort(array);
        return array[array.length-1];
    }


}
