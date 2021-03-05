package com.lcl.springcloud.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcl.springcloud.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/515:57
 */
@Repository
@Mapper
public interface AccountRepository extends BaseMapper<Account> {

}
