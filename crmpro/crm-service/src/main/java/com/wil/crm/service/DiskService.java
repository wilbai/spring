package com.wil.crm.service;

import com.wil.crm.entity.Disk;

import java.util.List;

/**
 * Created by wil on 2017/11/15.
 */
public interface DiskService {
    void saveNewFolder(Disk disk);

    List<Disk> findDiskListByPId(Integer pId);
}
