package com.wil;

import com.wil.rabbit.HelloSender;
import com.wil.rabbit.MsgSender;
import com.wil.rabbit.MsgSenderFanout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wil on 2019/3/1.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitHelloTest {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    private MsgSender msgSender;
    @Autowired
    private MsgSenderFanout msgSenderFanout;

    @Test
    public void sendFanout() throws Exception {
        msgSenderFanout.send();
    }

    @Test
    public void hello() {
        helloSender.send();
    }

    @Test
    public void oneToMany() throws Exception {
        for (int i=0; i<100; i++){
            helloSender.send(i);
            Thread.sleep(300);
        }
    }

    @Test
    public void send1() throws Exception {
        msgSender.send1();
    }

    @Test
    public void send2() throws Exception {
        msgSender.send2();
    }


}
