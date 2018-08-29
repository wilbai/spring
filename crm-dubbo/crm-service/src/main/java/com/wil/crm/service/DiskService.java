package com.wil.crm.service;

import com.wil.crm.entity.Disk;
import com.wil.crm.exception.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wil on 2017/11/15.
 */
public interface DiskService {
    void saveNewFolder(Disk disk);

    List<Disk> findDiskListByPId(Integer pId);

    Disk findById(Integer pId);

    /**
     * 保存上传的文件
     * @param inputStream
     * @param size
     * @param fileName
     * @param pId
     * @param accountId
     */
    void saveNewFile(InputStream inputStream, long size, String fileName, Integer pId, Integer accountId) throws ServiceException;

    InputStream getDownloadInputStream(Integer id) throws IOException;

    void saveNewDisk(Disk disk);

    void updateDisk(Disk disk);
}
