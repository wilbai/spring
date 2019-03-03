package com.wil.mapper;

import com.wil.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wil on 2017/12/8.
 */
@Mapper
public interface StudentMapper {

    @Insert("insert into t_stu (name, class_id) values (#{name}, #{classId})")
    public void save(@Param("name") String name,@Param("classId") Integer classId);

    @Select("select * from t_stu where id = #{id}")
    public Student getStudentById(Integer id);
}
