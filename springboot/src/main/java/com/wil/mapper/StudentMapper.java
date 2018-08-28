package com.wil.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wil on 2017/12/8.
 */
@Mapper
public class StudentMapper {

    @Insert("insert into t_stu (name, class_id) values (#{name}, #{classId})")
    public void save(@Param("name") String name,@Param("classId") Integer classId) {

    }

}
