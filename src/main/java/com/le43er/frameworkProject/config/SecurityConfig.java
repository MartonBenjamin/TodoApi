package com.le43er.frameworkProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  @Autowired
  private UserDetailsService userService;

  @Autowired
  public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(bCryptPasswordEncoder());
    return authProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/admin/role/**").hasAuthority("ADMIN")
        .requestMatchers("/user/modify", "user/delete", "user/admin/getUsers").hasAuthority("ADMIN")
        .requestMatchers("/todo/category/add", "/todo/category/modify", "todo/category/delete/**").hasAuthority("ADMIN")
        .requestMatchers("/todo/category/get").permitAll()
        .requestMatchers("/todo/**").hasAuthority("USER")
        .requestMatchers("/user/register", "/user/", "/user/reg", "/user/add").permitAll()
        .requestMatchers("/swagger-resources/**").permitAll()
        .requestMatchers("/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .defaultSuccessUrl("/todo/get")
        .permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/login?logout")
        .permitAll()
    ;
    return http.build();
  }
}
