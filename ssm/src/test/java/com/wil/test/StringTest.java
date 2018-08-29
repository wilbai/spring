package com.wil.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wil on 2018/5/12.
 */
public class StringTest {
    public static void main(String[] args) {
        /*String a = "1,12,2,23,4,";
        String b = a.substring(0, a.length()-1);
        System.out.println(a.substring(0, a.length()-1));*/

        List<Integer> all = new ArrayList<>();
        all.add(1);
        all.add(12);
        all.add(2);
        all.add(23);
        all.add(4);

        List<Integer> res = new ArrayList<>();
        Random r = new Random();
        int index = 0;

        for(int i = 0; i < 3; i++) {
            index = r.nextInt(all.size()-1);
            res.add(all.get(index));
            all.remove(index);
        }
        System.out.println(res);
    }
}
