package com.wil.crm.mapper;

/**
 * Created by wil on 2017/11/7.
 */
import java.util.List;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Dept;
import com.wil.crm.example.AccountExample;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("com/wil/crm/example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("com/wil/crm/example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account findByMobile(@Param("mobile") String mobile);

    List<Account> findPageByParam(@Param("start") Integer start,
                                  @Param("length") Integer length,
                                  @Param("deptId") Integer deptId,
                                  @Param("accountName") String accountName);

    Long countByDeptId(@Param("deptId") Integer deptId);

    Long countAccountByDeptId(@Param("id") Integer id);

    List<Dept> findDeptListByAccountId(@Param("accountId") Integer accountId);
}
