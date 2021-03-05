package com.lcl.springcloud.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcl.springcloud.model.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/516:50
 */
@Repository
@Mapper
public interface StorageRepository extends BaseMapper<Storage> {
}
