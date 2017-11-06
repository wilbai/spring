package com.wil.mapper;

import com.wil.entity.AccountDeptExample;
import com.wil.entity.AccountDept;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDeptMapper {
    long countByExample(AccountDeptExample example);

    int deleteByExample(AccountDeptExample example);

    int deleteByPrimaryKey(AccountDept key);

    int insert(AccountDept record);

    int insertSelective(AccountDept record);

    List<AccountDept> selectByExample(AccountDeptExample example);

    int updateByExampleSelective(@Param("record") AccountDept record, @Param("example") AccountDeptExample example);

    int updateByExample(@Param("record") AccountDept record, @Param("example") AccountDeptExample example);
}