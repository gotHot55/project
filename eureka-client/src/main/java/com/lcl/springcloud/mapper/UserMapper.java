package com.lcl.springcloud.mapper;

import com.lcl.springcloud.model.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2315:52
 */
@Mapper
public interface UserMapper {

    @Select("select * from login where id = #{id}")
    Login getById(@Param("id") Integer id);
}
