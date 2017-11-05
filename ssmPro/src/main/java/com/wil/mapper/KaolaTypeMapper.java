package com.wil.mapper;

import com.wil.entity.KaolaType;
import com.wil.entity.KaolaTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KaolaTypeMapper {
    long countByExample(KaolaTypeExample example);

    int deleteByExample(KaolaTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KaolaType record);

    int insertSelective(KaolaType record);

    List<KaolaType> selectByExample(KaolaTypeExample example);

    KaolaType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KaolaType record, @Param("example") KaolaTypeExample example);

    int updateByExample(@Param("record") KaolaType record, @Param("example") KaolaTypeExample example);

    int updateByPrimaryKeySelective(KaolaType record);

    int updateByPrimaryKey(KaolaType record);
}