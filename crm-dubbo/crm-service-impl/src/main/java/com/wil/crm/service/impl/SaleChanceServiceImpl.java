package com.wil.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.entity.SaleChance;
import com.wil.crm.entity.SaleChanceRecord;
import com.wil.crm.example.SaleChanceExample;
import com.wil.crm.example.SaleChanceRecordExample;
import com.wil.crm.mapper.CustomerMapper;
import com.wil.crm.mapper.SaleChanceMapper;
import com.wil.crm.mapper.SaleChanceRecordMapper;
import com.wil.crm.service.SaleChanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/13.
 */
@Service
public class SaleChanceServiceImpl implements SaleChanceService {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    @Autowired
    private SaleChanceRecordMapper saleChanceRecordMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Value("#{'${sale.progress}'.split(',')}")
    private List<String> progressList;



    /**
     * 查找当前account的chances并分页
     * @param account
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<SaleChance> pageMySaleChances(Account account, Integer pageNo) {

        PageHelper.startPage(pageNo, 10);
        List<SaleChance> saleChanceList = saleChanceMapper.findChancesByAccountId(account.getId());
        return new PageInfo<SaleChance>(saleChanceList);
    }

    /**
     * 根据id查找销售机会与客户详情
     * @param id
     * @return
     */
    @Override
    public SaleChance findSaleChanceWithCustomerById(Integer id) {
        return saleChanceMapper.findChanceWithCustomerById(id);
    }

    @Override
    public List<String> findAllProgress() {
        return progressList;
    }

    /**
     * 根据id查找recordList
     * @param id
     * @return
     */
    @Override
    public List<SaleChanceRecord> findAllRecordListBySaleId(Integer id) {
        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(id);
        return saleChanceRecordMapper.selectByExample(saleChanceRecordExample);
    }

    /**
     * 新增销售机会
     *
     * @param saleChance
     */
    @Override
    @Transactional
    public void saveSaleChance(SaleChance saleChance) {
        saleChance.setCreateTime(new Date());
        saleChance.setLastTime(new Date());
        saleChanceMapper.insertSelective(saleChance);

        //更新对应客户的最后跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustomerId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKey(customer);

        //判断销售机会详情是否有值，增加跟进记录
        if(StringUtils.isNotEmpty(saleChance.getContent())) {
            SaleChanceRecord saleChanceRecord = new SaleChanceRecord();
            saleChanceRecord.setContent(saleChance.getContent());
            saleChanceRecord.setCreateTime(new Date());
            saleChanceRecord.setSaleId(saleChance.getId());
            saleChanceRecordMapper.insert(saleChanceRecord);
        }
    }

    @Override
    @Transactional
    public void deleteSaleChanceById(Integer id) {
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);

        //删除对应的跟进记录
        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(id);
        saleChanceRecordMapper.deleteByExample(saleChanceRecordExample);

        //删除销售机会
        saleChanceMapper.deleteByPrimaryKey(id);

        //判断该客户是否有其他的销售机会，若没有，则最后跟进时间设置为null
        //若有，则修改为最近销售机会的跟进时间
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustomerIdEqualTo(saleChance.getCustomerId());
        saleChanceExample.setOrderByClause("last_time desc");
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);

        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustomerId());
        if(saleChanceList.isEmpty()) {
            customer.setLastContactTime(null);
        } else {
            customer.setLastContactTime(saleChanceList.get(0).getLastTime());
        }
        customerMapper.updateByPrimaryKey(customer);

    }

    /**
     * 添加新的跟进记录
     * @param record
     */
    @Override
    @Transactional
    public void saveSaleChanceRecord(SaleChanceRecord record) {
        record.setCreateTime(new Date());
        saleChanceRecordMapper.insert(record);

        //销售机会最后的跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(record.getSaleId());
        saleChance.setLastTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //客户的跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustomerId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

    }

    /**
     * 更新销售机会进度
     *
     * @param id
     * @param progress
     */
    @Override
    @Transactional
    public void updateChanceState(Integer id, String progress) {
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        saleChance.setProgress(progress);
        //销售机会的lastTime
        saleChance.setLastTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //客户的lastContactTime
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustomerId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

        //新增一个跟进记录
        SaleChanceRecord record = new SaleChanceRecord();
        record.setSaleId(id);
        record.setCreateTime(new Date());
        record.setContent("将当前进度修改为: ["+progress+"]");
        saleChanceRecordMapper.insertSelective(record);
    }

    /**
     * 根据客户id查找销售机会列表
     * @param id
     * @return
     */
    @Override
    public List<SaleChance> findAllChancesByCustomerId(Integer id) {
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustomerIdEqualTo(id);
        saleChanceExample.setOrderByClause("last_time desc");

        return saleChanceMapper.selectByExample(saleChanceExample);
    }

    @Override
    public List<Map<String, Object>> countChanceByProcess(Account account) {

        List<Map<String, Object>> countList = saleChanceMapper.countProcess(account.getId());
        long sum = 0L;
        for(Map<String, Object> map : countList) {
            sum += (long) map.get("count");

        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        for(Map<String, Object> map : countList) {
            long num = (long) map.get("count");
            String res = numberFormat.format((float) num / (float) sum * 100);
            map.remove("count");
            map.put("count", res);
        }

        return countList;
    }


}



