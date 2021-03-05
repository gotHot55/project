package com.lcl.springcloud.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2410:57
 */
@Mapper
//@Repository
public interface AccountInfoRepository {
    @Update("update account_info set account_balance = account_balance + #{amount} where account_no = #{accountNo}")
    int updateAmount(@Param("amount") Double amount, @Param("accountNo") String accountNo);

    @Select("select count(1) from de_duplication  where tx_no = #{txNo}")
    int isExistTx(String txNo);

    @Insert("insert into de_duplication values(#{txNo} , now())")
    int addTx(String txNo);

}
