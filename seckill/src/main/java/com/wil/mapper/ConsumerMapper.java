package com.wil.mapper;

import com.wil.entity.Consumer;
import com.wil.entity.ConsumerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerMapper {
    long countByExample(ConsumerExample example);

    int deleteByExample(ConsumerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    List<Consumer> selectByExample(ConsumerExample example);

    Consumer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Consumer record, @Param("example") ConsumerExample example);

    int updateByExample(@Param("record") Consumer record, @Param("example") ConsumerExample example);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);

    Consumer findByMobile(@Param("mobile") String mobile);
}