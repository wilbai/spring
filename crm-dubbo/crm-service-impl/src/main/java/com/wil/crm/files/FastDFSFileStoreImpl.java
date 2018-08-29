package com.wil.crm.files;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wil on 2017/11/18.
 */
public class FastDFSFileStoreImpl implements FileStore {

    @Value("${fastdfs.tracker.server}")
    private String trackerServer;

    private final String appendFlag = "#";


    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException {

        StorageClient storageClient = getStorageClient();

        String extName = "";
        if(fileName.lastIndexOf(".") != -1) {
            extName = fileName.substring(fileName.lastIndexOf(".")+1);
        }

        try {
            String[] results = storageClient.upload_file(IOUtils.toByteArray(inputStream), extName, null);

            //group1#xxxxx
            StringBuilder builder = new StringBuilder();

            builder.append(results[0]).append(appendFlag).append(results[1]);

            return builder.toString();
        } catch (MyException e) {
            e.printStackTrace();
            throw new RuntimeException("文件保存到FastDFS异常", e);
        }

    }

    @Override
    public byte[] getFile(String fileName) throws IOException {

        StorageClient storageClient = getStorageClient();
        String[] array = fileName.split(appendFlag);
        String groupName = array[0];
        String filePath = array[1];

        try {
            return storageClient.download_file(groupName, filePath);
        } catch (MyException e) {
            e.printStackTrace();
            throw new RuntimeException("从FastDFS获取文件异常", e);
        }
    }

    private StorageClient getStorageClient() {

        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServer);

        try {
            ClientGlobal.initByProperties(properties);
            TrackerClient client = new TrackerClient();
            TrackerServer trackerServer = client.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);

            return storageClient;

        } catch (IOException | MyException e) {
            e.printStackTrace();
            throw new RuntimeException("获取StorageClient异常", e);
        }

    }

}
