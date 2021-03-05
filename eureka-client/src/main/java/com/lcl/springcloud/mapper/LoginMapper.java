package com.lcl.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcl.springcloud.model.Login;
import org.apache.ibatis.annotations.Mapper;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2314:18
 */
@Mapper
public interface LoginMapper extends BaseMapper<Login> {
}
