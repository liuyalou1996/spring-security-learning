package com.iboxpay.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import com.alibaba.druid.pool.DruidDataSource;

@EnableWebSecurity
public class WebSecurityConfig {

  @Configuration
  public static class FormLoginWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DruidDataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()// 限制基于HttpServletReqeuest的请求访问
            .antMatchers("/","/home").permitAll()// /和/home路径能被任何人访问
            .anyRequest().authenticated()// 其它请求需要身份认证
            .anyRequest().hasRole("USER")// 其它请求必须是USER角色，该方法默认会加上ROLE_前缀
          .and()
            .formLogin()// 支持基于表单的身份认证
            .loginPage("/login")// 指定跳转到登录页的url，若不指定则会生成默认登录页面，默认为/login
            .loginProcessingUrl("/loginProcess")// 指定认证处理的url，表单action指定地址必须为该地址，默认为/login
            .defaultSuccessUrl("/success")// 认证成功后默认跳转的地址，默认为/home
            .failureUrl("/loginProcess?error")// 认证失败后跳转的地址，默认为/login?error
            .permitAll()// 授权所有与表单登录相关的url
          .and()
            .rememberMe()// 开启记住我的功能
            .rememberMeCookieName("remember-me")// 传给浏览器的cookie名，默认为remember-me
            .rememberMeParameter("remember-me")// 前端复选框传入的字段名，默认为remember-me
            .tokenValiditySeconds(30 * 1000)// cookie有效时间
            .key(UUID.randomUUID().toString())// 防止名为remember-me的token被修改的key，默认为随机数，生成随机数需要时间，最好指定固定值
          .and()
            .logout()// 开启退出登录支持
            .logoutUrl("/logout")// 触发退出登录的url，前端页面地址必须为改地址，默认为/logout
            .logoutSuccessUrl("/loginProcess?logout")// 退出登录跳转的地址
            .deleteCookies("remember-me")// 退出登录后删除名为remember-me的cookie，默认会删除remember-me功能对应的cookie
            .permitAll()// 授权所有与退出登录相关的url
          .and()
            .csrf()
            .disable();// 禁用csrf，不禁用则要在表单里加上隐藏域或csrf标签
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      // 创建DelegatingPasswordEncoder，该PasswordEncoder会使用BCryptPasswordEncoder对密码进行编码
      PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
      // String encodedPwd = encoder.encode("123");
      // 前端传入的密码编码后与加密后的密码比对，编码后的格式为{编码器Id}+编码后的密码
      // auth.inMemoryAuthentication().withUser("lyl").password(encodedPwd).roles("USER");
      auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(encoder);
    }

  }
  
  /**
   * 多个HttpSecurity配置登录方式优先级高的生效，优先级低的不生效
   */
  @Order(1)
  @Configuration
  public static class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
            .antMatchers("/github")
            .authenticated()
          .and()
            .oauth2Login();
    }
  }

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    ClientRegistration registration = ClientRegistration.withRegistrationId("github")
        .clientId("b1970103273292301234")
        .clientSecret("fc3f1fd021822050c5c7602fa31258710df9c06d")
        .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
        .scope("read:user")
        .authorizationUri("https://github.com/login/oauth/authorize")
        .tokenUri("https://github.com/login/oauth/access_token")
        .userInfoUri("https://api.github.com/user")
        .userNameAttributeName("id")
        .clientName("GitHub")
        .build();
    return new InMemoryClientRegistrationRepository(registration);
  }

}
