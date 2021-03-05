package com.lcl.springcloud.controller;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/215:35
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class TestController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @GetMapping("/getCurrentUser")
    public Object authentication(Authentication authentication, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = StrUtil.subAfter(header, "bearer", false);
        return Jwts.parser()
                .setSigningKey("test-key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ModelMap postAccessToken(Principal principal/*, @RequestParam Map<String, String> parameters*/) throws HttpRequestMethodNotSupportedException {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id","admin");
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username","lcl");
        params.put("password","123456");
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, params).getBody();
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", oAuth2AccessToken.getValue());
        map.put("refreshToken", oAuth2AccessToken.getRefreshToken().getValue());
        map.put("expiresIn", oAuth2AccessToken.getExpiresIn());
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code", 0000)
                .addAttribute("message", "成功")
                .addAttribute("data", map);

        return modelMap;
    }

}
