package com.lcl.springboot.nettychatroom.http;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Todo
 *netty启动入口
 * @author Administrator
 * @date 2020/11/1715:42
 */
public class RuleApplication {
    //引擎端口
    private final static int ENGINE_PORT = 8086;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        DispatcherServlet servlet = getDispatcherSerlet(ctx);
        NettyHttpServer server = new NettyHttpServer(ENGINE_PORT, servlet);
        server.start();
    }

    private static DispatcherServlet getDispatcherSerlet(ClassPathXmlApplicationContext ctx) {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:spring-mvc.xml");
        context.setParent(ctx);
        MockServletConfig servletConfig = new MockServletConfig((ServletContext) context.getServletConfig(), "dispatcherServlet");
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        try {
            dispatcherServlet.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return dispatcherServlet;
    }
}
