/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 *
 * @author euvinmongwe
 */
@Configuration
public class SecurityConfig {
    
    /**
     * 
     * @param http
     * @throws Exception 
     */
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/web/resources/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/HomePage")
                .permitAll().defaultSuccessUrl("/", true)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }
    
}
