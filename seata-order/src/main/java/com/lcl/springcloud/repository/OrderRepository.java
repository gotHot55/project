package com.lcl.springcloud.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcl.springcloud.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import sun.util.resources.ga.LocaleNames_ga;

import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/511:58
 */
@Repository
@Mapper
public interface OrderRepository extends BaseMapper<Order> {
    @Insert("insert into `order` (user_id,product_id,`count`,money,status) values (#{userId},#{productId},#{count},#{money},#{status})")
    void add(@Param("userId") Long userId,@Param("productId") Long productId,@Param("count") Integer count,@Param("money") BigDecimal money,@Param("status") Integer status);

    @Update("update `order` set status=#{status} where user_id=#{userId} and product_id=#{productId}")
    void update(@Param("status") Integer status, @Param("userId") Long userId, @Param("productId") Long productId);
}
