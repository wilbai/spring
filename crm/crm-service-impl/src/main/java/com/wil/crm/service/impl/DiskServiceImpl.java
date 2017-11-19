package com.wil.crm.service.impl;

import com.wil.crm.entity.Disk;
import com.wil.crm.example.DiskExample;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.files.FileStore;
import com.wil.crm.mapper.DiskMapper;
import com.wil.crm.service.DiskService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wil on 2017/11/15.
 */
@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Autowired
    @Qualifier("fastDFSFileStoreImpl")
    private FileStore fileStore;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void saveNewFolder(Disk disk) {
        disk.setType(Disk.TYPE_DIR);
        disk.setCreateTime(new Date());
        diskMapper.insertSelective(disk);
    }

    @Override
    public List<Disk> findDiskListByPId(Integer pId) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPIdEqualTo(pId);
        return diskMapper.selectByExample(diskExample);
    }

    @Override
    public Disk findById(Integer id) {
        return diskMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void saveNewFile(InputStream inputStream, long size, String fileName, Integer pId, Integer accountId) throws ServiceException {

        Disk disk = new Disk();
        disk.setCreateTime(new Date());
        disk.setType(Disk.TYPE_FILE);
        disk.setAccountId(accountId);
        disk.setFileSize(FileUtils.byteCountToDisplaySize(size));
        disk.setByteSize(Double.longBitsToDouble(size));
        disk.setpId(pId);
        disk.setName(fileName);
        disk.setDownloadCount(0);

        try {
            String saveName = fileStore.saveFile(inputStream, fileName);
            disk.setSaveName(saveName);
            diskMapper.insertSelective(disk);
        } catch (IOException ex) {
            throw new ServiceException(ex, "文件上传异常");
        }


    }

    @Override
    @Transactional
    public InputStream getDownloadInputStream(Integer id) throws IOException {

        Disk disk = diskMapper.selectByPrimaryKey(id);
        if(disk == null || disk.getType().equals(Disk.TYPE_DIR)) {
            throw new ServiceException("文件不存在或已被删除");
        }

        disk.setDownloadCount(disk.getDownloadCount()+1);
        diskMapper.updateByPrimaryKeySelective(disk);

        byte[] bytes = fileStore.getFile(disk.getSaveName());
        return new ByteArrayInputStream(bytes);

    }
}
