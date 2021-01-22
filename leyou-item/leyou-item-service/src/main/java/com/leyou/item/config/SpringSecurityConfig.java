//package com.leyou.item.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
///**
// * @Author: MoHan
// * @Description: MoHan
// * @Date: 2021/1/18 11:12
// */
//@Component
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    /****
//     * 1、放行配置
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //放行地址
////        web.ignoring().antMatchers("/css/**");
////        web.ignoring().antMatchers("/img/**");
////        web.ignoring().antMatchers("/js/**");
////        web.ignoring().antMatchers("/plugins/**");
////        web.ignoring().antMatchers("/*.html");
////        web.ignoring().antMatchers("/seller/add.shtml");
//    }
//
//    /***
//     * 2、权限拦截配置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        //限制访问
//        http.authorizeRequests().antMatchers("/**").access("hasAnyRole('ADMIN')");
//
//        //禁用CSRF
//        http.csrf().disable();
//
//
//        //发生异常
//        http.exceptionHandling().accessDeniedPage("/error.html");
//
//        //启用iframe
//        http.headers().frameOptions().disable();
//
//        //一个用户只允许在一个地方登录，其他用户登录就会把已登录用户挤掉
//        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
//
//        //配置登录
//        http.formLogin().loginPage("http://manage.leyou.com") //登录页面地址
//                .loginProcessingUrl("/login") //登录访问路径
//                .successHandler(new AuthenticationSuccessHandler() {  //成功
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        //成功响应消息
//
//                        responseLogin(response, "/");
//                    }
//                }).failureHandler(new AuthenticationFailureHandler() {  //失败
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                //失败响应消息
//                responseLogin(response, "账号或密码不正确！");
//            }
//        });
//
//        //.defaultSuccessUrl("/admin/index.html",true)
//        //.failureUrl("/shoplogin.html");
//
//        //配置登出
//        http.logout().logoutUrl("/logout")  //退出登录的访问路径
//                .invalidateHttpSession(true) //清除所有Session
//                .logoutSuccessUrl("/login"); //退出登录的跳转地址
//
//    }
//
//    /***
//     * 响应用户登录
//     * @param response
//     * @param result
//     * @throws IOException
//     */
//    public void responseLogin(HttpServletResponse response, String result) throws IOException {
//        //设置编码格式
//        response.setContentType("application/json;charset=utf-8");
//
//        //输出数据
//        PrintWriter writer = response.getWriter();
//        writer.write(result);
//
//        writer.flush();
//        writer.close();
//    }
//
//
////    @Autowired
////    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private BCryptPasswordEncoder encoder;
//
//    /***
//     * 3、授权认证
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //写死
//        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
//
//        //自定义授权认证类,将实现了UserDetailService的对象注入认证管理器
////        auth.userDetailsService(userDetailsService)
////                .passwordEncoder(encoder);  //指定加密对象
//    }
//}
//
