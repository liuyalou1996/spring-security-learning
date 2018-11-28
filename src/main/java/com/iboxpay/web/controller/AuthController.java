package com.iboxpay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

  @RequestMapping("/loginProcess")
  public String handelRequest(String error, String logout) {
    // 如果是退出登录，则跳转到退出登录后的页面
    if (logout != null) {
      return "logout";
    }

    // 认证、授权失败或者直接访问该地址跳回到登录页面
    return "login";
  }
}
