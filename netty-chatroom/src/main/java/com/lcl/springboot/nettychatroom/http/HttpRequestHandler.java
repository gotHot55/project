package com.lcl.springboot.nettychatroom.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.CharsetUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/1716:06
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private DispatcherServlet servlet;

    public HttpRequestHandler(DispatcherServlet servlet) {
        this.servlet = servlet;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        boolean flag= HttpMethod.POST.equals(fullHttpRequest.method()) ||
                HttpMethod.GET.equals(fullHttpRequest.method()) ||
                HttpMethod.PUT.equals(fullHttpRequest.method()) ||
                HttpMethod.DELETE.equals(fullHttpRequest.method());
        Map<String, String> parammap = getRequestParam(ctx, fullHttpRequest);
        if (flag && ctx.channel().isActive()) {
            //http请求、 get/post
            MockHttpServletResponse servletResponse = new MockHttpServletResponse();
            MockHttpServletRequest servletRequest = new MockHttpServletRequest(
                    servlet.getServletConfig().getServletContext());
            for (String name : fullHttpRequest.headers().names()) {
                for (String value : fullHttpRequest.headers().getAll(name)) {
                    servletRequest.addHeader(name, value);
                }
            }
            String uri = fullHttpRequest.uri();
            uri = new String(uri.getBytes("ISO8859-1"), "UTF-8");
            uri = URLDecoder.decode(uri, "UTF-8");
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
            String path = uriComponents.getPath();
            path = URLDecoder.decode(path, "UTF-8");
            servletRequest.setRequestURI(path);
            servletRequest.setServletPath(path);
            servletRequest.setMethod(fullHttpRequest.method().name());
            if (uriComponents.getScheme() != null) {
                servletRequest.setScheme(uriComponents.getScheme());
            }
            if (uriComponents.getHost() != null) {
                servletRequest.setServerName(uriComponents.getHost());
            }
            if (uriComponents.getPort() != -1) {
                servletRequest.setServerPort(uriComponents.getPort());
            }
            ByteBuf content = fullHttpRequest.content();
            content.readerIndex(0);
            byte[] data = new byte[content.readableBytes()];
            content.readBytes(data);
            if (uriComponents.getQuery() != null) {
                String decode = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
                servletRequest.setQueryString(decode);
            }
            if (parammap != null && parammap.size() > 0) {
                for (String key : parammap.keySet()) {
                    servletRequest.addParameter(UriUtils.decode(key, "UTF-8"),
                            UriUtils.decode(parammap.get(key) == null ? "" : parammap.get(key),"UTF-8"));
                }
            }
            servlet.service(servletRequest,servletResponse);

            HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
            String result = servletResponse.getContentAsString();
           result =  StringUtils.isEmpty(result) ? status.toString() : result;
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                    Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
            response.headers().set("Content_Type", "text/json;charset=UTF-8");
            response.headers().set("Access-Control-Allow-Origin","*");
            response.headers().set("Access-Control-Allow-Headers",
                    "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-File-Name");
            response.headers().set("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
            response.headers().set("Content-Length",Integer.valueOf(response.content().readableBytes()));
            response.headers().set("Connection", "keep-alive");
            ChannelFuture future = ctx.writeAndFlush(response);
            future.addListener(ChannelFutureListener.CLOSE);


        }
    }

    /**
     * 获取post请求，get请求的参数保存到map中
     * @param ctx
     * @param req
     * @return
     */
    private Map<String, String> getRequestParam(ChannelHandlerContext ctx, FullHttpRequest req) {
        HashMap<String, String> requestParam = new HashMap<>();
        //处理get请求
        if (req.method() == HttpMethod.GET) {
            QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
            Map<String, List<String>> parameters = decoder.parameters();
            Iterator<Map.Entry<String, List<String>>> iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> next = iterator.next();
                requestParam.put(next.getKey(), next.getValue().get(0));
            }
        }
        if (req.method() == HttpMethod.POST) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), req);
            List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();
            for (InterfaceHttpData data : postData) {
                if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    MemoryAttribute attribute = (MemoryAttribute) data;
                    requestParam.put(attribute.getName(), attribute.getValue());
                }
            }
        }
        return requestParam;
    }
}
