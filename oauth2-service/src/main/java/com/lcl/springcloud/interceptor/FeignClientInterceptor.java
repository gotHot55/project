package com.lcl.springcloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/216:40
 */
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            ServletRequestAttributes  attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String value = request.getHeader(name);
                    if (name.equals("Authorization")) {
                        System.out.println(name + ":-->" + value);
                        requestTemplate.header(name, value);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("出错了");
            e.printStackTrace();
        }


    }
}
