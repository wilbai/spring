package com.wil;

import com.wil.crm.entity.Task;
import com.wil.crm.service.TaskService;
import com.wil.crm.service.impl.TaskServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wil on 2017/11/16.
 */
public class FastDfsTest {

    @Test
    public void uploadFile() throws IOException, MyException {

        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.84.130:22122,192.168.84.131:22122");

        //初始化
        ClientGlobal.initByProperties(properties);

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();

        //存储服务器客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);

        InputStream inputStream = new FileInputStream("d:/b.txt");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        //metadata 元数据
        NameValuePair[] nameValuePair = new NameValuePair[2];
        nameValuePair[0] = new NameValuePair("width", "500");
        nameValuePair[1] = new NameValuePair("device", "iPhone X");


        //String[] results = storageClient.upload_file(bytes, "txt", null);没有nameValuePair时
        String[] results = storageClient.upload_file(bytes, "txt", nameValuePair);
        for(String str : results) {
            System.out.println(str);
        }
        //group1
        //M00/00/00/wKhUgloOz26AWY2qAAAAIemlk4I697.txt

        inputStream.close();

    }

    @Test
    public void download() throws IOException, MyException {

        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.84.130:22122,192.168.84.131:22122");

        ClientGlobal.initByProperties(properties);

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();

        StorageClient storageClient = new StorageClient(trackerServer, null);

        byte[] bytes = storageClient.download_file("group1", "M00/00/00/wKhUjFoPk7KAAtDXAAAAIemlk4I656.txt");

        //获取metadata数组
        NameValuePair[] nameValuePairs = storageClient.get_metadata("group1", "M00/00/00/wKhUjFoPk7KAAtDXAAAAIemlk4I656.txt");
        for(NameValuePair nameValuePair : nameValuePairs) {
            System.out.println(nameValuePair.getName() + "-->" + nameValuePair.getValue());
        }

        //fileInfo
        FileInfo fileInfo = storageClient.get_file_info("group1", "M00/00/00/wKhUjFoPk7KAAtDXAAAAIemlk4I656.txt");
        System.out.println("crc32:" + fileInfo.getCrc32());
        System.out.println("createTime:" + fileInfo.getCreateTimestamp());
        System.out.println("fileSize:" + fileInfo.getFileSize());
        System.out.println("sourceIp:" + fileInfo.getSourceIpAddr());

        FileOutputStream outputStream = new FileOutputStream("D:/new.txt");
        outputStream.write(bytes,0,bytes.length);
        outputStream.flush();
        outputStream.close();

    }

    @Test
    public void md5() {
        System.out.println(DigestUtils.md5Hex("mysql" + "000000"));
    }



}
