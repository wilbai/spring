package com.wil.test;

import org.apache.commons.text.similarity.JaccardSimilarity;

import java.text.DecimalFormat;

/**
 * Created by wil on 2018/5/17.
 */
public class HanlpTest {

    public static void main(String[] args) {

        String s2 = "Portable版的基本功能指的是，除CRF分词、依存句法分析外的全部功能。如果用户有自定义的需求， 可以使用hanlp.properties进行配置。";
        String s1 = "Portable版的基本功能指的是，除CRF分词、依存句法分析外的全部功能。 可以使用hanlp.properties配置";

        //计算jaccard相似系数
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        double jcdsimilary3 = jaccardSimilarity.apply(s1, s2);
        System.out.println("jcdsimilary3:"+jcdsimilary3);

        double score = 100;
        score = jcdsimilary3*score;
        System.out.println(score);

        double f = 111231.5585;
        DecimalFormat df = new DecimalFormat("#");
        System.out.println(df.format(f));
    }


}
