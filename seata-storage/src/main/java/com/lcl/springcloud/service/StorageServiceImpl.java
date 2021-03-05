package com.lcl.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcl.springcloud.model.Storage;
import com.lcl.springcloud.repository.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/516:53
 */
@Service
public class StorageServiceImpl implements StorageService {
    private Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    @Resource
    private StorageRepository storageRepository;
    @Override
    public void decrease(Long productId, Integer deCount) throws Exception {
        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);
        Storage storage = storageRepository.selectOne(wrapper);
        if (storage != null) {
            if (deCount.compareTo(storage.getResidue()) > 0) {
                logger.error("您买的商品库存不足!");
                throw new Exception("您买的商品库存不足！");
            }
            storage.setUsed(storage.getUsed() + deCount);
            storage.setResidue(storage.getResidue() - deCount);
            try {
                logger.info("执行sql开始");
                storageRepository.update(storage, wrapper.eq("product_id", productId));
                logger.info("执行sql开始");
            } catch (Exception e) {
                logger.error("更改库存失败");
                e.printStackTrace();
            }
        }else {
            logger.error("查询数据为空！");
            throw new Exception("查询数据为空！");
        }
    }
}
