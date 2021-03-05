package com.lcl.springcloud.service;

import com.lcl.springcloud.model.ChangeEventModel;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2317:02
 */
public interface AccountInfoService {
    /**
     *    更新帐号余额-发送消息
     * @param changeEventModel
     */
    void updateAmount(ChangeEventModel changeEventModel);

    /**
     * 更新帐号余额-本地事务
     */
    void doUpdateAccount(ChangeEventModel changeEventModel);
}
