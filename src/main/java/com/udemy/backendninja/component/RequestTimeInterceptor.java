package com.udemy.backendninja.component;

import com.udemy.backendninja.entity.User;
import com.udemy.backendninja.repository.LogRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

//Spring crea el bean y lo guarda al arrancar la app
@Component("requestTimeInterceptor")

public class RequestTimeInterceptor extends HandlerInterceptorAdapter {
    //HandlerInterceptorAdapter
    //Es el que se va a ejecutar antes de entrar en el metodo del controlador

    @Autowired
    @Qualifier("logRepository")
    private LogRepository logRepository;

    private static final Log LOG= LogFactory.getLog(RequestTimeInterceptor.class);

    //PRIMERO
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       request.setAttribute("startTime",System.currentTimeMillis());
       return true;
    }

    //SEGUNDO
    //Es el que se va a ejecutar justo antes de mostrar la vista en la computadora
    /*@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       long startTime = (long)request.getAttribute("startTime");
       LOG.info("Url to: '"+request.getRequestURL().toString()+ "' in: '"+(System.currentTimeMillis()-startTime)+ "ms'");
    }
    //Por cada petición que nos haga entre por esta clase*/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long)request.getAttribute("startTime");
        String url= request.getRequestURL().toString();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if(null != auth && auth.isAuthenticated()){
            username = auth.getName();
        }
        logRepository.save(new com.udemy.backendninja.entity.Log(new Date(), auth.getDetails().toString(),username,url));
        LOG.info("Url to: '"+ url + "' in"+ (System.currentTimeMillis() - startTime)+ "ms'");

    }
}
