package com.wil;

import com.wil.dao.BaseDao;
import com.wil.dao.ProductDao;
import org.junit.Test;

/**
 * Created by wil on 2017/11/30.
 */
public class ClazzTest {

    @Test
    public void test() {
        ProductDao productDao = new ProductDao();
        System.out.println("Dao new ....");
    }

}
