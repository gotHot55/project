package com.lcl.springcloud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.lcl.springcloud.model.Login;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2710:49
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String serviceUrl;

    /*@Override
    @HystrixCommand(fallbackMethod = "getDefault",
            commandKey = "get", threadPoolKey = "thread-pool-11", ignoreExceptions = {NumberFormatException.class, IndexOutOfBoundsException.class})
    public ModelMap get(Integer id) {
        if (!(id instanceof Integer)) {
            throw new NumberFormatException("请输入正确的id，查询id为整数");
        }
        if (id < 1) {
            throw new IndexOutOfBoundsException("查询id为不为负");
        }
        return restTemplate.getForObject(serviceUrl + "/get/" + id, ModelMap.class, id);
    }*/
    @Override
    @CacheResult(cacheKeyMethod = "getCache")
    @HystrixCommand(fallbackMethod = "getDefault",commandKey = "get")
    public ModelMap get(Integer id) {
        log.info("查询id:{}", id);
        return restTemplate.getForObject(serviceUrl + "/get/" + id, ModelMap.class, id);
    }

    public String getCache(Integer id) {
        return String.valueOf(id);
    }
    public ModelMap getDefault(@PathVariable Integer id) {
        Login login = new Login(id,"default","123456");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code", 200)
                .addAttribute("message", "查询成功")
                .addAttribute("data", login);
        return modelMap;
    }

    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    @Override
    public Future<Login> getUserFuture(Integer id) {
        return new AsyncResult<Login>() {
            @Override
            public Login invoke() {
                ModelMap modelMap = restTemplate.getForObject(serviceUrl + "/get/" + id, ModelMap.class, id);
                Map login = (Map) modelMap.getAttribute("login");
                Login login1 = BeanUtil.mapToBean(login, Login.class, true);
                return login1;
            }
        };
    }
    @HystrixCommand
    public List<Login> getUserByIds(List<Integer> ids) {
        log.info("请求id：{}", ids);
        ModelMap modelMap = restTemplate.getForObject(serviceUrl + "/get/{1}", ModelMap.class, CollUtil.join(ids, ","));
        return (List<Login>) modelMap.getAttribute("login");
    }
}
