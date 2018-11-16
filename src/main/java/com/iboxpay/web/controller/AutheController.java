package com.iboxpay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AutheController {

  @RequestMapping("/loginProcess")
  public String handelRequest(String error) {
    if (error != null) {
      return "failure";
    }

    return "login";
  }
}
