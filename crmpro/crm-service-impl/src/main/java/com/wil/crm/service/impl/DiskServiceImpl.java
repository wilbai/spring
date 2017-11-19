package com.wil.crm.service.impl;

import com.wil.crm.entity.Disk;
import com.wil.crm.example.DiskExample;
import com.wil.crm.mapper.DiskMapper;
import com.wil.crm.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wil on 2017/11/15.
 */
@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

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
}
