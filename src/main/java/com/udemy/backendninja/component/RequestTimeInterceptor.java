package com.udemy.backendninja.component;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Spring crea el bean y lo guarda al arrancar la app
@Component("requestTimeInterceptor")

public class RequestTimeInterceptor extends HandlerInterceptorAdapter {
    //HandlerInterceptorAdapter
    //Es el que se va a ejecutar antes de entrar en el metodo del controlador

    private static final Log LOG= LogFactory.getLog(RequestTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       request.setAttribute("startTime",System.currentTimeMillis());
       return true;
    }

    //Es el que se va a ejecutar justo antes de mostrar la vista en la computadora
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       long startTime = (long)request.getAttribute("startTime");
       LOG.info("Url to: '"+request.getRequestURL().toString()+ "' in: '"+(System.currentTimeMillis()-startTime)+ "ms'");
    }
    //Por cada petición que nos haga entre por esta clase
}
