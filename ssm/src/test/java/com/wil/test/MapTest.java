package com.wil.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2018/5/17.
 */
public class MapTest {

    public static void main(String[] args) {
        Map<String, Object> countMap = new HashMap<>();
        List<Map<String, Object>> countList = new ArrayList<>();

        countMap.put("60分以下", 3);

        countMap.put("60分以上", 5);

        countList.add(countMap);
        System.out.println(countList);
    }


}
