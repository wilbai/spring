package com.wil.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wil on 2017/12/6.
 */
public class RedisClusterTest {

    @Test
    public void save() throws IOException {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(8);
        config.setMinIdle(3);

        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.84.140",6000));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6001));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6002));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6003));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6004));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6005));

        JedisCluster jedisCluster = new JedisCluster(hostAndPorts,config);

        jedisCluster.set("name", "Alex");
        jedisCluster.close();

    }

    @Test
    public void get() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(8);
        config.setMinIdle(3);

        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.84.140",6000));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6001));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6002));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6003));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6004));
        hostAndPorts.add(new HostAndPort("192.168.84.140",6005));

        JedisCluster jedisCluster = new JedisCluster(hostAndPorts,config);
        System.out.println(jedisCluster.get("name"));;
    }
}
