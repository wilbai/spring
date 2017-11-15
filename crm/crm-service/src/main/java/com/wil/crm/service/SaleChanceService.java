package com.wil.crm.service;

import com.github.pagehelper.PageInfo;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.SaleChance;
import com.wil.crm.entity.SaleChanceRecord;

import java.util.List;

/**
 * Created by wil on 2017/11/13.
 */
public interface SaleChanceService {
    /**
     * 查找当前account的chances并分页
     * @param account
     * @param pageNo
     * @return
     */
    PageInfo<SaleChance> pageMySaleChances(Account account, Integer pageNo);

    /**
     * 根据id查找销售机会与客户详情
     * @param id
     * @return
     */
    SaleChance findSaleChanceWithCustomerById(Integer id);

    List<String> findAllProgress();

    /**
     * 根据id查找recordList
     * @param id
     * @return
     */
    List<SaleChanceRecord> findAllRecordListBySaleId(Integer id);

    /**
     * 新增销售机会
     * @param saleChance
     */
    void saveSaleChance(SaleChance saleChance);

    void deleteSaleChanceById(Integer id);

    /**
     * 添加新的跟进记录
     * @param record
     */
    void saveSaleChanceRecord(SaleChanceRecord record);

    /**
     * 更新销售机会进度
     * @param id
     * @param progress
     */
    void updateChanceState(Integer id, String progress);

    /**
     * 根据客户id查找销售机会列表
     * @param id
     * @return
     */
    List<SaleChance> findAllChancesByCustomerId(Integer id);
}
