package com.wil.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wil on 2018/5/17.
 */
public class SubStringTest {

    public static void main(String[] args) {
        List<String> wrongIds = new ArrayList<>();
        wrongIds.add("1");
        wrongIds.add("2");
        wrongIds.add("3");
        wrongIds.add("4");
        wrongIds.add("5");
        wrongIds.add("6");

        StringBuilder builder = new StringBuilder();
        for(String id : wrongIds) {
            builder.append(id);
            builder.append(",");
        }
        String wrong = builder.toString();
        String wrongResIds = wrong.substring(0, wrong.length()-1);
        System.out.println(wrongResIds);

    }

}
