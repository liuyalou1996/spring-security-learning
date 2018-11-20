package com.iboxpay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

  @RequestMapping("/loginProcess")
  public String handelRequest(String error, String logout) {
    // 认证失败重新跳转到登录页面
    if (error != null) {
      return "login";
    }

    // 如果是退出登录，则跳转到退出登录后的页面
    if (logout != null) {
      return "logout";
    }

    return "success";
  }
}
