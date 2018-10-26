/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rd.hmspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author mfigu
 */
@SpringBootApplication
public class HMSpringbootMain {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(HMSpringbootMain.class, args);
        component bean = ctx.getBean(component.class);
        if (bean != null) {
            bean.inicio();
        }
    }
}
