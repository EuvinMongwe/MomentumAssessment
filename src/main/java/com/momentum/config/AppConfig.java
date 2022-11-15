/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author euvinmongwe
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.momentum")
public class AppConfig implements WebMvcConfigurer {

    /**
     * configs views
     *
     * @param registers
     */
    public void addViewControllers(ViewControllerRegistry registers) {
        registers.addViewController("/").setViewName("HomePage");
        registers.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * View resolver
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp().prefix("/web/jsp/")
                .suffix(".jsp");
    }

    /**
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/web/resources/");
    }

    public static String getConfigPath() {
        String templateDirectory = null;
        //templateDirectory = "/Users/euvinmongwe/NetBeansProjects/Projects/MomentumInvestments/config";
         templateDirectory="/apps/momentum/";
        return templateDirectory;
    }

    /**
     * Util method to get properties file
     *
     * @param filename file name
     * @return properties file
     */
    public static Properties getProperties(String filename) {
        filename = getConfigPath() + filename;

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filename));
            return properties;
        } catch (IOException e) {

        }
        return null;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static String getFile(String fileName) {
        String configFileName = null;
        configFileName = getConfigPath() + fileName;
        return configFileName;
    }

    /**
     * Java mail sender
     *
     * @return email sender
     */
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties emailProperties = getPropertyFile("mail.properties");

        if (emailProperties != null) {
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(Integer.parseInt(emailProperties.getProperty("mail.port")));
            mailSender.setUsername(emailProperties.getProperty("mail.user"));
            mailSender.setPassword(emailProperties.getProperty("mail.pass"));

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "false");
        }

        return mailSender;
    }

    public Properties getPropertyFile(String filename) {
        filename = AppConfig.getConfigPath() + filename;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filename));
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
