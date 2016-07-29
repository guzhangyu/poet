package com.poet.listener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * Created by guzy on 16/7/27.
 */
@Component
public class MyContextListener{


    @PostConstruct
    public void onApplicationEvent() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();

        ServletContext servletContext = webApplicationContext.getServletContext();
        servletContext.setAttribute("webroot",System.getProperty("webroot"));
    }
}
