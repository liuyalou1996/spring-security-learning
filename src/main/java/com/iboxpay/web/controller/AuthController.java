package com.iboxpay.web.controller;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

  @RequestMapping("/loginProcess")
  public String handleRequest(String error, String logout) {
    // 如果是退出登录，则跳转到退出登录后的页面
    if (logout != null) {
      return "logout";
    }

    // 认证、授权失败或者直接访问该地址跳回到登录页面
    return "login";
  }

  @ResponseBody
  @RequestMapping("/userInfo")
  public Map<String, Object> getUserAttributes() {
    OAuth2AuthenticationToken token =
        (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    OAuth2User user = token.getPrincipal();
    return user.getAttributes();
  }
}
