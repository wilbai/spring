package com.wil;

import com.wil.crm.entity.Task;
import com.wil.crm.mapper.TaskMapper;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by wil on 2017/12/6.
 */
public class TimeTest {


    @Test
    public void time() {

        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());
        DateTime dateTime = new DateTime(date.getTime());
        System.out.println(dateTime.toString());
        String dates = dateTime.toString();
        DateTime dateTime1 = new DateTime(dates);
        System.out.println(dateTime1.toDate());
    }


}
