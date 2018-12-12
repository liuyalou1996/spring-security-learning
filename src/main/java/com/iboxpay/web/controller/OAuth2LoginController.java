package com.iboxpay.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OAuth2LoginController {

  /**
   * Spring Boot自动配置会在容器中注册
   */
  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;

  @ResponseBody
  @RequestMapping("/userInfo")
  public Map<String, Object> getUserAttributes() {
    // 认证成功后SecurityContextHolder会通过ThreadLocal设置SecurityContext，这里能直接访问到认证信息
    OAuth2AuthenticationToken token =
        (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    OAuth2User user = token.getPrincipal();
    return user.getAttributes();
  }

  /**
   * 获取access token
   * @param authentication 认证成功后后自动注入
   * @return
   */
  @ResponseBody
  @RequestMapping("/accessToken")
  public OAuth2AccessToken getAccessToken(OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient client = authorizedClientService
        .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    return client.getAccessToken();
  }

}
